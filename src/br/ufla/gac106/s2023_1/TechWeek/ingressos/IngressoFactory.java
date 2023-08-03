package br.ufla.gac106.s2023_1.TechWeek.ingressos;

import java.util.ArrayList;
import java.util.List;

import br.ufla.gac106.s2023_1.TechWeek.atividades.Atividade;

public abstract class IngressoFactory {
    /**
     * Cria uma lista de ingressos com base nos parâmetros fornecidos.
     * 
     * @param nome             O nome do comprador dos ingressos.
     * @param quantidadeComum  A quantidade de ingressos comuns a serem criados.
     * @param quantidadeMeia   A quantidade de ingressos de meia-entrada a serem criados.
     * @param quantidadeDesconto A quantidade de ingressos com desconto a serem criados.
     * @param atividade        A atividade para a qual os ingressos estão sendo criados.
     * @return Uma lista contendo os ingressos criados.
     * @throws IllegalArgumentException se alguma quantidade for negativa ou se a atividade for nula.
     */
    public static List<Ingresso> criarIngressos(String nome, int quantidadeComum, int quantidadeMeia, int quantidadeDesconto, Atividade atividade){
        List<Ingresso> ingressosComprados = new ArrayList<>();

        for(int i = 0; i < quantidadeComum; i++){
            ingressosComprados.add(new IngressoComum(atividade, nome));
        }
        for(int i = 0; i < quantidadeMeia; i++){
            ingressosComprados.add(new IngressoMeiaEntrada(atividade, nome));
        }
        for(int i = 0; i < quantidadeDesconto; i++){
            ingressosComprados.add(new IngressoDesconto(atividade, nome));
        }
        return ingressosComprados;
    }
     
}
