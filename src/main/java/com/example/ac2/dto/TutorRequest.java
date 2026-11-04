package com.example.ac2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TutorRequest(
        @NotBlank
        String nome,
        @NotBlank
        @Pattern(regexp = "\\d{11}",
                message = "CPF deve conter 11 números")
        String cpf,
        @NotBlank
        String telefone,
        @NotBlank
        String endereco
        ) {}
