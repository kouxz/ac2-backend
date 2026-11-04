package com.example.ac2.service.interfaces;

import java.util.List;

import com.example.ac2.dto.VeterinarioRequest;
import com.example.ac2.dto.VeterinarioResponse;

public interface VeterinarioService {

    VeterinarioResponse criar(VeterinarioRequest request);

    List<VeterinarioResponse> listar();

    VeterinarioResponse buscar(Long id);

    VeterinarioResponse atualizar(Long id, VeterinarioRequest request);

    void excluir(Long id);
}
