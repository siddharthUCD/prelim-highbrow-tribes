package service.actor;

import akka.actor.*;
import service.core.Tribe;
import service.core.UserInfo;
import service.messages.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.*;

public class Client extends AbstractActor {
    @Override
    public AbstractActor.Receive createReceive() {
        /**
         * Read the message from the broker and display the quotations from all services against the client info
         */
        return  receiveBuilder()
            .match(String.class,msg->{
                System.out.println("Client received: " + msg.toLowerCase(Locale.ROOT));
            })
            .match(ChatRegisterResponse.class, msg->{
                Tribe CurrTribe = msg.getTribe();
                System.out.println("You have joined the community dialogue of " + CurrTribe.getTribeName());
                Thread thread = new Thread(){
                    public void run(){
                        IsInChat = true;
                        ParticipateInChat();
                    }
                };
                thread.start();
            })
            .match(ChatMessageReceive.class, msg->{
                System.out.println("[" + msg.getSentTime() + "] " + msg.getSenderName() + ": " + msg.getMessage());

                Thread thread = new Thread(){
                    public void run(){
                        ParticipateInChat();
                    }
                };

                if(!IsInChat){
                    IsInChat = true;
                    thread.start();
                }
            }).build();
    }
    private static ActorSelection selection;
    private static UserInfo UI;
    private static Boolean IsInChat = false;

    public static void main(String [] args){
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(Client.class), "105105");
//        selection =
//                system.actorSelection("akka.tcp://default@127.0.0.1:2556/user/communicator");
        selection =
                system.actorSelection("akka.tcp://default@127.0.0.1:2557/user/triber");

        UI = new UserInfo();
        UI.setUniqueId(105105);
        UI.setPortNumber(2555);
        UI.setName("Mansoor");
        UI.setUniqueId(123);
        UI.setGitHubId("siddharthucd");

//        HashSet<String> programmingLanguages = new HashSet<>();
//        programmingLanguages.add("Java");
//        UI.setProgrammingLanguages(programmingLanguages);

        NewUserRequest newUserRequest  = new NewUserRequest(UI);
//        ChatRegisterRequest chatRegisterRequest = new ChatRegisterRequest();
//        chatRegisterRequest.set_UserInfo(UI);
        selection.tell(newUserRequest, ref);
    }

    private void ParticipateInChat() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Reading data using readLine
            String message = reader.readLine();
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            while(true) {
                message = reader.readLine();
                ts = new Timestamp(System.currentTimeMillis());
                // Printing the read line
                selection.tell(new ChatMessageSend(UI.getName(), ts, UI.getUniqueId(), message), getSelf());
            }
        }
        catch(IOException ex){
            System.out.println("Error occured!");
        }
    }
}
