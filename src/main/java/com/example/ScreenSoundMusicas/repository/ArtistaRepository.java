package com.example.ScreenSoundMusicas.repository;

import com.example.ScreenSoundMusicas.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    Optional<Artista> findById(Long id);
}
