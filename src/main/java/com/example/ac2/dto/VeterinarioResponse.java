package com.example.ac2.dto;

import java.util.Set;

import com.example.ac2.model.Especie;

public record VeterinarioResponse(
        Long id,
        String nome,
        String crmv,
        Set<Especie> especiesHabilitadas
        ) {}
