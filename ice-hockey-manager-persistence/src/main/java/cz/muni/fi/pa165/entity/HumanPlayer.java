package cz.muni.fi.pa165.entity;

import cz.muni.fi.pa165.enums.Role;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
@Entity
public class HumanPlayer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(min = 4, max = 20)
  @Column(nullable = false, unique = true)
  private String username;

  @NotNull
  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @NotNull
  @Column(nullable = false)
  private String passwordHash;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof HumanPlayer)) {
      return false;
    }
    HumanPlayer other = (HumanPlayer) o;
    return Objects.equals(email, other.email);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
        "username='" + username + '\'' +
        ", email='" + email + '\'' +
        ", role=" + role +
        '}';
  }
}
