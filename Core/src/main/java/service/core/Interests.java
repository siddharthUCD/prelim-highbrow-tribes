package service.core;

import java.util.ArrayList;
import java.util.List;

public class Interests {
    private List<String> ProgrammingLanguages;

    public Interests(){
        ProgrammingLanguages = new ArrayList<>();
    }

    public List<String> GetProgrammingLanguages(){
        return ProgrammingLanguages;
    }

    public void AddProgrammingLanguages(String ProgrammingLanguage){
        if(!ProgrammingLanguages.contains(ProgrammingLanguage)) {
            ProgrammingLanguages.add(ProgrammingLanguage);
        }
    }
}
