package tn.esprit.microservice.tournoii.entity;



public class MatchUpdateMessage {
    private Long matchId;
    private String status;

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
