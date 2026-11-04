package com.example.ac2.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.ac2.model.StatusConsulta;

public record ConsultaResponse(
        Long id,
        Long animalId,
        String animalNome,
        Long veterinarioId,
        String veterinarioNome,
        String local,
        LocalDate data,
        LocalTime horario,
        StatusConsulta status
        ) {}
