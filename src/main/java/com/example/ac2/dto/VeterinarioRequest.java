package com.example.ac2.dto;

import java.util.Set;

import com.example.ac2.model.Especie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record VeterinarioRequest(
        @NotBlank
        String nome,
        @NotBlank
        String crmv,
        @NotEmpty
        Set<Especie> especiesHabilitadas
        ) {}
