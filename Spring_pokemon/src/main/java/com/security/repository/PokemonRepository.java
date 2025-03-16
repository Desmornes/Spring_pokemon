package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.security.entity.Pokemon;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Optional<Pokemon> findByName(String name);

}
