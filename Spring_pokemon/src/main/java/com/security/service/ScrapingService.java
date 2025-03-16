package com.security.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ScrapingService {

    private static final String BASE_URL = "https://pokemondb.net/pokedex/";

    public String getPokemonData(String pokemonName) {
        try {
            // Construire l'URL complète pour le Pokémon spécifié
            String url = BASE_URL + pokemonName.toLowerCase();

            // Se connecter au site avec un User-Agent pour simuler un navigateur
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                    .get();

            // Extraire le nom du Pokémon
            Element nameElement = document.selectFirst("h1");
            String name = nameElement != null ? nameElement.text() : "Nom non trouvé";

            // Extraire le type du Pokémon
            Element typeElement = document.selectFirst("table.vitals-table tr:contains(Type) td");
            String type = typeElement != null ? typeElement.text() : "Type non trouvé";

            // Extraire les statistiques du Pokémon
            Element statsTable = document.selectFirst("table.vitals-table");
            String stats = statsTable != null ? statsTable.text() : "Statistiques non trouvées";

            // Retourner les informations formatées
            return String.format("Nom : %s\nType : %s\nStatistiques :\n%s", name, type, stats);

        } catch (IOException e) {
            return "Erreur lors de la récupération des données : " + e.getMessage();
        }
    }
}
