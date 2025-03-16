package com.security.service;

import com.security.entity.Pokemon;
import com.security.repository.PokemonRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PokemonService {
    private final WebClient webClient;
    private final PokemonRepository pokemonRepository;

    public PokemonService(WebClient.Builder webClientBuilder, PokemonRepository pokemonRepository) {
        this.webClient = webClientBuilder.baseUrl("https://tyradex.vercel.app/api/v1").build();
        this.pokemonRepository = pokemonRepository;
    }

    public Mono<Pokemon> getPokemonByName(String name) {
        // Vérification dans la base de données
        Optional<Pokemon> existingPokemon = pokemonRepository.findByName(name);
        if (existingPokemon.isPresent()) {
            return Mono.just(existingPokemon.get()); // Si Pokémon trouvé en DB, retour direct
        }

        // Si Pokémon pas trouvé en base, appel de l'API externe
        return webClient.get()
                .uri("/pokemon/{name}", name)
                .retrieve()
                .bodyToMono(Map.class)
                .map(this::mapToPokemon) // Transformation des données de l'API en objet Pokemon
                .doOnNext(pokemon -> {
                    // Sauvegarde du Pokémon dans la base de données si ce n'est pas déjà fait
                    if (pokemon != null) {
                        pokemonRepository.save(pokemon);
                    }
                });
    }

    private Pokemon mapToPokemon(Map<String, Object> response) {
        Object name = null;
        Pokemon pokemon = new Pokemon(name);
        if (response.get("name") instanceof Map) {
            Map<String, String> nameMap = (Map<String, String>) response.get("name");
            pokemon.setName(nameMap.getOrDefault("fr", "Inconnu"));
        }

        if (response.get("types") instanceof List) {
            List<Map<String, Object>> typesList = (List<Map<String, Object>>) response.get("types");
            if (!typesList.isEmpty() && typesList.get(0).get("name") instanceof String) {
                pokemon.setType((String) typesList.get(0).get("name"));
            } else {
                pokemon.setType("Inconnu");
            }
        }

        if (response.get("pokedex_id") instanceof Integer) {
            pokemon.setBaseExperience((Integer) response.get("pokedex_id"));
        } else {
            pokemon.setBaseExperience(0);
        }

        return pokemon;
    }
    public List<Pokemon> getAllPokemons() {
        return pokemonRepository.findAll();
    }
    public Pokemon createPokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    public Pokemon updatePokemon(Long id, Pokemon updatedPokemon) {
        return pokemonRepository.findById(id)
                .map(pokemon -> {
                    pokemon.setName(updatedPokemon.getName());
                    pokemon.setType(updatedPokemon.getType());
                    pokemon.setBaseExperience(updatedPokemon.getBaseExperience());
                    return pokemonRepository.save(pokemon);
                })
                .orElseThrow(() -> new RuntimeException("Pokemon not found with id: " + id));
    }
    public void deletePokemon(Long id) {
        pokemonRepository.deleteById(id);
    }
}
