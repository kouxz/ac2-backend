package com.example.ac2.service.interfaces;

import java.util.List;

import com.example.ac2.dto.AtendimentoRequest;
import com.example.ac2.dto.AtendimentoResponse;

public interface AtendimentoService {

    AtendimentoResponse registrar(AtendimentoRequest request);

    AtendimentoResponse buscar(Long id);

    List<AtendimentoResponse> historicoAnimal(Long animalId);
}
