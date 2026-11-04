package com.example.ac2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.ac2.dto.ConsultaRequest;
import com.example.ac2.dto.ConsultaResponse;
import com.example.ac2.service.interfaces.ConsultaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse agendar(@Valid @RequestBody ConsultaRequest request) {
        return service.agendar(request);
    }

    @GetMapping
    public List<ConsultaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ConsultaResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @GetMapping("/animal/{animalId}")
    public List<ConsultaResponse> listarPorAnimal(
            @PathVariable Long animalId) {
        return service.listarPorAnimal(animalId);
    }

    @PutMapping("/{id}/cancelar")
    public ConsultaResponse cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }
}
