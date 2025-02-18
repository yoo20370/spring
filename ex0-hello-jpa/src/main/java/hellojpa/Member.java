package hellojpa;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Member {

    @Id // PK 매핑
    private Long id;

    // 컬럼 매핑
    // 객체는 username으로 사용하고, 테이블 컬럼명은 name으로 사용하고 싶을 때
    @Column(name = "name")
    private String username;

    private Integer age;

    // enum 타입 매핑
    // 객체에서 enum 타입 사용시, DB에는 없음 그 때, Enumerated 사용하면 된다.
    // varchar 타입으로 만들어짐
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 날짜 타입 매핑
    // 자바는 날짜 시간을 구분하지 않지만
    // DB는 DATE, TIME, TIMESTAMP를 구분하므로 매핑정보를 줘야 한다.
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    // BLOB, CLOB 매핑
    // 데이터베이스에 varchar를 넘어서는 큰 컨텐츠를 넘고 싶을 때 사용
    @Lob
    private String description;

    // DB와 관계없이 메모리에서 처리하고 싶을 때 사용 - 매핑 무시
    @Transient
    private int temp;

    public Member(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
