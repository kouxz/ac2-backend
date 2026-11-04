package com.example.ac2.service.interfaces;

import java.util.List;

import com.example.ac2.dto.AnimalRequest;
import com.example.ac2.dto.AnimalResponse;

public interface AnimalService {

    AnimalResponse criar(AnimalRequest request);

    List<AnimalResponse> listar();

    AnimalResponse buscar(Long id);

    List<AnimalResponse> listarPorTutor(Long tutorId);

    AnimalResponse atualizar(Long id, AnimalRequest request);

    void excluir(Long id);
}
