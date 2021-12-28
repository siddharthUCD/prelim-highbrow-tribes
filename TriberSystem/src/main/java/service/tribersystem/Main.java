package service.tribersystem;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.Matcher;

public class Main {
    public static void main(String[] args){
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(Matcher.class), "matcher");
    }
}
