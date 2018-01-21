package model;

/**
 *
 * @author student
 */
public class User {
    private String username;
    private String account;
    private String password;
    private String department;
    private int priority;

    public User() {
        
    }
    public User(String u) {
        username = u;
    }
    
    public String getUsername() {
        return username;
    }

    public String getAccount() {
        return account;
    }

    public String getDepartment() {
        return department;
    }

    public int getPriority() {
        return priority;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPriority(int priority) {
        System.out.println("設定優先全");
        this.priority = priority;
    }
    
    
    
}
