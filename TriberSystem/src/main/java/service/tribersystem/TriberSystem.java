package service.tribersystem;

import service.core.Interests;
import service.core.Tribe;
import service.core.UserInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TriberSystem {
    public List<Tribe> GetTribeSuggestions(UserInfo userInfo){
        List<Tribe> ExistingTribes = new ArrayList<>();
        List<Tribe> FilteredTribes = new ArrayList<>();
        Interests siddharthInterests = new Interests();
        Interests mansoorInterests = new Interests();
        Interests ritikaInterests = new Interests();

        siddharthInterests.AddProgrammingLanguages("C#");
        mansoorInterests.AddProgrammingLanguages("Java");
        ritikaInterests.AddProgrammingLanguages("Java");

        UserInfo siddharth = new UserInfo("Siddharth", "", siddharthInterests);
        UserInfo mansoor = new UserInfo("Mansoor", "", mansoorInterests);
        UserInfo ritika = new UserInfo("Ritika", "", ritikaInterests);

        Tribe CSharpTribe = new Tribe(101, "CSharp Tribe", "C#", Arrays.asList(siddharth));
        Tribe JavaTribe = new Tribe(102, "Java Tribe", "Java", Arrays.asList(ritika, mansoor));

        ExistingTribes = Arrays.asList(JavaTribe, CSharpTribe);

        for(Tribe ExistingTribe:ExistingTribes){
            if(userInfo.getProgrammingLanguages().contains(ExistingTribe.getProgrammingLanguage())){
                FilteredTribes.add(ExistingTribe);
            }
        }

        return FilteredTribes;
    }

    public List<Tribe> GetExistingTribes(){
        List<Tribe> ExistingTribes = new ArrayList<>();
        Interests siddharthInterests = new Interests();
        Interests mansoorInterests = new Interests();
        Interests ritikaInterests = new Interests();

        siddharthInterests.AddProgrammingLanguages("C#");
        mansoorInterests.AddProgrammingLanguages("Java");
        ritikaInterests.AddProgrammingLanguages("Java");

        UserInfo siddharth = new UserInfo("Siddharth", "", siddharthInterests);
        UserInfo mansoor = new UserInfo("Mansoor", "", mansoorInterests);
        UserInfo ritika = new UserInfo("Ritika", "", ritikaInterests);

        Tribe CSharpTribe = new Tribe(101, "CSharp Tribe", "C#", Arrays.asList(siddharth));
        Tribe JavaTribe = new Tribe(102, "Java Tribe", "C#", Arrays.asList(ritika, mansoor));

        ExistingTribes = Arrays.asList(JavaTribe, CSharpTribe);

        return ExistingTribes;
    }
}
