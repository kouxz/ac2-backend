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

import com.example.ac2.dto.VacinaRequest;
import com.example.ac2.dto.VacinaResponse;
import com.example.ac2.service.interfaces.VacinaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vacinas")
public class VacinaController {

    private final VacinaService service;

    public VacinaController(VacinaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VacinaResponse registrar(
            @Valid @RequestBody VacinaRequest request) {
        return service.registrar(request);
    }

    @GetMapping("/{id}")
    public VacinaResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @GetMapping("/animal/{animalId}")
    public List<VacinaResponse> listarPorAnimal(
            @PathVariable Long animalId) {
        return service.listarPorAnimal(animalId);
    }
}
