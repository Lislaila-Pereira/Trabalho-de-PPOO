package br.ufla.gac106.s2023_1.TechWeek.atividades;

import java.io.Serializable;

public class Evento implements Serializable{
    private String nome;
    private String tema;
    private String universidade;
    private int ano;


    public Evento(String nome, String tema, String universidade, int ano) {
        this.nome = nome;
        this.tema = tema;
        this.universidade = universidade;
        this.ano = ano;
    }

    public String getNome() {
        return nome;
    }

    public String getTema() {
        return tema;
    }
   
    public String getUniversidade() {
        return universidade;
    }

    public int getAno() {
        return ano;
    } 
    
    public String getDescricao(){
        return "O evento " + getNome() + " com o tema " + getTema() + " aconteceu no(a) " + getUniversidade() + " no ano " + getAno();
    }
}
