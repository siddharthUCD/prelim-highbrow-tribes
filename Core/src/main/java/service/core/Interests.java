package service.core;

import lombok.Getter;
import lombok.Setter;
import service.messages.MySerializable;

import java.util.ArrayList;

public class Interests implements MySerializable {
    @Getter
    @Setter
    private ArrayList<String> ProgrammingLanguages;

    public Interests(){
        ProgrammingLanguages = new ArrayList<>();
    }

    public ArrayList<String> getProgrammingLanguages() {
        return ProgrammingLanguages;
    }

    public void setProgrammingLanguages(ArrayList<String> programmingLanguages) {
        ProgrammingLanguages = programmingLanguages;
    }

    public void addProgrammingLanguages(String programmingLanguage){
        ProgrammingLanguages.add(programmingLanguage);
    }
}
