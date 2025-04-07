package tn.esprit.microservice.tournoii.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import tn.esprit.microservice.tournoii.entity.MatchUpdateMessage; // <-- à créer dans un fichier à part

@Controller
public class WebSocketController {

    @MessageMapping("/match/update")
    @SendTo("/topic/matchUpdates")
    public MatchUpdateMessage sendMatchUpdate(MatchUpdateMessage message) {
        System.out.println("⚽ Message reçu via WebSocket : matchId=" + message.getMatchId() + ", status=" + message.getStatus());
        return message;
    }
}
