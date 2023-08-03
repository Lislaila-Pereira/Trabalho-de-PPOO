package br.ufla.gac106.s2023_1.TechWeek.ingressos;

import br.ufla.gac106.s2023_1.TechWeek.atividades.Atividade;
import br.ufla.gac106.s2023_1.base.relatorios.ContabilizadorIngressos;

public abstract class Ingresso implements ContabilizadorIngressos {
    private Atividade atividade;
    private String nomeComprador;
    private double valorIngresso;
    private static final int taxa = 10; //todos os ingressos terao uma taxa de 10 reais

    public Ingresso(Atividade atividade, String nomeComprador){
        this.atividade = atividade;
        this.nomeComprador = nomeComprador;
        this.valorIngresso = calculaValor(atividade);
        this.valorIngresso += taxa;
        atividade.atualizarIngressosDisponiveis();
    }

    public abstract double calculaValor(Atividade atividade);

    public Atividade getAtividade(){
        return atividade;
    }
    
    public String getComprador(){
        return nomeComprador;
    }
    
    public double getValor(){
        return valorIngresso;
    };

    public abstract String getTipo();

    @Override
    public String identificador() {
        return getAtividade().getEvento().getNome();
    }

    @Override
    public int quantidadeIngressos() {
        return 1;
    }

    @Override
    public double valorTotal() {
        return getValor();
    }
}
