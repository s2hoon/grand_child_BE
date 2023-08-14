package grandchild.grandchild.dto;

public class SignupRequest {
    private String id;
    private String username;
    private String password;
    private int age;

    public SignupRequest() {
        // 기본 생성자
    }

    public SignupRequest(String id, String username, String password, int age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    // Getter와 Setter 메서드 구현

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}