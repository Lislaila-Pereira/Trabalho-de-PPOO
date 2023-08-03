package br.ufla.gac106.s2023_1.TechWeek.telas;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import br.ufla.gac106.s2023_1.TechWeek.atividades.Atividade;
import br.ufla.gac106.s2023_1.base.compraIngressos.JanelaBase;

public class TelaIngressos extends JanelaBase {
    private JPanel painelCentral;
    private JLabel nomeLabel, comumLabel, meiaLabel, descontoLabel;
    private JTextField nomeTextField;
    private JSpinner comumSpinner, meiaSpinner, descontoSpinner;
    private Atividade atividadeSelecionada;
    
    public TelaIngressos(String titulo, String instrucoes, int largura, int altura, boolean temBotaoVoltar, JanelaBase janelaAnterior, boolean temBotaoAvancar, boolean temBotaoFinalizar, Atividade atividadeSelecionada) {
        super(titulo, instrucoes, largura, altura, temBotaoVoltar, janelaAnterior, temBotaoAvancar, temBotaoFinalizar);
        this.atividadeSelecionada = atividadeSelecionada;
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
        nomeLabel = new JLabel("Nome:");
        comumLabel = new JLabel("Ingresso comum:");
        meiaLabel = new JLabel("Meia-entrada:");
        descontoLabel = new JLabel("Ingresso com desconto:");
        nomeTextField = new JTextField();

        comumSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        JSpinner.DefaultEditor comumEditor = (JSpinner.DefaultEditor) comumSpinner.getEditor();
        comumEditor.getTextField().setEditable(false);
        meiaSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        JSpinner.DefaultEditor meiaEditor = (JSpinner.DefaultEditor) meiaSpinner.getEditor();
        meiaEditor.getTextField().setEditable(false);
        descontoSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        JSpinner.DefaultEditor descontoEditor = (JSpinner.DefaultEditor) descontoSpinner.getEditor();
        descontoEditor.getTextField().setEditable(false);

    }
    
    // Método privado que organiza a disposição dos componentes no painel central
    private void montarJanela(){
        painelCentral.setLayout(new GridLayout(0, 2, 15, 15));
        painelCentral.add(nomeLabel);
        painelCentral.add(nomeTextField);
        painelCentral.add(comumLabel);
        painelCentral.add(comumSpinner);
        painelCentral.add(meiaLabel);
        painelCentral.add(meiaSpinner);
        painelCentral.add(descontoLabel);
        painelCentral.add(descontoSpinner);
    }

    // Método sobrescrito que é chamado ao clicar no botao avancar
    @Override
    protected boolean aoAvancar() {
        String nome = nomeTextField.getText().trim();
        int quantidadeComum = (int) comumSpinner.getValue();
        int quantidadeMeia = (int) meiaSpinner.getValue();
        int quantidadeDesconto = (int) descontoSpinner.getValue();

        // Verifica se a variável 'nome' é nula antes de chamar o método 'isEmpty()'
        if (nome != null && !nome.isEmpty()) {
            if (quantidadeComum == 0 && quantidadeMeia == 0 && quantidadeDesconto == 0) {
                JOptionPane.showMessageDialog(this, "Nenhum ingresso selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                int totalSelecionado = quantidadeComum + quantidadeMeia + quantidadeDesconto;
                int ingressosDisponiveis = atividadeSelecionada.getIngressosDisponiveis();

                //quantidade de ingressos selecionados esta disponivel
                if (totalSelecionado <= ingressosDisponiveis) { 
                    JanelaBase telaIngressos = this;
                    TelaConfirmacao telaConfirmacao = new TelaConfirmacao("Módulo de Compra de Ingressos", "Confirmação da compra:", 800, 600, true, telaIngressos, false, true, atividadeSelecionada, nome, quantidadeComum, quantidadeMeia, quantidadeDesconto);
                    telaConfirmacao.setVisible(true);
                    setVisible(false);
                    return true;
                } else { //quantidade de ingressos selecionados nao esta disponivel
                    String mensagem = "Você selecionou um total de " + totalSelecionado + " ingressos, mas só estão disponíveis " + ingressosDisponiveis + " ingressos.";
                    JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, digite um nome.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
