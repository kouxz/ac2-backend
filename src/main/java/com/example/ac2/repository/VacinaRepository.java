package com.example.ac2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ac2.model.Vacina;

public interface VacinaRepository extends JpaRepository<Vacina, Long> {

    List<Vacina> findByAnimalIdOrderByDataAplicacaoDesc(Long animalId);
}
