package tn.esprit.gestionequipe.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String teamName;

    private String homeStadium;

    @Column(nullable = false)
    private LocalDateTime foundedDate;

    private LocalDateTime lastUpdated;

    @Enumerated(EnumType.STRING)
    private TeamStatus status;

    private String league;

    private String teamColors;

    private Integer squadSizeLimit = 25;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Users> players = new ArrayList<>();
}