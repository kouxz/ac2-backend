package com.example.ac2.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VacinaRequest(
        @NotNull
        Long animalId,
        @NotBlank
        String nome,
        @NotNull
        LocalDate dataAplicacao,
        @NotBlank
        String lote
        ) {}
