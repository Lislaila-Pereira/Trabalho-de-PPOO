package br.ufla.gac106.s2023_1.TechWeek.ingressos;

import br.ufla.gac106.s2023_1.TechWeek.atividades.Atividade;

public class IngressoComum extends Ingresso {

    public IngressoComum(Atividade atividade, String nomeUsuario) {
        super(atividade, nomeUsuario);
    }

    @Override
    public double calculaValor(Atividade atividade) {
        return atividade.getPrecoIngresso();
    }

    @Override
    public String getTipo(){
        return "Ingresso Comum!";
    }
}
