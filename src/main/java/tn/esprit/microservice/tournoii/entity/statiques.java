package tn.esprit.microservice.tournoii.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class statiques {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long matchId;

    private int butsEquipeA;
    private int butsEquipeB;
    private int tirsEquipeA;
    private int tirsEquipeB;
    private int fautesEquipeA;
    private int fautesEquipeB;

    private int possessionEquipeA;
    private int possessionEquipeB;

    // Getters et setters...
}
