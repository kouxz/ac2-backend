package com.example.ac2.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ac2.dto.AtendimentoRequest;
import com.example.ac2.dto.AtendimentoResponse;
import com.example.ac2.exception.BusinessException;
import com.example.ac2.exception.ResourceNotFoundException;
import com.example.ac2.model.Animal;
import com.example.ac2.model.Atendimento;
import com.example.ac2.model.Consulta;
import com.example.ac2.model.StatusConsulta;
import com.example.ac2.repository.AnimalRepository;
import com.example.ac2.repository.AtendimentoRepository;
import com.example.ac2.repository.ConsultaRepository;
import com.example.ac2.service.interfaces.AtendimentoService;

@Service
@Transactional
public class AtendimentoServiceImpl implements AtendimentoService {

    private final AtendimentoRepository atendimentos;
    private final AnimalRepository animais;
    private final ConsultaRepository consultas;

    public AtendimentoServiceImpl(
            AtendimentoRepository atendimentos,
            AnimalRepository animais,
            ConsultaRepository consultas) {
        this.atendimentos = atendimentos;
        this.animais = animais;
        this.consultas = consultas;
    }

    private Animal obterAnimal(Long id) {
        return animais.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Animal não encontrado."));
    }

    private Atendimento obterAtendimento(Long id) {
        return atendimentos.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Atendimento não encontrado."));
    }

    private AtendimentoResponse converter(Atendimento a) {
        return new AtendimentoResponse(
                a.getId(),
                a.getAnimal().getId(),
                a.getAnimal().getNome(),
                a.getConsulta() == null ? null : a.getConsulta().getId(),
                a.getDiagnostico(),
                a.getObservacoes(),
                a.getOrientacoes(),
                a.getDataRegistro()
        );
    }

    @Override
    public AtendimentoResponse registrar(AtendimentoRequest r) {

        Animal animal = obterAnimal(r.animalId());

        Consulta consulta = null;

        if (r.consultaId() != null) {

            consulta = consultas.findById(r.consultaId())
                    .orElseThrow(()
                            -> new ResourceNotFoundException("Consulta não encontrada."));

            if (!consulta.getAnimal().getId().equals(animal.getId())) {
                throw new BusinessException(
                        "A consulta não pertence ao animal informado."
                );
            }

            if (consulta.getStatus() == StatusConsulta.CANCELADA) {
                throw new BusinessException(
                        "Não é possível atender uma consulta cancelada."
                );
            }

            if (atendimentos.existsByConsultaId(consulta.getId())) {
                throw new BusinessException(
                        "Esta consulta já possui atendimento registrado."
                );
            }

            consulta.setStatus(StatusConsulta.REALIZADA);
        }

        Atendimento atendimento = new Atendimento();

        atendimento.setAnimal(animal);
        atendimento.setConsulta(consulta);
        atendimento.setDiagnostico(r.diagnostico());
        atendimento.setObservacoes(r.observacoes());
        atendimento.setOrientacoes(r.orientacoes());

        return converter(atendimentos.save(atendimento));
    }

    @Override
    @Transactional(readOnly = true)
    public AtendimentoResponse buscar(Long id) {
        return converter(obterAtendimento(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AtendimentoResponse> historicoAnimal(Long animalId) {

        obterAnimal(animalId);

        return atendimentos.findByAnimalIdOrderByDataRegistroDesc(animalId)
                .stream()
                .map(this::converter)
                .toList();
    }
}
