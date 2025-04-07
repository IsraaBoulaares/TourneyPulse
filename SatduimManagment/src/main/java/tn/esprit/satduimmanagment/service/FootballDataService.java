package tn.esprit.satduimmanagment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;






@Service
public class FootballDataService {

    @Autowired
    private RestTemplate restTemplate;

    private final String API_TOKEN = "c644d5fafff144b2a9ced2eff5a1574b"; // Replace with your actual API token
    private final String BASE_URL = "https://api.football-data.org/v4";

    public String getMatches() {
        return sendGetRequest(BASE_URL + "/matches");
    }

    public String getAreas() {
        return sendGetRequest(BASE_URL + "/areas");
    }

    public String getCompetitions() {
        return sendGetRequest(BASE_URL + "/competitions");
    }

    private String sendGetRequest(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", API_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }
}
