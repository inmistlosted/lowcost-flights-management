package com.lowcost.entities;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticket_id")
    private int id;

    private boolean baggage;
    private boolean priority;
    private double price;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Column(name = "user_id")
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBaggage() {
        return baggage;
    }

    public void setBaggage(boolean baggage) {
        this.baggage = baggage;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Ticket(){}

    public Ticket(Seat seat){
        this.seat = seat;
    }

    public Ticket(boolean baggage, boolean priority, double price, Seat seat, int userId) {
        this.baggage = baggage;
        this.priority = priority;
        this.price = price;
        this.seat = seat;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", baggage=" + baggage +
                ", priority=" + priority +
                ", price=" + price +
                ", seat=" + seat +
                ", userId=" + userId +
                '}';
    }
}
