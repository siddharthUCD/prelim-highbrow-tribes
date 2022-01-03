package service.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import lombok.Getter;
import lombok.Setter;
import service.centralCore.*;
import service.messages.*;
import service.tribersystem.TriberSystem;

import java.util.*;
import java.util.stream.Collectors;

public class Triber extends AbstractActor {
    private static ActorSystem system;
    private static TriberSystem triberSystem;
    private static long userUniqueId = 1;
    private static long tribeUniqueId = 1;
    private static ActorSelection interestsActor, persistanceActor;

    HashMap<Long, UserInfo> requestsToUserInfoMap = new HashMap<>();
    HashMap<Long, Long> uniqueIdMap = new HashMap<>();
    ArrayList<Tribe> allTribes = new ArrayList<>();

    public static void main(String[] args){
        System.out.println("1) Test here");
        system = ActorSystem.create();
        system.actorOf(Props.create(Triber.class), "triber");
        interestsActor = system.actorSelection("akka.tcp://default@127.0.0.1:2554/user/interests");
        persistanceActor = system.actorSelection("akka.tcp://default@127.0.0.1:2552/user/userSystem");

        System.out.println("2) Test here");
        persistanceActor.tell("InitializeTriberSystem", null);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(TriberInitializationResponse.class,
                msg -> {
                    allTribes = msg.getAllTribes();
                    userUniqueId = msg.getMaxUserId();
                    tribeUniqueId = msg.getMaxTribeId();
                })
            .match(NewUserRequest.class,
                msg -> {
                    //Adding new User
                    long currUserUniqueId = ++userUniqueId;
                    uniqueIdMap.put(currUserUniqueId, msg.getNewUser().getUniqueId());
                    requestsToUserInfoMap.put(currUserUniqueId, new UserInfo(msg.getNewUser().getName(), msg.getNewUser().getGitHubId(), currUserUniqueId));

                    //Interests request sent to Interests System
                    System.out.println("User Creation Request for Name: " + msg.getNewUser().getName() + " with ID: " + msg.getNewUser().getUniqueId() + " sent to interests service");
                    interestsActor.tell(new InterestsRequest(currUserUniqueId,msg.getNewUser().getGitHubId()), getSelf());
                })
            .match(InterestsResponse.class,
                msg -> {
                    System.out.println("Received Interests response from Interests System for " + msg.getRequestId());
                    requestsToUserInfoMap.get(msg.getRequestId()).setInterests(msg.getInterest());

                    TribeSuggestion ts = new TribeSuggestion();
                    HashSet<Tribe> suggestedTribe = getTribeSuggestions(msg.getInterest());

                    if(suggestedTribe.size() == 0){
                        System.out.println("new Tribe Creation");
                        String programmingLanguage = "";

                        for(String language:msg.getInterest().getProgrammingLanguages()){
                            programmingLanguage = language;
                            break;
                        }

                        List<UserInfo> members = Arrays.asList(requestsToUserInfoMap.get(msg.getRequestId()));
                        String tribeName = programmingLanguage + "_Tribe";
                        long tribeID = ++tribeUniqueId;
                        Tribe freshTribe = new Tribe(tribeID, tribeName, programmingLanguage, members);

                        requestsToUserInfoMap.get(msg.getRequestId()).setTribeId(tribeID);

                        TribeCreationRequest TCR = new TribeCreationRequest(requestsToUserInfoMap.get(msg.getRequestId()), freshTribe);
                        persistanceActor.tell(TCR, getSelf());
                    }
                    else if(suggestedTribe.size() == 1){
                        System.out.println("Assigning available tribe");

                        UserInfo currUser = requestsToUserInfoMap.get(msg.getRequestId());

                        for(Tribe tribe:suggestedTribe){
                            currUser.setTribeId(tribe.getTribeID());
                        }

                        UserCreationRequest UCR = new UserCreationRequest(msg.getRequestId(), currUser);

                        persistanceActor.tell(UCR, getSelf());
                    }
                    else{
                        System.out.println("User has to choose the tribe");

                        UserInfo tempUser = requestsToUserInfoMap.get(msg.getRequestId());
                        ActorSelection tempClientActor = system.actorSelection("akka.tcp://default@127.0.0.1:" + tempUser.getPortNumber() + "/user/" + uniqueIdMap.get(msg.getRequestId()));

                        ts.setUniqueId(msg.getRequestId());
                        tempClientActor.tell(ts, null);
                    }
                })
            .match(TribeDetailRequest.class,
                msg -> {
                    long tribeId = requestsToUserInfoMap.get(msg.getUniqueId()).getTribeId();
                    Tribe tribe = getTribeById(tribeId);
                    getSender().tell(new TribeDetailResponse(msg.getUniqueId(), tribe), null);
                })
            .match(NewUserResponse.class,
                msg -> System.out.println("User Creation Response for Id: " + msg.getRequestId() + " sent to the API gateway or the bridge service")).build();
    }

    private HashSet<Tribe> getTribeSuggestions(Interests interests){
        HashSet<Tribe> filteredTribes = new HashSet<>();

        for(Tribe existingTribe:allTribes){
            if(interests.getProgrammingLanguages().contains(existingTribe.getProgrammingLanguage())){
                filteredTribes.add(existingTribe);
            }
        }

        return filteredTribes;
    }

    private Tribe getTribeById(long tribeId){
        return allTribes.stream().filter(x->x.getTribeID() == tribeId).collect(Collectors.toList()).get(0);
    }
}
