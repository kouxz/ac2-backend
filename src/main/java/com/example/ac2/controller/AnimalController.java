package com.example.ac2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.ac2.dto.AnimalRequest;
import com.example.ac2.dto.AnimalResponse;
import com.example.ac2.service.interfaces.AnimalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalService service;

    public AnimalController(AnimalService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalResponse criar(@Valid @RequestBody AnimalRequest request) {
        return service.criar(request);
    }

    @GetMapping
    public List<AnimalResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AnimalResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @GetMapping("/tutor/{tutorId}")
    public List<AnimalResponse> listarPorTutor(@PathVariable Long tutorId) {
        return service.listarPorTutor(tutorId);
    }

    @PutMapping("/{id}")
    public AnimalResponse atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AnimalRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
