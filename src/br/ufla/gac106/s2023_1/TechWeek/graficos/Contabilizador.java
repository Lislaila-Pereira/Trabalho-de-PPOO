package br.ufla.gac106.s2023_1.TechWeek.graficos;

import br.ufla.gac106.s2023_1.base.relatorios.ContabilizadorIngressos;

public class Contabilizador implements ContabilizadorIngressos{
    private String identificador;
    private int quantidadeIngressos;
    private double valorTotal;

    public Contabilizador(String identificador, double valorTotal) {
        this.identificador = identificador;
        this.quantidadeIngressos = 1;
        this.valorTotal = valorTotal;
    }

    @Override
    public String identificador() {
        return identificador;
    }

    @Override
    public int quantidadeIngressos() {
        return quantidadeIngressos;
    }

    @Override
    public double valorTotal() {
        return valorTotal;
    }

    public void incrementarQuantidadeIngressos() {
        quantidadeIngressos++;
    }

    public void adicionarValorNoTotal(double valor) {
        valorTotal += valor;
    }
}
