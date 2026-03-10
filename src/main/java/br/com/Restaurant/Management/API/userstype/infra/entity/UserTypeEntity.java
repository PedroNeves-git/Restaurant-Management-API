package br.com.Restaurant.Management.API.userstype.infra.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users_type")
public class UserTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    public UserTypeEntity() {}

    public UserTypeEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
