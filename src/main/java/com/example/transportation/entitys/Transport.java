package com.example.transportation.entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "transport")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Byte id;

    @Column(name = "name")
    private String name;

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "transport")
    private List<Proposal> proposal;
}
