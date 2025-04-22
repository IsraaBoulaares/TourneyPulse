package tn.esprit.gestionequipe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.gestionequipe.DTO.Stade;
import tn.esprit.gestionequipe.entities.Team;
import tn.esprit.gestionequipe.entities.TeamStatus;
import tn.esprit.gestionequipe.entities.Users;
import tn.esprit.gestionequipe.feign.StadeClient;
import tn.esprit.gestionequipe.repository.TeamRepository;
import tn.esprit.gestionequipe.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StadeClient stadeClientService;



    public Stade getStadeById(Long id) {
        return stadeClientService.getStadeById(id);
    }

    public List<Stade> getAllStades() {
        return stadeClientService.getAllStades();
    }




    @Override
    @Transactional
    public Team createTeam(Team team) {
        if (team.getTeamName() == null || team.getTeamName().trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be empty");
        }

        team.setFoundedDate(LocalDateTime.now());
        team.setLastUpdated(LocalDateTime.now());
        team.setStatus(TeamStatus.ACTIVE);
        return teamRepository.save(team);
    }

    @Override
    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Football team not found with id: " + id));
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    @Transactional
    public Team updateTeam(Long id, Team teamDetails) {
        Team existingTeam = getTeamById(id);

        if (teamDetails.getTeamName() != null && !teamDetails.getTeamName().trim().isEmpty()) {
            existingTeam.setTeamName(teamDetails.getTeamName());
        }
        if (teamDetails.getHomeStadium() != null) {
            existingTeam.setHomeStadium(teamDetails.getHomeStadium());
        }
        if (teamDetails.getStatus() != null) {
            existingTeam.setStatus(teamDetails.getStatus());
        }
        if (teamDetails.getLeague() != null) {
            existingTeam.setLeague(teamDetails.getLeague());
        }
        if (teamDetails.getTeamColors() != null) {
            existingTeam.setTeamColors(teamDetails.getTeamColors());
        }
        if (teamDetails.getSquadSizeLimit() != null) {
            existingTeam.setSquadSizeLimit(teamDetails.getSquadSizeLimit());
        }

        existingTeam.setLastUpdated(LocalDateTime.now());
        return teamRepository.save(existingTeam);
    }

    @Override
    @Transactional
    public void deleteTeam(Long id) {
        Team team = getTeamById(id);
        team.getPlayers().forEach(player -> player.setTeam(null));
        team.getPlayers().clear();
        teamRepository.delete(team);
    }

    @Override
    @Transactional
    public Team addPlayerToTeam(Long teamId, Long playerId) {
        Team team = getTeamById(teamId);
        Users player = userRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + playerId));

        if (team.getSquadSizeLimit() != null && team.getPlayers().size() >= team.getSquadSizeLimit()) {
            throw new RuntimeException("Team has reached maximum squad size of " + team.getSquadSizeLimit());
        }

        if (!team.getPlayers().contains(player)) {
            player.setTeam(team);
            team.getPlayers().add(player);
            team.setLastUpdated(LocalDateTime.now());
            return teamRepository.save(team);
        }
        return team;
    }

    @Override
    @Transactional
    public Team removePlayerFromTeam(Long teamId, Long playerId) {
        Team team = getTeamById(teamId);
        Users player = userRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + playerId));

        if (team.getPlayers().remove(player)) {
            player.setTeam(null);
            team.setLastUpdated(LocalDateTime.now());
            return teamRepository.save(team);
        }
        return team;
    }

    @Override
    public List<Users> getTeamPlayers(Long teamId) {
        Team team = getTeamById(teamId);
        return team.getPlayers();
    }

    @Override
    public Users createUser(Users users) {
        return userRepository.save(users);
    }




    @Override
    public Page<Team> getAllTeamsPaginated(int page, int size) {
        return teamRepository.findAll(PageRequest.of(page, size));
    }

}

