package com.example.ac2.dto;

public record TutorResponse(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String endereco
        ) {}
