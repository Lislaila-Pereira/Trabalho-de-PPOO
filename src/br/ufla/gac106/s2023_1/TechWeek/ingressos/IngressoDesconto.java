package br.ufla.gac106.s2023_1.TechWeek.ingressos;

import br.ufla.gac106.s2023_1.TechWeek.atividades.Atividade;

public class IngressoDesconto extends Ingresso {

    public IngressoDesconto(Atividade atividade, String nomeUsuario) {
        super(atividade, nomeUsuario);
    }

    @Override
    public double calculaValor(Atividade atividade) {
        return atividade.getPrecoIngresso() * 0.8;
    }

    @Override
    public String getTipo(){
        return "Ingresso Com Desconto!";
    }
}
