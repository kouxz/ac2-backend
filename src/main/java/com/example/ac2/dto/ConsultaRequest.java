package com.example.ac2.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConsultaRequest(
        @NotNull
        Long animalId,
        @NotNull
        Long veterinarioId,
        @NotBlank
        String local,
        @NotNull
        LocalDate data,
        @NotNull
        LocalTime horario
        ) {}
