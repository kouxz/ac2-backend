package com.example.ac2.service.impl;

import com.example.ac2.dto.*;
import com.example.ac2.exception.*;
import com.example.ac2.model.Tutor;
import com.example.ac2.repository.TutorRepository;
import com.example.ac2.service.interfaces.TutorService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TutorServiceImpl implements TutorService {

    private final TutorRepository repository;

    public TutorServiceImpl(TutorRepository repository) {
        this.repository = repository;
    }

    private Tutor obter(Long id) {
        return repository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Tutor não encontrado: " + id));
    }

    private TutorResponse converter(Tutor t) {
        return new TutorResponse(
                t.getId(),
                t.getNome(),
                t.getCpf(),
                t.getTelefone(),
                t.getEndereco()
        );
    }

    @Override
    public TutorResponse criar(TutorRequest r) {

        if (repository.existsByCpf(r.cpf())) {
            throw new BusinessException("CPF já cadastrado.");
        }

        Tutor tutor = new Tutor(
                r.nome(),
                r.cpf(),
                r.telefone(),
                r.endereco()
        );

        return converter(repository.save(tutor));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TutorResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TutorResponse buscar(Long id) {
        return converter(obter(id));
    }

    @Override
    public TutorResponse atualizar(Long id, TutorRequest r) {

        Tutor tutor = obter(id);

        if (!tutor.getCpf().equals(r.cpf())
                && repository.existsByCpf(r.cpf())) {
            throw new BusinessException("CPF já cadastrado.");
        }

        tutor.setNome(r.nome());
        tutor.setCpf(r.cpf());
        tutor.setTelefone(r.telefone());
        tutor.setEndereco(r.endereco());

        return converter(repository.save(tutor));
    }

    @Override
    public void excluir(Long id) {
        repository.delete(obter(id));
    }
}
