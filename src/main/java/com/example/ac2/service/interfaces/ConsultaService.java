package com.example.ac2.service.interfaces;

import java.util.List;

import com.example.ac2.dto.ConsultaRequest;
import com.example.ac2.dto.ConsultaResponse;

public interface ConsultaService {

    ConsultaResponse agendar(ConsultaRequest request);

    List<ConsultaResponse> listar();

    ConsultaResponse buscar(Long id);

    List<ConsultaResponse> listarPorAnimal(Long animalId);

    ConsultaResponse cancelar(Long id);
}
