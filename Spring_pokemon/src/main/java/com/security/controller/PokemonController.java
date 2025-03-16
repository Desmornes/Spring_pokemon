package com.security.controller;

import com.security.entity.Pokemon;
import com.security.service.PokemonService;
import com.security.service.ScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;
    private final ScrapingService scrapingService;

    @Autowired
    public PokemonController(PokemonService pokemonService, ScrapingService scrapingService) {
        this.pokemonService = pokemonService;
        this.scrapingService = scrapingService;
    }

    @GetMapping("/{name}")
    public Mono<Pokemon> getPokemon(@PathVariable String name) {
        return pokemonService.getPokemonByName(name);
    }
    @PreAuthorize("permitAll()")
    @GetMapping
    public List<Pokemon> getAllPokemons() {
        return pokemonService.getAllPokemons();
    }

    @PostMapping
    public ResponseEntity<Pokemon> createPokemon(@RequestBody Pokemon pokemon) {
        Pokemon createdPokemon = pokemonService.createPokemon(pokemon);
        return ResponseEntity.ok(createdPokemon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pokemon> updatePokemon(@PathVariable Long id, @RequestBody Pokemon pokemon) {
        Pokemon updatedPokemon = pokemonService.updatePokemon(id, pokemon);
        return ResponseEntity.ok(updatedPokemon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePokemon(@PathVariable Long id) {
        pokemonService.deletePokemon(id);
        return ResponseEntity.ok("Pokemon deleted successfully");
    }

    @GetMapping("/{name}/scraping")
    public ResponseEntity<String> scrapePokemonData(@PathVariable String name) {
        String pokemonData = scrapingService.getPokemonData(name);
        return ResponseEntity.ok(pokemonData);
    }
}
