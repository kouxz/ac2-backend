package com.example.ac2.dto;

import java.time.LocalDate;

import com.example.ac2.model.Especie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnimalRequest(
        @NotBlank
        String nome,
        @NotNull
        Especie especie,
        @NotBlank
        String raca,
        LocalDate dataNascimento,
        @NotNull
        Long tutorId
        ) {}
