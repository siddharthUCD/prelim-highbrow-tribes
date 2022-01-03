package service.actor;

import akka.actor.*;
import service.centralCore.Tribe;
import service.centralCore.UserInfo;
import service.messages.*;

import javax.swing.*;
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

        System.out.println("User has sent newUserRequest to Triber");

        triberSelection.tell(newUserRequest, ref);
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return  receiveBuilder()
            .match(ChatRegisterResponse.class, msg->{
                Tribe CurrTribe = msg.getTribe();
                System.out.println("You have joined the community dialogue of " + CurrTribe.getTribeName());
                Thread thread = new Thread(() -> {
                    IsInChat = true;
                    ParticipateInChat();
                });
                thread.start();
            })
            .match(ChatMessageReceive.class, msg->{
                System.out.println("[" + msg.getSentTime() + "] " + msg.getSenderName() + ": " + msg.getMessage());

                Thread thread = new Thread(this::ParticipateInChat);

                if(!IsInChat){
                    IsInChat = true;
                    thread.start();
                }
            })
            .match(TribeSuggestion.class, msg->{
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));
                StringBuilder sb;
                System.out.println("Received tribe suggestions");
                System.out.println("Enter the ID of the tribe you would like to join");
                for(Tribe tribe:msg.getSuggestedTribes()){
                    sb = new StringBuilder();

                    for(UserInfo UI:tribe.getMembers()){
                        sb.append(UI.getName()).append(", ");
                    }

                    System.out.println("Tribe ID: " + tribe.getTribeID() + ", Programming Language: " + tribe.getProgrammingLanguage() + ", Tribe Name: " + tribe.getTribeName() + ", Members: " + sb);
                }
                String tribeIdString = reader.readLine();

                try {
                    long tribeIdLong = Long.parseLong(tribeIdString);

                    UI.setUniqueId(msg.getUniqueId());
                    setReference(msg.getUniqueId());
                    UI.setTribeId(tribeIdLong);

                    triberSelection.tell(UI, getSelf());
                }
                catch(Exception ex){
                    System.out.print("Invalid Input! Restart application");
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
