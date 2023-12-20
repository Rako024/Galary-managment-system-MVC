package com.galery.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String date;
    private String type;
    private int price;
    private String location;
    private String about;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public boolean hasCustomer() {
        return customer != null;
    }

    public void removeCustomer() {
        this.customer = null;
    }

}
