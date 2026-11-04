package com.example.ac2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ac2.model.Atendimento;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    List<Atendimento> findByAnimalIdOrderByDataRegistroDesc(Long animalId);

    boolean existsByConsultaId(Long consultaId);
}
