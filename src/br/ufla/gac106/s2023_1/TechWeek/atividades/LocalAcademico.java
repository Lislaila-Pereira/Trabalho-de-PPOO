package br.ufla.gac106.s2023_1.TechWeek.atividades;

import java.io.Serializable;

public class LocalAcademico implements Serializable{
    private String nome; 
    private String departamento; 
    private int capacidade;

    public LocalAcademico(String nome, String departamento, int capacidade) {
        this.departamento = departamento;
        this.nome = nome;
        this.capacidade = capacidade;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getDepartamento() {
        return departamento;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public String getDescricao(){
        return "O " + getNome() + " localizado no " + getDepartamento() + " tem capacidade para " + getCapacidade() + " pessoas";
    }
}
