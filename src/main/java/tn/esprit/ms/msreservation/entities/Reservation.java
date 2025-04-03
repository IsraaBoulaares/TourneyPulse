package tn.esprit.ms.msreservation.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idStade;
    private LocalDate date;
    private Long idPremiereEquipe;
    private Long idDeuxiemeEquipe;
    private Long idOrganisateur;

    @Column(columnDefinition = "TEXT")
    private String qrCodeBase64;
}
