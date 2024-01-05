package database;

import java.util.Set;

public class User {

  private Integer id;
  private String username;
  private String password;
  private Boolean isAdmin;
  private Set<Reservation> reservations;

  public User(Integer id, String username, String password, Boolean isAdmin) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.isAdmin = isAdmin;
  }

  public Integer getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public Set<Reservation> getReservations() {
    return reservations;
  }
}
