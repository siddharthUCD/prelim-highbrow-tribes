package com.example.Tribes.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashSet;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long UniqueId;
    private String name;
    private int tribeId;
    private String programmingLanguage;
    private String gitHubId;


    public User(String name, int tribeId, String programmingLanguage,String gitHubId) {
        this.name = name;
        this.tribeId = tribeId;
        this.programmingLanguage = programmingLanguage;
        this.gitHubId = gitHubId;
    }

    public User(){}

    public Long getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.UniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTribeId() {
        return tribeId;
    }

    public void setTribeId(int tribeId) {
        this.tribeId = tribeId;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getGitHubId() {
        return gitHubId;
    }

    public void setGitHubId(String gitHubId) {
        this.gitHubId = gitHubId;
    }
}
