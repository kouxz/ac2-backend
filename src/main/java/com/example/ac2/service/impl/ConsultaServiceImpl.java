package com.example.ac2.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ac2.dto.ConsultaRequest;
import com.example.ac2.dto.ConsultaResponse;
import com.example.ac2.exception.BusinessException;
import com.example.ac2.exception.ResourceNotFoundException;
import com.example.ac2.model.Animal;
import com.example.ac2.model.Consulta;
import com.example.ac2.model.StatusConsulta;
import com.example.ac2.model.Veterinario;
import com.example.ac2.repository.AnimalRepository;
import com.example.ac2.repository.ConsultaRepository;
import com.example.ac2.repository.VeterinarioRepository;
import com.example.ac2.service.interfaces.ConsultaService;

@Service
@Transactional
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultas;
    private final AnimalRepository animais;
    private final VeterinarioRepository veterinarios;

    public ConsultaServiceImpl(
            ConsultaRepository consultas,
            AnimalRepository animais,
            VeterinarioRepository veterinarios) {
        this.consultas = consultas;
        this.animais = animais;
        this.veterinarios = veterinarios;
    }

    private Animal obterAnimal(Long id) {
        return animais.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Animal não encontrado."));
    }

    private Veterinario obterVeterinario(Long id) {
        return veterinarios.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Veterinário não encontrado."));
    }

    private Consulta obterConsulta(Long id) {
        return consultas.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Consulta não encontrada."));
    }

    private ConsultaResponse converter(Consulta c) {
        return new ConsultaResponse(
                c.getId(),
                c.getAnimal().getId(),
                c.getAnimal().getNome(),
                c.getVeterinario().getId(),
                c.getVeterinario().getNome(),
                c.getLocal(),
                c.getData(),
                c.getHorario(),
                c.getStatus()
        );
    }

    @Override
    public ConsultaResponse agendar(ConsultaRequest r) {

        Animal animal = obterAnimal(r.animalId());

        Veterinario veterinario
                = obterVeterinario(r.veterinarioId());

        if (!veterinario.getEspeciesHabilitadas()
                .contains(animal.getEspecie())) {

            throw new BusinessException(
                    "Veterinário não habilitado para atender esta espécie."
            );
        }

        if (r.data().isBefore(LocalDate.now())) {
            throw new BusinessException(
                    "Não é permitido agendar consultas em datas passadas."
            );
        }

        boolean horarioOcupado
                = consultas.existsByVeterinarioIdAndDataAndHorarioAndStatusNot(
                        veterinario.getId(),
                        r.data(),
                        r.horario(),
                        StatusConsulta.CANCELADA
                );

        if (horarioOcupado) {
            throw new BusinessException(
                    "Veterinário já possui consulta neste horário."
            );
        }

        Consulta consulta = new Consulta();

        consulta.setAnimal(animal);
        consulta.setVeterinario(veterinario);
        consulta.setLocal(r.local());
        consulta.setData(r.data());
        consulta.setHorario(r.horario());
        consulta.setStatus(StatusConsulta.AGENDADA);

        return converter(consultas.save(consulta));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultaResponse> listar() {
        return consultas.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ConsultaResponse buscar(Long id) {
        return converter(obterConsulta(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultaResponse> listarPorAnimal(Long animalId) {

        obterAnimal(animalId);

        return consultas.findByAnimalIdOrderByDataDescHorarioDesc(animalId)
                .stream()
                .map(this::converter)
                .toList();
    }

    @Override
    public ConsultaResponse cancelar(Long id) {

        Consulta consulta = obterConsulta(id);

        if (consulta.getStatus() == StatusConsulta.REALIZADA) {
            throw new BusinessException(
                    "Não é possível cancelar uma consulta realizada."
            );
        }

        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new BusinessException(
                    "Esta consulta já está cancelada."
            );
        }

        consulta.setStatus(StatusConsulta.CANCELADA);

        return converter(consultas.save(consulta));
    }
}
