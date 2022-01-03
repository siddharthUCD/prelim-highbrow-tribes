package service.actor;

import akka.actor.*;
import service.centralCore.Tribe;
import service.centralCore.UserInfo;
import service.messages.ChatMessageReceive;
import service.messages.ChatMessageSend;
import service.messages.ChatRegisterResponse;
import service.messages.NewUserRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Random;

public class Client extends AbstractActor {
    private static ActorSystem system;
    private static ActorRef ref;
    private static ActorSelection communicationSelection;
    private static ActorSelection triberSelection;
    private static UserInfo UI;
    private static Boolean IsInChat = false;

    public static void main(String [] args){
        system = ActorSystem.create();
        communicationSelection =
                system.actorSelection("akka.tcp://default@127.0.0.1:2556/user/communicator");
        triberSelection =
                system.actorSelection("akka.tcp://default@127.0.0.1:2557/user/triber");
        Random rand = new Random();

        UI = new UserInfo();
        long uniqueId = rand.nextInt() + rand.nextInt();

        UI.setUniqueId(uniqueId);
        UI.setPortNumber(2555);
        UI.setName("Siddharth");
        UI.setGitHubId("siddharthucd");
        setReference(uniqueId);

        NewUserRequest newUserRequest  = new NewUserRequest(UI);
        triberSelection.tell(newUserRequest, ref);
    }

    @Override
    public AbstractActor.Receive createReceive() {
        /**
         * Read the message from the broker and display the quotations from all services against the client info
         */
        return  receiveBuilder()
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

    private void ParticipateInChat() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Reading data using readLine
            String message;
            Timestamp ts;
            while(true) {
                message = reader.readLine();
                ts = new Timestamp(System.currentTimeMillis());

                // Printing the read line
                communicationSelection.tell(new ChatMessageSend(UI.getName(), ts, UI.getUniqueId(), message), getSelf());
            }
        }
        catch(IOException ex){
            System.out.println("Error occured!");
        }
    }

    private static void setReference(Long UniqueId){
        ref = system.actorOf(Props.create(Client.class), UniqueId.toString());
    }
}
