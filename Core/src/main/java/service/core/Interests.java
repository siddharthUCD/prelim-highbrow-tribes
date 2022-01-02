package service.core;

import lombok.Getter;
import lombok.Setter;
import service.messages.MySerializable;

import java.util.ArrayList;
import java.util.HashSet;

public class Interests implements MySerializable {
    @Getter
    @Setter
    //private ArrayList<String> programmingLanguages;
    private HashSet<String> programmingLanguages;

    public Interests(){
        //programmingLanguages = new ArrayList<>();
        programmingLanguages = new HashSet<String>();
    }

    public Interests(HashSet<String> programmingLanguages){
        this.programmingLanguages = programmingLanguages;
    }


    public void addProgrammingLanguages(String programmingLanguage){
        programmingLanguages.add(programmingLanguage);
    }
}
