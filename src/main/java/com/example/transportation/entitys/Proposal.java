package com.example.transportation.entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "proposals")
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer  id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "sender_address")
    private String senderAddress;

    @Column(name = "recipient_address")
    private String recipientAddress;

    @Column(name = "date_dispatch")
    private LocalDate dateDispatch;

    @Column(name = "date_receipt")
    private LocalDate dateReceipt;

    @ManyToOne
    @JoinColumn(name = "transport_id", referencedColumnName = "id")
    private Transport transport;

    @Column(name = "budget")
    private Integer budget;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @OneToOne(mappedBy = "proposal")
    private Delivery delivery;


}
