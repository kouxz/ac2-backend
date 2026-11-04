package com.example.ac2.dto;

import java.time.LocalDateTime;

public record AtendimentoResponse(
        Long id,
        Long animalId,
        String animalNome,
        Long consultaId,
        String diagnostico,
        String observacoes,
        String orientacoes,
        LocalDateTime dataRegistro
        ) {}
