package br.ufla.gac106.s2023_1.TechWeek.telas;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.ufla.gac106.s2023_1.TechWeek.atividades.Atividade;
import br.ufla.gac106.s2023_1.TechWeek.atividades.Evento;
import br.ufla.gac106.s2023_1.TechWeek.modulos.ModuloAdministracao;
import br.ufla.gac106.s2023_1.base.compraIngressos.JanelaBase;

public class TelaAtividades extends JanelaBase {
    private JPanel painelSuperior, painelCentral, painelInferior;
    private JScrollPane painelRolagem;
    private JLabel numeroIngressos;
    private JTextArea descricaoTextArea;
    private JList<String> atividadesList;
    private Evento eventoSelecionado;
    
    public TelaAtividades(String titulo, String instrucoes, int largura, int altura, boolean temBotaoVoltar, JanelaBase janelaAnterior, boolean temBotaoAvancar, boolean temBotaoFinalizar, Evento eventoSelecionado) {
        super(titulo, instrucoes, largura, altura, temBotaoVoltar, janelaAnterior, temBotaoAvancar, temBotaoFinalizar);
        this.eventoSelecionado = eventoSelecionado;
        inicializarPainelCentral();
    }

    // Método que inicializa o painel central com base no evento selecionado
    public void inicializarPainelCentral() {
        if (eventoSelecionado != null) {
            descricaoTextArea = new JTextArea(eventoSelecionado.getDescricao());
            descricaoTextArea.setEditable(false);
            painelSuperior.add(descricaoTextArea, BorderLayout.CENTER);
            carregarListaAtividades();
        }
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
        atividadesList = new JList<>();
        painelRolagem = new JScrollPane(atividadesList);
        painelSuperior = new JPanel();
        painelInferior = new JPanel();
        numeroIngressos = new JLabel("Número de ingressos: ");
    }

    // Método privado que organiza a disposição dos componentes no painel central
    private void montarJanela() {
        painelSuperior.setLayout(new BorderLayout());
        painelCentral.setLayout(new BorderLayout());
        painelCentral.add(painelRolagem, BorderLayout.CENTER);
        painelCentral.add(painelSuperior, BorderLayout.NORTH);
        painelCentral.add(painelInferior, BorderLayout.SOUTH);
        painelInferior.add(numeroIngressos);
    }

    // Método que carrega a lista de atividades com base no evento selecionado
    private void carregarListaAtividades() {
        if (eventoSelecionado != null) {
            List<String> atividades = ModuloAdministracao.getInstancia().getListaNomeAtividades(eventoSelecionado.getNome());
            carregarJList(atividadesList, atividades);

            atividadesList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        String atividadeSelecionada = atividadesList.getSelectedValue();
                        if (atividadeSelecionada != null && !atividadeSelecionada.isEmpty()) {
                            Atividade atv = getAtividade(atividadeSelecionada);
                            if (atv != null) {
                                numeroIngressos.setText("Número de ingressos: " + atv.getIngressosDisponiveis());
                            } else {
                                numeroIngressos.setText("Número de ingressos: ");
                            }
                        }
                    }
                }
            });
        }
    }
    
    private Atividade getAtividade(String nome) {
        List<Atividade> listaAtividade = ModuloAdministracao.getInstancia().getAtividadesObj();
            for (Atividade atv : listaAtividade) {
                if(atv.getNome().equals(nome)) {
                    return atv;
                }
            }
            return null;
    }
    
    // Método sobrescrito que é chamado ao clicar no botao avancar
    @Override
    protected boolean aoAvancar() {
        String atividadeSelecionada = atividadesList.getSelectedValue();
        
        if (atividadeSelecionada != null && !atividadeSelecionada.isEmpty()) {
            //percorrer a lista de atividades para encontrar o obj desejado
            Atividade atividade = getAtividade(atividadeSelecionada);
            
            if (atividade != null && atividade.getIngressosDisponiveis() > 0) {
                JanelaBase telaAtividades = this;
                TelaIngressos telaIngressos = new TelaIngressos("Módulo de Compra de Ingressos", "Selecione uma atividade:", 800, 600, true, telaAtividades, true, false, atividade);
                telaIngressos.setVisible(true);
                setVisible(false);
                return false;
            } else {
                JOptionPane.showMessageDialog(this, "Não há ingressos disponíveis", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma atividade", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}

