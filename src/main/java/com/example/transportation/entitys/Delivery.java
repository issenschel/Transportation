package com.example.transportation.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer  id;

    @OneToOne
    @JoinColumn(name = "proposal_id", referencedColumnName = "id")
    private Proposal proposal;

    @Column(name = "status")
    private String status;
}
