package br.ufla.gac106.s2023_1.TechWeek.atividades;

import java.io.Serializable;

public abstract class Atividade implements Serializable{
    //Participação na semana: identificação da atividade, semana(evento), tipo de atividade (palestra ou minicurso), local, dia/horário
    private String nome; 
    private Evento evento; 
    private TipoAtividade tipoAtividade; 
    private LocalAcademico localAcademico; 
    private String dataInicio;
    private String horaInicio;
    private double precoIngresso;
    private final int maxIngressos;
    private int ingressosDisponiveis;
         
    public Atividade(String nome, Evento evento, TipoAtividade tipoAtividade, LocalAcademico localAcademico,
        String dataInicio, String horaInicio, double precoIngresso, int maxIngressoAtividade) {
        this.nome = nome;
        this.evento = evento;
        this.tipoAtividade = tipoAtividade;
        this.localAcademico = localAcademico;
        this.dataInicio = dataInicio;
        this.horaInicio = horaInicio;
        this.precoIngresso = precoIngresso;
        this.maxIngressos = calculaMaxIngresso(localAcademico, maxIngressoAtividade);
        this.ingressosDisponiveis = maxIngressos;
    }

    public String getNome() {
        return nome;
    }

    public Evento getEvento() {
        return evento;
    }

    public String getTipoAtividade() {
        if(tipoAtividade == TipoAtividade.PALESTRA)
            return "Palestra";
        return "Minicurso";
    }

    public LocalAcademico getLocalAcademico() {
        return localAcademico;
    }

    public String getDataInicio() {
        return dataInicio;
    }
    
    public String getHoraInicio() {
        return horaInicio;
    }

    public Double getPrecoIngresso() {
        return precoIngresso;
    }

    public int getMaxIngresso(){
        return maxIngressos;
    }

    public int getIngressosDisponiveis(){
        return ingressosDisponiveis;
    }

    public void atualizarIngressosDisponiveis(){
        ingressosDisponiveis-=1;
    }

    public void adicionarIngressos(int quantidade) {
        if(ingressosDisponiveis + quantidade < maxIngressos){
            ingressosDisponiveis+= quantidade;
        } else {
            ingressosDisponiveis = maxIngressos;
        }
    }
    
    private int calculaMaxIngresso(LocalAcademico localAcademico, int maxIngressoAtividade) {
        if(maxIngressoAtividade > localAcademico.getCapacidade()){
           return localAcademico.getCapacidade();
        }
        return maxIngressoAtividade;
    }

    public abstract String getDescricao();
}
