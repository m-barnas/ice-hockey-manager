package cz.muni.fi.pa165.dto;

/**
 * @author Martin Barnas 433523@mail.muni.cz
 */
public class HumanPlayerAuthenticateDTO {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof HumanPlayerAuthenticateDTO)) {
            return false;
        }
        HumanPlayerAuthenticateDTO that = (HumanPlayerAuthenticateDTO) o;
        return getEmail().equals(that.getEmail());
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
        return "HumanPlayerAuthenticateDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
