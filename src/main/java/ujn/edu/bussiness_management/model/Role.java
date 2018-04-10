package ujn.edu.bussiness_management.model;

public class Role {
    private Long  id;

    private String role;

    private String rname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getrname() {
        return rname;
    }

    public void setrname(String rname) {
        this.rname = rname == null ? null : rname.trim();
    }
}