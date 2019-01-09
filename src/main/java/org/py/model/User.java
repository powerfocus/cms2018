package org.py.model;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
    private static final long serialVersionUID = -1143454276688380817L;
    @Id
    private Long id;
    private String username;
    private String password;
    private Integer ulevel;
    private LocalDateTime lastlogin;
    private String info;
    private Boolean islock;
    private String role;

    public User() {
    }

    public User(Long id, String username, String password, Integer ulevel, LocalDateTime lastlogin, String info, Boolean islock, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.ulevel = ulevel;
        this.lastlogin = lastlogin;
        this.info = info;
        this.islock = islock;
        this.role = role;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUlevel() {
        return ulevel;
    }

    public void setUlevel(Integer ulevel) {
        this.ulevel = ulevel;
    }

    public LocalDateTime getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(LocalDateTime lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean getIslock() {
        return islock;
    }

    public void setIslock(Boolean islock) {
        this.islock = islock;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ulevel=" + ulevel +
                ", lastlogin=" + lastlogin +
                ", info='" + info + '\'' +
                ", islock=" + islock +
                ", role='" + role + '\'' +
                '}';
    }
}
