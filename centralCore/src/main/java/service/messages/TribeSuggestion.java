package service.messages;

import lombok.Getter;
import lombok.Setter;
import service.centralCore.Tribe;

import java.util.HashSet;

public class TribeSuggestion {
    @Getter
    @Setter
    public HashSet<Tribe> suggestedTribes;
}
