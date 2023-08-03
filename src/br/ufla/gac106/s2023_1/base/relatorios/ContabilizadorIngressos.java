package br.ufla.gac106.s2023_1.base.relatorios;

import java.io.Serializable;

/**
 * Interface para representar um contabilizador de Ingressos
 * 
 * Ela serve, basicamente, para representar algo que tenha uma quantidade de ingressos e um valor total associado.
 * 
 * Poderia ser, por exemplo: 
 * - um filme, afinal um filme pode ter uma certa quantidade de ingressos vendidos e o valor arrecadado com eles.
 * - poderia ser também, um comprador, afinal um comprador pode ter comprado uma certa quantidade de ingressos
 *   e ter gasto um valor total com a compra deles.
 */
public interface ContabilizadorIngressos extends Serializable{
    /**
     * Identificador que indica a que se refere a contabilização. 
     * Ou seja, a quantidade de ingressos vendidos/comprados é de que?
     * 
     * Exemplos:
     * - Se for de um evento: o identificador poderia ser nome de um filme ou de um campeonato, por exemplo.
     * - Se for de um comprador: o identificador seria o nome do comprador
     */
    String identificador();

    /**
     * Quantidade de ingressos vendidos ou comprados
     */
    int quantidadeIngressos();

    /**
     * Valor total arrecadado ou gasto com a venda de ingressos
     */
    double valorTotal();

}
