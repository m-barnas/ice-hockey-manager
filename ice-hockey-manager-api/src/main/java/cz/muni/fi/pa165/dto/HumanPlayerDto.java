package cz.muni.fi.pa165.dto;

import cz.muni.fi.pa165.enums.Role;

import java.util.Objects;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
public class HumanPlayerDto {

    private Long id;

    private String username;

    private String email;

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
        if (!(o instanceof HumanPlayerDto)) {
            return false;
        }
        HumanPlayerDto other = (HumanPlayerDto) o;
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
