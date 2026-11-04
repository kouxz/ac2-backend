package com.example.ac2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ac2.model.Veterinario;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

    Optional<Veterinario> findByCrmv(String crmv);

    boolean existsByCrmv(String crmv);
}
