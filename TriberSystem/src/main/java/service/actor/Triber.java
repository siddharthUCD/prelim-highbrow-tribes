package service.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.centralCore.*;
import service.messages.*;
import service.tribersystem.TriberSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Triber extends AbstractActor {
    private static TriberSystem triberSystem;
    private static long userUniqueId = 1;
    private static long tribeUniqueId = 1;
    private static ActorSelection interestsActor;
    private static ActorSelection persistanceActor;

    HashMap<Long, UserInfo> requestsToUserInfoMap = new HashMap<Long, UserInfo>();
    HashMap<Long, Long> uniqueIdMap = new HashMap<Long, Long>();
    ArrayList<Tribe> allTribes = new ArrayList<>();
    ArrayList<UserInfo> allUsers = new ArrayList<>();

    public static void main(String[] args){
        System.out.println("1) Test here");
        ActorSystem system = ActorSystem.create();
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
                    allUsers = msg.getAllUsers();
                    userUniqueId = msg.getMaxUserId();
                    tribeUniqueId = msg.getMaxTribeId();
                })
            .match(NewUserRequest.class,
                msg -> {
                    System.out.println("User Creation Request for Name: " + msg.getNewUser().getName() + " with ID: " + msg.getNewUser().getUniqueId() + " sent to interests service");

                    //Adding new User
                    long tempUserUniqueId = ++userUniqueId;
                    uniqueIdMap.put(msg.getNewUser().getUniqueId(), tempUserUniqueId);
                    requestsToUserInfoMap.put(tempUserUniqueId, new UserInfo(msg.getNewUser().getName(), msg.getNewUser().getGitHubId(), tempUserUniqueId));

                    //Interests request sent to Ritika
                    interestsActor.tell(new InterestsRequest(msg.getNewUser().getUniqueId(),msg.getNewUser().getGitHubId()), getSelf());
                })
            .match(InterestsResponse.class,
                msg -> {
                    System.out.println("Received Interests response from Ritika: " + msg.getRequestId());
                    requestsToUserInfoMap.get(msg.getRequestId()).setInterests(msg.getInterest());

                    TribeSuggestion ts = new TribeSuggestion();
                    ts.setSuggestedTribes(getTribeSuggestions(msg.getInterest()));

                    System.out.println("Sent user persistance to Mansoor: " + msg.getRequestId());
                    persistanceActor.tell(UCR, getSelf());
                })
            .match(TribeDetailRequest.class,
                msg -> {
                    Tribe tribe = triberSystem.GetExistingTribes().get(0);
                    getSender().tell(new TribeDetailResponse(msg.getUniqueId(), tribe), null);
                })
            .match(NewUserResponse.class,
                msg -> {
                    System.out.println("User Creation Response for Id: " + msg.getRequestId() + " sent to the API gateway or the bridge service");
                }).build();
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
}
