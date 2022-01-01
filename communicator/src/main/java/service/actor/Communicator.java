package service.actor;

import akka.actor.*;
import service.core.Tribe;
import service.core.UserInfo;
import service.messages.*;

import java.util.HashMap;
import java.util.Locale;

public class Communicator extends AbstractActor {
    private static ActorSystem system;
    public static void main(String [] args){
        System.out.println("1) Test here");
        system = ActorSystem.create();
        system.actorOf(Props.create(Communicator.class), "communicator");
        System.out.println("2) Test here");
    }
    private ActorSelection TriberActor = system.actorSelection("akka.tcp://default@127.0.0.1:2557/user/triber");
    private HashMap<Long, Tribe> ActiveUsers = new HashMap<>();
    private HashMap<Long, Integer> UserPorts = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class,
                        msg -> {
                            System.out.println("test initializaogoe" + msg.toLowerCase(Locale.ROOT));
//                            MatcherActor.tell(new TribeDetailRequest(msg.getUniqueId()), null);
                        })
                .match(ChatRegisterRequest.class,
                        msg -> {
                            System.out.println("Chat register request received from: " + msg.getUniqueId());

                            ActiveUsers.put(msg.getUniqueId(), null);
                            UserPorts.put(msg.getUniqueId(), msg.getPortNumber());
                            TriberActor.tell(new TribeDetailRequest(msg.getUniqueId()), getSelf());
                        })
                .match(TribeDetailResponse.class,
                        msg -> {
                            ActiveUsers.put(msg.getUniqueId(), msg.getTribe());
                            int PortNumber = UserPorts.get(msg.getUniqueId());
                            ActorSelection tempSelection = system.actorSelection("akka.tcp://default@127.0.0.1:" + PortNumber + "/user/" + msg.getUniqueId());
                            tempSelection.tell(new ChatRegisterResponse(msg.getTribe()), null);
                        })
                .match(ChatMessageSend.class,
                        msg -> {
                            ActorSelection selection;
                            for(UserInfo user : ActiveUsers.get(msg.getUniqueId()).getMembers()){
                                if(user.getUniqueId() != msg.getUniqueId()) {
                                    System.out.println("PortNumber: " + user.getPortNumber() + user.getUniqueId());
                                    selection = system.actorSelection("akka.tcp://default@127.0.0.1:" + user.getPortNumber() + "/user/" + user.getUniqueId());
                                    selection.tell(new ChatMessageReceive(msg.getSenderName(), msg.getSentTime(), msg.getUniqueId(), msg.getMessage()), null);
                                }
                            }
                        })
                .match(ChatMessageReceive.class,
                        msg -> {
//                            ActiveUsers.put(msg.getUniqueId(), msg.getTribe());
                        }).build();
    }
}
