package com.evenement.evenement.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

@Service
public class OpenCageService {

    private final String API_KEY = "8068e8d1a46a4f6ba3c473dc926f9bc8";
    private final String BASE_URL = "https://api.opencagedata.com/geocode/v1/json";

    public String getLocationName(double latitude, double longitude) {
        RestTemplate restTemplate = new RestTemplate();

        // Construire l'URL avec les paramètres nécessaires
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("q", latitude + "," + longitude)
                .queryParam("key", API_KEY)
                .toUriString();

        // Appeler l'API
        String response = restTemplate.getForObject(url, String.class);

        try {
            // Traiter la réponse JSON
            JSONObject json = new JSONObject(response);

            // Vérifier si des résultats sont retournés
            JSONArray results = json.getJSONArray("results");
            if (results.length() == 0) {
                return "Aucun résultat trouvé.";
            }

            // Récupérer le premier résultat et formater l'adresse
            JSONObject result = results.getJSONObject(0);
            String formatted = result.getString("formatted");

            return formatted;
        } catch (JSONException e) {
            e.printStackTrace();
            return "Erreur lors du traitement de la réponse de l'API.";
        }
    }
}
