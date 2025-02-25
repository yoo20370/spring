package jpabook.jpql.jpql;

public class MemberDto {

    private String username;
    private String teamname;
    private Team team;
    private boolean bool;

    public MemberDto(String username, String teamname, Team team, boolean bool) {
        this.username = username;
        this.teamname = teamname;
        this.team = team;
        this.bool = bool;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
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

    @Override
    public String toString() {
        return "MemberDto{" +
                "username='" + username + '\'' +
                ", teamname='" + teamname + '\'' +
                ", team=" + team +
                ", bool=" + bool +
                '}';
    }
}
