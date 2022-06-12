package com.lowcost.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "flight_id")
    private Integer id;

    @Column(name = "direction")
    private String direction;

    @Column(name = "departure_time")
    private String departureTime;

    @Column(name = "flight_time")
    private String flightTime;

    @Column(name = "seats")
    private int seatsNum;

    @Column(name = "start_price")
    private double startPrice;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "flight_id")
    private List<Seat> seats;

    private double price;
    private int seatsNumAvailable;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSeatsNumAvailable() {
        return seatsNumAvailable;
    }

    public void setSeatsNumAvailable(int seatsNumAvailable) {
        this.seatsNumAvailable = seatsNumAvailable;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }

    public int getSeatsNum() {
        return seatsNum;
    }

    public void setSeatsNum(int seatsNum) {
        this.seatsNum = seatsNum;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }
}
