package com.example.ac2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtendimentoRequest(
        @NotNull
        Long animalId,
        Long consultaId,
        @NotBlank
        String diagnostico,
        String observacoes,
        String orientacoes
        ) {}
