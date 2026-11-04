package com.example.ac2.service.impl;

import com.example.ac2.dto.*;
import com.example.ac2.exception.ResourceNotFoundException;
import com.example.ac2.model.Animal;
import com.example.ac2.model.Tutor;
import com.example.ac2.repository.AnimalRepository;
import com.example.ac2.repository.TutorRepository;
import com.example.ac2.service.interfaces.AnimalService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animais;
    private final TutorRepository tutores;

    public AnimalServiceImpl(
            AnimalRepository animais,
            TutorRepository tutores) {
        this.animais = animais;
        this.tutores = tutores;
    }

    private Tutor obterTutor(Long id) {
        return tutores.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Tutor não encontrado."));
    }

    private Animal obterAnimal(Long id) {
        return animais.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Animal não encontrado."));
    }

    private AnimalResponse converter(Animal a) {
        return new AnimalResponse(
                a.getId(),
                a.getNome(),
                a.getEspecie(),
                a.getRaca(),
                a.getDataNascimento(),
                a.getTutor().getId(),
                a.getTutor().getNome()
        );
    }

    @Override
    public AnimalResponse criar(AnimalRequest r) {

        Animal animal = new Animal(
                r.nome(),
                r.especie(),
                r.raca(),
                r.dataNascimento(),
                obterTutor(r.tutorId())
        );

        return converter(animais.save(animal));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnimalResponse> listar() {
        return animais.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AnimalResponse buscar(Long id) {
        return converter(obterAnimal(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnimalResponse> listarPorTutor(Long tutorId) {

        obterTutor(tutorId);

        return animais.findByTutorId(tutorId)
                .stream()
                .map(this::converter)
                .toList();
    }

    @Override
    public AnimalResponse atualizar(Long id, AnimalRequest r) {

        Animal animal = obterAnimal(id);

        animal.setNome(r.nome());
        animal.setEspecie(r.especie());
        animal.setRaca(r.raca());
        animal.setDataNascimento(r.dataNascimento());
        animal.setTutor(obterTutor(r.tutorId()));

        return converter(animais.save(animal));
    }

    @Override
    public void excluir(Long id) {
        animais.delete(obterAnimal(id));
    }
}
