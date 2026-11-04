package com.example.ac2.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ac2.dto.VeterinarioRequest;
import com.example.ac2.dto.VeterinarioResponse;
import com.example.ac2.exception.BusinessException;
import com.example.ac2.exception.ResourceNotFoundException;
import com.example.ac2.model.Veterinario;
import com.example.ac2.repository.VeterinarioRepository;
import com.example.ac2.service.interfaces.VeterinarioService;

@Service
@Transactional
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository repository;

    public VeterinarioServiceImpl(VeterinarioRepository repository) {
        this.repository = repository;
    }

    private Veterinario obter(Long id) {
        return repository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Veterinário não encontrado."));
    }

    private VeterinarioResponse converter(Veterinario v) {
        return new VeterinarioResponse(
                v.getId(),
                v.getNome(),
                v.getCrmv(),
                v.getEspeciesHabilitadas()
        );
    }

    @Override
    public VeterinarioResponse criar(VeterinarioRequest r) {

        if (repository.existsByCrmv(r.crmv())) {
            throw new BusinessException("CRMV já cadastrado.");
        }

        Veterinario veterinario = new Veterinario(
                r.nome(),
                r.crmv(),
                new HashSet<>(r.especiesHabilitadas())
        );

        return converter(repository.save(veterinario));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VeterinarioResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VeterinarioResponse buscar(Long id) {
        return converter(obter(id));
    }

    @Override
    public VeterinarioResponse atualizar(Long id, VeterinarioRequest r) {

        Veterinario v = obter(id);

        if (!v.getCrmv().equals(r.crmv())
                && repository.existsByCrmv(r.crmv())) {
            throw new BusinessException("CRMV já cadastrado.");
        }

        v.setNome(r.nome());
        v.setCrmv(r.crmv());
        v.setEspeciesHabilitadas(
                new HashSet<>(r.especiesHabilitadas())
        );

        return converter(repository.save(v));
    }

    @Override
    public void excluir(Long id) {
        repository.delete(obter(id));
    }
}
