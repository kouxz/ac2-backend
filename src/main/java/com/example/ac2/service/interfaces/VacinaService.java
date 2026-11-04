package com.example.ac2.service.interfaces;

import java.util.List;

import com.example.ac2.dto.VacinaRequest;
import com.example.ac2.dto.VacinaResponse;

public interface VacinaService {

    VacinaResponse registrar(VacinaRequest request);

    VacinaResponse buscar(Long id);

    List<VacinaResponse> listarPorAnimal(Long animalId);
}
