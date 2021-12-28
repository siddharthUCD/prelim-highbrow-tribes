import akka.actor.ActorSystem;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import service.core.Interests;
import service.core.Tribe;
import service.core.UserInfo;
import service.tribersystem.TriberSystem;

import java.util.List;

public class TriberSystemTest {
    static ActorSystem system;

    @BeforeClass
    public static void setup(){
    }

    @AfterClass
    public static void teardown(){
    }

    @Test
    public void testTriberSystem() {
        TriberSystem ts = new TriberSystem();
        Interests i = new Interests();
        i.AddProgrammingLanguages("C#");

        UserInfo us = new UserInfo("Mandvi", "", i);

        List<Tribe> tribes = ts.GetTribeSuggestions(us);

        for(Tribe tribe:tribes){
            System.out.println(tribe.toString());
        }
    }
}
