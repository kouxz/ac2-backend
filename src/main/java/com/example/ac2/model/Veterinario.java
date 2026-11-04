package com.example.ac2.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "veterinarios")
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String crmv;

    @ElementCollection(targetClass = Especie.class)
    @CollectionTable(
            name = "veterinario_especies",
            joinColumns = @JoinColumn(name = "veterinario_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "especie")
    private Set<Especie> especiesHabilitadas = new HashSet<>();

    public Veterinario() {
    }

    public Veterinario(String nome, String crmv,
            Set<Especie> especiesHabilitadas) {
        this.nome = nome;
        this.crmv = crmv;
        this.especiesHabilitadas = especiesHabilitadas;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public Set<Especie> getEspeciesHabilitadas() {
        return especiesHabilitadas;
    }

    public void setEspeciesHabilitadas(Set<Especie> especiesHabilitadas) {
        this.especiesHabilitadas = especiesHabilitadas;
    }
}
