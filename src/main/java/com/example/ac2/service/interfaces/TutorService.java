package com.example.ac2.service.interfaces;

import java.util.List;

import com.example.ac2.dto.TutorRequest;
import com.example.ac2.dto.TutorResponse;

public interface TutorService {

    TutorResponse criar(TutorRequest request);

    List<TutorResponse> listar();

    TutorResponse buscar(Long id);

    TutorResponse atualizar(Long id, TutorRequest request);

    void excluir(Long id);
}
