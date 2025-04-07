package tn.esprit.gestionequipe.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ChatbotService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public ChatbotService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://generativelanguage.googleapis.com").build();
    }

    public Mono<String> getChatResponse(String prompt) {
        return webClient.post()
                .uri(apiUrl + "?key=" + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"contents\": [{\"parts\": [{\"text\": \"" + prompt + "\"}]}]}")
                .retrieve()
                .bodyToMono(String.class)
                .map(this::extractTextFromResponse)
                .onErrorResume(e -> Mono.just("Error: Could not get response from Gemini API - " + e.getMessage()));
    }

    private String extractTextFromResponse(String response) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            // Adjust the path based on the actual Gemini API response structure
            JsonNode textNode = rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text");
            return textNode != null ? textNode.asText() : "No text found in response";
        } catch (Exception e) {
            return "Error parsing response: " + e.getMessage();
        }
    }


}