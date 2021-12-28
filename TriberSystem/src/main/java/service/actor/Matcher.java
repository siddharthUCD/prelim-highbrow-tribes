package service.actor;

import akka.actor.*;
import service.core.UserInfo;
import service.message.*;

import java.util.HashMap;

public class Matcher extends AbstractActor {
    ActorSystem system = ActorSystem.create();

    ActorSelection InterestsActor = system.actorSelection("akka.tcp://default@127.0.0.1:2551/user/interests");
    ActorSelection UserCreationActor = system.actorSelection("akka.tcp://default@127.0.0.1:2551/user/usercreation");
    //Orchestrator is either the API gateway or a separate service that's going to be the center of control
    ActorSelection OrchestratorActor = system.actorSelection("akka.tcp://default@127.0.0.1:2551/user/orchestrator");

    //Maintains the list of available quotations services
    ActorRef actorRef = new ActorRef() {
        @Override
        public ActorPath path() {
            return null;
        }

        @Override
        public boolean isTerminated() {
            return false;
        }
    };

    HashMap<Long, UserInfo> RequestsToUserInfoMap = new HashMap<Long, UserInfo>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(NewUserRequest.class,
                        msg -> {
                            RequestsToUserInfoMap.put(msg.getRequestId(), msg.getUserInfo());
                            InterestsRequest IR = new InterestsRequest(msg.getRequestId());
                            InterestsActor.tell(IR, getSelf());
                            System.out.println("User Creation Request for Name: " + msg.getUserInfo().getName() + " with ID: " + msg.getRequestId() + " sent to interests service");
                        })
                .match(InterestsResponse.class,
                        msg -> {
                            UserCreationRequest UCR = new UserCreationRequest(msg.getRequestId(), RequestsToUserInfoMap.get(msg.getRequestId()), msg.getInterests());

                            UserCreationActor.tell(UCR, getSelf());
                            System.out.println("Interests response received for Id: " + msg.getRequestId() + " and sent to the user creation service");
                        })
                .match(UserCreationResponse.class,
                        msg -> {
                            //Orchestrator is either the API gateway or a separate service that's going to be the center of control
                            OrchestratorActor.tell(new NewUserResponse(msg.getRequestId(), msg.getSuggestedTribes()), null);

                            System.out.println("User Creation Response for Id: " + msg.getRequestId() + " sent to the API gateway or the bridge service");
                        }).build();
    }
}
