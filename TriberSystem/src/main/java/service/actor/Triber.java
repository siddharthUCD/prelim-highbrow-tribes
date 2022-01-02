package service.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import jnr.ffi.annotations.In;
import service.centralCore.*;
import service.messages.*;
import service.tribersystem.TriberSystem;

import java.util.ArrayList;
import java.util.HashMap;

public class Triber extends AbstractActor {
    private static TriberSystem triberSystem;
    private static long SEED = 1;
    HashMap<Long, UserInfo> RequestsToUserInfoMap = new HashMap<Long, UserInfo>();
    ArrayList<Tribe> AllTribes = new ArrayList<>();

    private static ActorSelection InterestsActor;
    private static ActorSelection PersistanceActor;

    public static void main(String[] args){
        System.out.println("1) Test here");
        ActorSystem system = ActorSystem.create();
        system.actorOf(Props.create(Triber.class), "triber");
        InterestsActor = system.actorSelection("akka.tcp://default@127.0.0.1:2554/user/interests");
        PersistanceActor = system.actorSelection("akka.tcp://default@127.0.0.1:2552/user/userSystem");

        /*
        An initialization request will be sent ot Mansoor and list of all the tribes and use info will be
        returned and stored in the RequestsToUserInfoMap & AllTribes attributes
        */

        System.out.println("2) Test here");
//        UserInfo userInfo = new UserInfo("Ridhi","ridhi1",1000L);
//        Interests interests = new Interests();
//        interests.addProgrammingLanguages("Java");
//        userInfo.setInterests(interests);
//        NewUserRequest NUR = new NewUserRequest();
//        PersistanceActor.tell(new UserCreationRequest(1000L,userInfo), null);
        triberSystem = new TriberSystem();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(NewUserRequest.class,
                        msg -> {
                            System.out.println("User Creation Request for Name: " + msg.getNewUser().getName() + " with ID: " + msg.getNewUser().getUniqueId() + " sent to interests service");
                            long tempSeed = SEED++;
                            RequestsToUserInfoMap.put(tempSeed, new UserInfo(msg.getNewUser().getName(), msg.getNewUser().getGitHubId(), tempSeed));
                              //An Interests request will be sent to Ritika
                            InterestsActor.tell(new InterestsRequest(msg.getNewUser().getUniqueId(),msg.getNewUser().getGitHubId()), getSelf());

                        })
                .match(InterestsResponse.class,
                        msg -> {
                            RequestsToUserInfoMap.get(msg.getRequestId()).setInterests(msg.getInterests());
                        })
                .match(TribeDetailRequest.class,
                        msg -> {
//                            Interests i = new Interests();
//                            i.addProgrammingLanguages("C#");
//
//                            UserInfo us = new UserInfo("Mandvi", "", 105);
//
//                            us.setProgrammingLanguages(i.getProgrammingLanguages());
//                            List<Tribe> suggestedTribes = triberSystem.GetTribeSuggestions(us);

                            Tribe tribe = triberSystem.GetExistingTribes().get(0);
                            getSender().tell(new TribeDetailResponse(msg.getUniqueId(), tribe), null);
                        })
                .match(NewUserResponse.class,
                        msg -> {
                            System.out.println("User Creation Response for Id: " + msg.getRequestId() + " sent to the API gateway or the bridge service");
                        }).build();
    }
}
