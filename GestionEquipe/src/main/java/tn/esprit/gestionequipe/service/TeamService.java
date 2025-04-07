package tn.esprit.gestionequipe.service;

import tn.esprit.gestionequipe.entities.Team;
import tn.esprit.gestionequipe.entities.Users;

import java.util.List;

public interface TeamService {
    Team createTeam(Team team);
    Team getTeamById(Long id);
    List<Team> getAllTeams();
    Team updateTeam(Long id, Team teamDetails);
    void deleteTeam(Long id);
    Team addPlayerToTeam(Long teamId, Long playerId);
    Team removePlayerFromTeam(Long teamId, Long playerId);
    List<Users> getTeamPlayers(Long teamId);

    Users createUser(Users users);

    public String searchTeam(String teamName);
}