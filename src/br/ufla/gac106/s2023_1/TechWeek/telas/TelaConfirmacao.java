package br.ufla.gac106.s2023_1.TechWeek.telas;

import java.awt.BorderLayout;
import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import br.ufla.gac106.s2023_1.TechWeek.atividades.Atividade;
import br.ufla.gac106.s2023_1.TechWeek.graficos.SistemaVendas;
//import br.ufla.gac106.s2023_1.TechWeek.graficos.SistemaVendas;
import br.ufla.gac106.s2023_1.TechWeek.ingressos.IngressoFactory;
import br.ufla.gac106.s2023_1.TechWeek.ingressos.IngressoPDF;
import br.ufla.gac106.s2023_1.TechWeek.modulos.ModuloAdministracao;
import br.ufla.gac106.s2023_1.base.compraIngressos.JanelaBase;
import br.ufla.gac106.s2023_1.TechWeek.ingressos.Ingresso;

public class TelaConfirmacao extends JanelaBase {
    private JPanel painelCentral;
    private JTextArea descricaoTextArea;
    private List<Ingresso> ingressosComprados;
    private Atividade atividadeSelecionada;

    // Construtor da classe
    public TelaConfirmacao(String titulo, String instrucoes, int largura, int altura, boolean temBotaoVoltar, JanelaBase janelaAnterior, boolean temBotaoAvancar, boolean temBotaoFinalizar, Atividade atividadeSelecionada, String nome, int quantidadeComum, int quantidadeMeia, int quantidadeDesconto) {
        super(titulo, instrucoes, largura, altura, temBotaoVoltar, janelaAnterior, temBotaoAvancar, temBotaoFinalizar);
        this.atividadeSelecionada = atividadeSelecionada;
        preencherInformacoesCompra(nome, quantidadeComum, quantidadeMeia, quantidadeDesconto, atividadeSelecionada);
    }

    // Método sobrescrito que cria o painel central da janela
    @Override
    protected JPanel criarPainelCentral() {
        criarComponentes();
        montarJanela();
        return painelCentral;
    }
    
    // Método privado que inicializa os componentes do painel central
    private void criarComponentes() {
        painelCentral = new JPanel();
        descricaoTextArea = new JTextArea();
        descricaoTextArea.setEditable(false);
    }

    // Método privado que organiza a disposição dos componentes no painel central
    private void montarJanela() {
        painelCentral.setLayout(new BorderLayout());
        painelCentral.add(descricaoTextArea);
    }

    // Método público que preenche as informações da compra e exibe na área de texto
    public void preencherInformacoesCompra(String nome, int quantidadeComum, int quantidadeMeia, int quantidadeDesconto, Atividade atividade) {
        ingressosComprados = IngressoFactory.criarIngressos(nome, quantidadeComum, quantidadeMeia, quantidadeDesconto, atividade);
        double valor = calcularValorTotalIngressos();

        String texto = "Clique em Finalizar para confirmar a compra:\n" + 
                "\n" +
                "Ingresso comum:\t\t" + quantidadeComum +
                "\nIngresso meia-entrada:\t\t" + quantidadeMeia +
                "\nIngresso com desconto:\t\t" + quantidadeDesconto +
                "\nValor total da compra: R$" + valor;
        descricaoTextArea.setText(texto); 
    }

    // Método privado que calcula o valor total dos ingressos comprados
    private double calcularValorTotalIngressos() {
        double valorTotal = 0;
        for (Ingresso ingresso : ingressosComprados) {
            valorTotal += ingresso.getValor();
        }
        return valorTotal;
    }

    // Método sobrescrito que é chamado ao voltar na janela
    @Override
    protected boolean aoVoltar() {
        atividadeSelecionada.adicionarIngressos(ingressosComprados.size());
        ingressosComprados.clear();
        return true;
    }

    // Método sobrescrito que é chamado ao finalizar a compra
    @Override
    protected boolean aoFinalizar() {
        gerarPDF();
        //envia os dados para a classe que vai montar os graficos
        SistemaVendas.getInstancia().atualizarListaIngressos(ingressosComprados);
        JOptionPane.showMessageDialog(this, "Compra concluída com sucesso", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
        ModuloAdministracao.getInstancia().moduloIngresso();
        return true;
    }
    
    // Método privado que gera o arquivo PDF com os ingressos comprados
    private void gerarPDF(){
        File outputDir = new File("ingressosPDF");
        if (!outputDir.exists()) {
            // Tratamento de exceção caso não seja possível criar o diretório
            try {
                outputDir.mkdir();
            } catch (SecurityException e) {
                // Mensagem de erro mais amigável para o usuário final
                String mensagemErro = "Ocorreu um problema relacionado à segurança.\n" +
                                      "Por favor, entre em contato com o suporte técnico para obter ajuda.";
            
                // Exibe a mensagem de erro para o usuário final
                JOptionPane.showMessageDialog(null, mensagemErro, "Erro de Segurança", JOptionPane.ERROR_MESSAGE);
            }
        }
        new IngressoPDF(ingressosComprados, outputDir.getPath());
    }
}

