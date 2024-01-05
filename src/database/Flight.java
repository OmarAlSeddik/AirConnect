package database;

import java.sql.Date;

public class Flight {

  private Integer id;
  private String origin;
  private String destination;
  private Date departure;
  private Date arrival;

  public Flight(
    Integer id,
    String origin,
    String destination,
    Date departure,
    Date arrival
  ) {
    this.id = id;
    this.origin = origin;
    this.destination = destination;
    this.departure = departure;
    this.arrival = arrival;
  }

  public Integer getId() {
    return id;
  }

  public String getOrigin() {
    return origin;
  }

  public String getDestination() {
    return destination;
  }

  public Date getDeparture() {
    return departure;
  }

  public Date getArrival() {
    return arrival;
  }
}
