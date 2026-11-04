package com.example.ac2.dto;

import java.time.LocalDate;

public record VacinaResponse(
        Long id,
        Long animalId,
        String animalNome,
        String nome,
        LocalDate dataAplicacao,
        String lote
        ) {}
