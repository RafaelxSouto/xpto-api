package br.com.xpto.modules.user.entity;


import br.com.xpto.modules.user.enums.UserRole;
import br.com.xpto.modules.user.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(length = 36, nullable = false, updatable = false)
  private UUID id;

  @Column(name = "full_name", nullable = false, length = 100)
  private String fullName;

  @Column(name = "user_name", nullable = false, unique = true, length = 30)
  private String userName;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @Column(nullable = false, unique = true, length = 11)
  private String cpf;

  private LocalDate birthDate;

  @Column(length = 20)
  private String phone;

  @Column(name = "profile_image_url", length = 255)
  private String profileImageUrl;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private UserRole role;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private UserStatus status;

  @Column(name = "is_verified", nullable = false)
  private Boolean isVerified;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt = Instant.now();


  @PrePersist
  public void prePersist() {
    if (id == null) {
      id = UUID.randomUUID();
    }
    if (createdAt == null) {
      createdAt = Instant.now();
    }
    if (role == null) {
      role = UserRole.USER;
    }
    if (status == null) {
      status = UserStatus.ACTIVE;
    }
    if (isVerified == null) {
      isVerified = Boolean.FALSE;
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return status != UserStatus.BANNED;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return status == UserStatus.ACTIVE;
  }
}
