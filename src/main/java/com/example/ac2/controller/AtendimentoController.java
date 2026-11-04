package com.example.ac2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.ac2.dto.AtendimentoRequest;
import com.example.ac2.dto.AtendimentoResponse;
import com.example.ac2.service.interfaces.AtendimentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {

    private final AtendimentoService service;

    public AtendimentoController(AtendimentoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AtendimentoResponse registrar(
            @Valid @RequestBody AtendimentoRequest request) {
        return service.registrar(request);
    }

    @GetMapping("/{id}")
    public AtendimentoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @GetMapping("/animal/{animalId}")
    public List<AtendimentoResponse> historico(
            @PathVariable Long animalId) {
        return service.historicoAnimal(animalId);
    }

    @GetMapping(
            value = "/{id}/imprimir",
            produces = "text/html;charset=UTF-8"
    )
    public String imprimir(@PathVariable Long id) {

        AtendimentoResponse a = service.buscar(id);

        return """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <title>Prontuário Veterinário</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            margin: 40px;
                        }
                        h1 {
                            text-align: center;
                        }
                        .campo {
                            margin-bottom: 20px;
                        }
                    </style>
                </head>
                <body>
                    <h1>PetCare - Prontuário Veterinário</h1>

                    <div class="campo">
                        <strong>Animal:</strong> %s
                    </div>

                    <div class="campo">
                        <strong>Diagnóstico:</strong> %s
                    </div>

                    <div class="campo">
                        <strong>Observações:</strong> %s
                    </div>

                    <div class="campo">
                        <strong>Orientações:</strong> %s
                    </div>

                    <div class="campo">
                        <strong>Data do registro:</strong> %s
                    </div>

                    <script>
                        window.print();
                    </script>
                </body>
                </html>
                """.formatted(
                a.animalNome(),
                a.diagnostico(),
                a.observacoes() == null ? "" : a.observacoes(),
                a.orientacoes() == null ? "" : a.orientacoes(),
                a.dataRegistro()
        );
    }
}
