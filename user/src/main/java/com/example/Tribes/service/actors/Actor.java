package com.example.Tribes.service.actors;

import akka.actor.AbstractActor;
import com.example.Tribes.Repo.Constants;
import com.example.Tribes.TribesApplication;
import service.messages.NewUserRequest;
import service.messages.NewUserResponse;
import service.messages.UserCreationRequest;
import service.messages.UserCreationResponse;

public class Actor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(UserCreationRequest.class,
                msg -> {
                    if(Constants.configurableApplicationContext == null) {
                        TribesApplication.main(new String[0]);
                    }
                    TribesApplication.setUserInfo(msg.getNewUser());
                    getSender().tell(new UserCreationResponse(msg.getRequestId()),getSelf());
                }).match(NewUserRequest.class,
                msg -> {
                    if(Constants.configurableApplicationContext == null) {
                        TribesApplication.main(new String[0]);
                    }
                   getSender().tell(new NewUserResponse(msg.getNewUser().getUniqueId(),TribesApplication.getUserInfo(msg.getNewUser().getUniqueId())),getSelf());
                }).build();
    }
}
