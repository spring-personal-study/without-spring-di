package hello.core.member;

public final class Member {
    private final Long id;
    private final String name;
    private final Grade grade;

    public Member(Long id, String name, Grade grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }
    /**
     * @return MemberId;
     */
    public Long getId() {
        return id;
    }

    /**
    * @return MemberName;
    */
    public String getName() {
        return name;
    }

    public Grade getGrade() {
        return grade;
    }


    /**
     * @return toString
     */
    @Override
    public String toString() {
        return "Member{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", grade=" + grade
                + '}';
    }
}

