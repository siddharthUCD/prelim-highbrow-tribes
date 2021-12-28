package service.core;

import java.util.List;

public class Tribe {
    private String ProgrammingLanguage;
    private List<UserInfo> Members;
    private String TribeName;
    private long TribeID;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(UserInfo member:Members){
            sb.append(member.getName() + ", ");
        }

        return "Tribe Id: " + TribeID + ", Tribe Name: " + TribeName + ", Language: " + ProgrammingLanguage + ", Members: " + sb.toString().substring(0,sb.length()-2);
    }

    public Tribe(long TribeId, String TribeName, String programmaingLanguage, List<UserInfo> members){
        this.TribeID = TribeId;
        this.TribeName = TribeName;
        this.ProgrammingLanguage = programmaingLanguage;
        this.Members = members;
    }

    public String getProgrammingLanguage() {
        return ProgrammingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        ProgrammingLanguage = programmingLanguage;
    }

    public List<UserInfo> getMembers() {
        return Members;
    }

    public void setMembers(List<UserInfo> members) {
        Members = members;
    }

    public String getTribeName() {
        return TribeName;
    }

    public long getTribeID() {
        return TribeID;
    }
}
