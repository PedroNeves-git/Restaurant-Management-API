package br.com.Restaurant.Management.API.users.core.domain;

import br.com.Restaurant.Management.API.users.core.domain.vo.Email;
import br.com.Restaurant.Management.API.users.core.domain.vo.Login;
import br.com.Restaurant.Management.API.users.core.domain.vo.Password;
import br.com.Restaurant.Management.API.users.core.dto.output.UserOutputDTO;
import br.com.Restaurant.Management.API.users.core.dto.UserPersistenceDTO;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private Email email;
    private Login login;
    private Password password;
    private boolean active;
    private Long typeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User(
            Long id,
            String name,
            Email email,
            Login login,
            Password password,
            boolean active,
            Long typeId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = requireName(name);
        this.email = Objects.requireNonNull(email);
        this.login = Objects.requireNonNull(login);
        this.password = Objects.requireNonNull(password);
        this.active = active;
        this.typeId = Objects.requireNonNull(typeId);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public static User newUser(String name, String email, String login, String password, Long typeId) {
        LocalDateTime now = LocalDateTime.now();
        return new User(null, name, new Email(email), new Login(login), new Password(password), true, typeId, now, now);
    }

    public static User restore(Long id, String name, String email, String login, String password,
                               Boolean active, Long typeId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new User(id, name, new Email(email), new Login(login), new Password(password),
                Boolean.TRUE.equals(active), typeId, createdAt, updatedAt);
    }

    public void update(String name, String email, String login, Long typeId, Boolean active) {
        if (name != null) this.name = requireName(name);
        if (email != null) this.email = new Email(email);
        if (login != null) this.login = new Login(login);
        if (typeId != null) this.typeId = typeId;
        if (active != null) this.active = active;
        refreshUpdatedAt();
    }

    public UserOutputDTO toOutput() {
        return new UserOutputDTO(id, name, email.value(), login.value(), active, typeId, createdAt);
    }

    public UserPersistenceDTO export() {
        return new UserPersistenceDTO(id, name, email.value(), login.value(), password.value(), active, typeId, createdAt, updatedAt);
    }

    private void refreshUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    private String requireName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
        return name;
    }
}