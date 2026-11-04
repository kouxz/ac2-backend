package com.example.ac2.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ac2.model.Consulta;
import com.example.ac2.model.StatusConsulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByVeterinarioIdAndDataAndHorarioAndStatusNot(
            Long veterinarioId,
            LocalDate data,
            LocalTime horario,
            StatusConsulta status
    );

    List<Consulta> findByAnimalIdOrderByDataDescHorarioDesc(Long animalId);

    List<Consulta> findByVeterinarioIdAndData(Long veterinarioId, LocalDate data);
}
