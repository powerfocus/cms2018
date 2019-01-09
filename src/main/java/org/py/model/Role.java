package org.py.model;

import javax.persistence.Id;
import java.io.Serializable;

public class Role implements Serializable {
    private static final long serialVersionUID = -1784446351469550210L;
    @Id
    private Long id;
    private String name;
    private String user;

    public Role() {
    }

    public Role(Long id, String name, String user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
