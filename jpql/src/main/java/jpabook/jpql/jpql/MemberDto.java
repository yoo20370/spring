package jpabook.jpql.jpql;

public class MemberDto {

    private String username;
    private String teamname;

    public MemberDto(String username, String teamname) {
        this.username = username;
        this.teamname = teamname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }
}
