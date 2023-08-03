package br.ufla.gac106.s2023_1.TechWeek.graficos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.ufla.gac106.s2023_1.TechWeek.Arquivo;
import br.ufla.gac106.s2023_1.TechWeek.ingressos.Ingresso;
import br.ufla.gac106.s2023_1.base.relatorios.ContabilizadorIngressos;
import br.ufla.gac106.s2023_1.base.relatorios.GraficoIngressos;

public class SistemaVendas implements Serializable {
    private static Arquivo<SistemaVendas> arquivo;
    private static SistemaVendas instancia;
    private List<Ingresso> todosIngressosVendidos;
    private List<ContabilizadorIngressos> vendasPorEvento;
    private List<ContabilizadorIngressos> vendasPorAtividade;
    private List<ContabilizadorIngressos> vendasPorComprador;

    public SistemaVendas() {
        //criando objs
        arquivo = new Arquivo<SistemaVendas>("dados_ingressos.bin", SistemaVendas.class);
        todosIngressosVendidos = new ArrayList<Ingresso>();
        vendasPorEvento = new ArrayList<>();
        vendasPorAtividade = new ArrayList<>();
        vendasPorComprador = new ArrayList<>();
    }
    
    //-----Lidando com arquivos---------
    public static SistemaVendas getInstancia(){
        if(instancia==null){ //verifica se ja foi instanciado
            arquivo = new Arquivo<SistemaVendas>("dados_ingressos.bin", SistemaVendas.class);
            instancia = arquivo.getDados(); //obtem instancia do arquivo
            if(instancia==null){ //verifica se exsite algum arquivo salvo
                instancia = new SistemaVendas();
                return instancia;
            }
        }
        return instancia; //existe instancia, retorna a instancia atual
    //arquivo.salvarDados(instancia); //salva instancia atual    
    }
    
    public void atualizarListaIngressos(List<Ingresso> ingressosComprados) {
        //atualizando lista de ingressosVendidos     
        todosIngressosVendidos.addAll(ingressosComprados);
        atualizaVendas(ingressosComprados);
        arquivo.salvarDados(instancia);
    }

    private void atualizaVendas(List<Ingresso> ingressosComprados) {
        for (Ingresso ingresso : ingressosComprados) {
            // Para vendas por evento:
            //procuro o evento, se for encontrado incremento a quantidade de ingressos e somo no valor total o preço do ingresso
            //se o evento nao for encontrado eu adiciono ele a lista;
            boolean eventoEncontrado = false;
            for (ContabilizadorIngressos evento : vendasPorEvento) {
                if (evento.identificador().equals(ingresso.getAtividade().getEvento().getNome())) {
                    eventoEncontrado = true;
                    ((Contabilizador) evento).incrementarQuantidadeIngressos();;
                    ((Contabilizador) evento).adicionarValorNoTotal(ingresso.getValor());
                    break;
                }
            }
            if (!eventoEncontrado) {
                ContabilizadorIngressos novoEvento = new Contabilizador(ingresso.getAtividade().getEvento().getNome(), ingresso.getValor());
                vendasPorEvento.add(novoEvento);
            }
        
            // Para vendas por atividade:
            boolean atividadeEncontrada = false;
            for (ContabilizadorIngressos atividade : vendasPorAtividade) {
                if (atividade.identificador().equals(ingresso.getAtividade().getNome())) {
                    atividadeEncontrada = true;
                    ((Contabilizador) atividade).incrementarQuantidadeIngressos();
                    ((Contabilizador) atividade).adicionarValorNoTotal(ingresso.getValor());
                    break;
                }
            }
            if (!atividadeEncontrada) {
                ContabilizadorIngressos novaAtividade = new Contabilizador(ingresso.getAtividade().getNome(), ingresso.getValor());
                vendasPorAtividade.add(novaAtividade);
            }
        
            // Para vendas por comprador:
            boolean compradorEncontrado = false;
            for (ContabilizadorIngressos comprador : vendasPorComprador) {
                if (comprador.identificador().equals(ingresso.getComprador())) {
                    compradorEncontrado = true;
                    ((Contabilizador) comprador).incrementarQuantidadeIngressos();
                    ((Contabilizador) comprador).adicionarValorNoTotal(ingresso.getValor());
                    break;
                }
            }
            if (!compradorEncontrado) {
                ContabilizadorIngressos novoComprador = new Contabilizador(ingresso.getComprador(), ingresso.getValor());
                vendasPorComprador.add(novoComprador);
            }
        }
    }
    
    public void mostrarGrafico(String titulo) {
        GraficoIngressos grafico = new GraficoIngressos();

        if (titulo.equals("Vendas por Evento")) {
            grafico.exibir(titulo, vendasPorEvento, true);
        } else if (titulo.equals("Vendas por Atividade")) {
            grafico.exibir(titulo, vendasPorAtividade, true);
        } else if (titulo.equals("Vendas por Comprador")) {
            grafico.exibir(titulo, vendasPorComprador, true);
        }
    }
}
