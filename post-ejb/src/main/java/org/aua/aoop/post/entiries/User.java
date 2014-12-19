package org.aua.aoop.post.entiries;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class User {

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    private String name;

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
