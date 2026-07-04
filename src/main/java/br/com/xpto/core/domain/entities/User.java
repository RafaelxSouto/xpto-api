package br.com.xpto.core.domain.entities;

import java.time.LocalDateTime;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String avatarUrl;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(String id, String name, String email, String password, String avatarUrl, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User create(String name, String email, String password) {
        return new User(null, name, email, password, null, false, LocalDateTime.now(), LocalDateTime.now());
    }

    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateAvatar(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getAvatarUrl() { return avatarUrl; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
