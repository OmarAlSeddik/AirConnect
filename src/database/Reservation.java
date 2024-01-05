package database;

public class Reservation {

  private User user;
  private Flight flight;

  public Reservation(User user, Flight flight) {
    this.user = user;
    this.flight = flight;
  }

  public User getUser() {
    return user;
  }

  public Flight getFlight() {
    return flight;
  }
}
