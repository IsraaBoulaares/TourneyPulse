package tn.esprit.microservice.tournoi.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

public class Tournoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String lieu;
    private String date;
    private String classement;

    @OneToMany(mappedBy = "tournoi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<match> matches = new ArrayList<>();
    public Tournoi() {}

    public Tournoi(String nom, String lieu, String date, String classement) {
        this.nom = nom;
        this.lieu = lieu;
        this.date = date;
        this.classement = classement;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getClassement() { return classement; }
    public void setClassement(String classement) { this.classement = classement; }

    public List<match> getMatches() { return matches; }
    public void setMatches(List<match> matches) { this.matches = matches; }
}
