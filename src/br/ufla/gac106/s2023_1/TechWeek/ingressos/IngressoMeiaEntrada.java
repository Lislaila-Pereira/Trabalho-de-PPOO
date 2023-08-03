package br.ufla.gac106.s2023_1.TechWeek.ingressos;

import br.ufla.gac106.s2023_1.TechWeek.atividades.Atividade;

public class IngressoMeiaEntrada extends Ingresso {

    public IngressoMeiaEntrada(Atividade atividade, String nomeUsuario) {
        super(atividade, nomeUsuario);
    }

    @Override
    public double calculaValor(Atividade atividade) {
        return atividade.getPrecoIngresso()/2;
    }

    @Override
    public String getTipo(){
        return "Ingresso Meia Entrada!";
    }
}
