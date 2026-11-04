package com.example.ac2.dto;

import java.time.LocalDate;

import com.example.ac2.model.Especie;

public record AnimalResponse(
        Long id,
        String nome,
        Especie especie,
        String raca,
        LocalDate dataNascimento,
        Long tutorId,
        String tutorNome
        ) {}
