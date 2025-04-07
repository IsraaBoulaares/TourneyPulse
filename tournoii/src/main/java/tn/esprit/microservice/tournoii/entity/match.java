package tn.esprit.microservice.tournoii.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMatch;

    private String nomMatch;
    private LocalDateTime dateMatch;
    private Double duree;


    @ManyToOne
    @JoinColumn(name = "tournoi_id")
    @JsonIgnore
    private Tournoi tournoi;


    public match() {
    }

    public match(String nomMatch, LocalDateTime dateMatch, Double duree) {
        this.nomMatch = nomMatch;
        this.dateMatch = dateMatch;
        this.duree = duree;
    }


    public Long getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Long idMatch) {
        this.idMatch = idMatch;
    }

    public String getNomMatch() {
        return nomMatch;
    }

    public void setNomMatch(String nomMatch) {
        this.nomMatch = nomMatch;
    }

    public LocalDateTime getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(LocalDateTime dateMatch) {
        this.dateMatch = dateMatch;
    }

    public Double getDuree() {
        return duree;
    }

    public void setDuree(Double duree) {
        this.duree = duree;
    }

    public Tournoi getTournoi() {
        return tournoi;
    }

    public void setTournoi(Tournoi tournoi) {
        this.tournoi = tournoi;
    }
}
