package hello.spring.member;

public class Member {

    private Long id;
    private String Name;
    private Grade Grade;

    public Member(Long id, String name, Grade grade) {
        this.id = id;
        Name = name;
        Grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public hello.spring.member.Grade getGrade() {
        return Grade;
    }

    public void setGrade(hello.spring.member.Grade grade) {
        Grade = grade;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Grade=" + Grade +
                '}';
    }
}
