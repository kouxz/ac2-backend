package com.example.ac2.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ac2.dto.VacinaRequest;
import com.example.ac2.dto.VacinaResponse;
import com.example.ac2.exception.BusinessException;
import com.example.ac2.exception.ResourceNotFoundException;
import com.example.ac2.model.Animal;
import com.example.ac2.model.Vacina;
import com.example.ac2.repository.AnimalRepository;
import com.example.ac2.repository.VacinaRepository;
import com.example.ac2.service.interfaces.VacinaService;

@Service
@Transactional
public class VacinaServiceImpl implements VacinaService {

    private final VacinaRepository vacinas;
    private final AnimalRepository animais;

    public VacinaServiceImpl(
            VacinaRepository vacinas,
            AnimalRepository animais) {
        this.vacinas = vacinas;
        this.animais = animais;
    }

    private Animal obterAnimal(Long id) {
        return animais.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Animal não encontrado."));
    }

    private Vacina obterVacina(Long id) {
        return vacinas.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Vacina não encontrada."));
    }

    private VacinaResponse converter(Vacina v) {
        return new VacinaResponse(
                v.getId(),
                v.getAnimal().getId(),
                v.getAnimal().getNome(),
                v.getNome(),
                v.getDataAplicacao(),
                v.getLote()
        );
    }

    @Override
    public VacinaResponse registrar(VacinaRequest r) {

        if (r.dataAplicacao().isAfter(LocalDate.now())) {
            throw new BusinessException(
                    "A data de aplicação não pode ser futura."
            );
        }

        Vacina vacina = new Vacina();

        vacina.setAnimal(obterAnimal(r.animalId()));
        vacina.setNome(r.nome());
        vacina.setDataAplicacao(r.dataAplicacao());
        vacina.setLote(r.lote());

        return converter(vacinas.save(vacina));
    }

    @Override
    @Transactional(readOnly = true)
    public VacinaResponse buscar(Long id) {
        return converter(obterVacina(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VacinaResponse> listarPorAnimal(Long animalId) {

        obterAnimal(animalId);

        return vacinas.findByAnimalIdOrderByDataAplicacaoDesc(animalId)
                .stream()
                .map(this::converter)
                .toList();
    }
}
