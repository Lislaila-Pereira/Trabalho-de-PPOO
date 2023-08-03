package br.ufla.gac106.s2023_1.TechWeek.telas;

import java.util.List;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.ufla.gac106.s2023_1.TechWeek.atividades.Evento;
import br.ufla.gac106.s2023_1.TechWeek.modulos.ModuloAdministracao;
import br.ufla.gac106.s2023_1.base.compraIngressos.JanelaBase;

public class TelaEventos extends JanelaBase {
    private JPanel painelCentral;
    private JList<String> eventosList;
    private JScrollPane painelRolagem;
    
    public TelaEventos() {
        super("Módulo de Compra de Ingressos", "Selecione um evento:", 800, 600, true, null, true, false);
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
        // Criar painelCentral
        painelCentral = new JPanel();
        // Cria um JList para exibir os eventos
        eventosList = new JList<>();
        // Adiciona a lista de eventos a um painel de rolagem
        painelRolagem = new JScrollPane(eventosList);
    }
    
    // Método privado que organiza a disposição dos componentes no painel central
    private void montarJanela() {
        // Layout do painel
        painelCentral.setLayout(new BorderLayout());
        // Adiciona o painel de rolagem ao painel central
        painelCentral.add(painelRolagem, BorderLayout.CENTER);
        
        // Carrega o JList com eventos
        List<String> eventos = ModuloAdministracao.getInstancia().getListaNomeEventos();
        if (eventos != null) {
            carregarJList(eventosList, eventos);
        }
    }
    
    private Evento getEventoSelecionado() {
        int indiceSelecionado = eventosList.getSelectedIndex();
        if (indiceSelecionado != -1) {
            List<Evento> eventos = ModuloAdministracao.getInstancia().getEventosObj();
            if (eventos != null && indiceSelecionado < eventos.size()) {
                return eventos.get(indiceSelecionado);
            }
        }
        return null;
    }

    // Método sobrescrito que é chamado ao clicar no botao avancar
    @Override
    protected boolean aoAvancar() {
        Evento eventoSelecionado = getEventoSelecionado();
        if (eventoSelecionado != null) {
            JanelaBase telaEventos = this;
            TelaAtividades telaAtividades = new TelaAtividades("Módulo de Compra de Ingressos", "Selecione uma atividade:", 800, 600, true, telaEventos, true, false, eventoSelecionado);
            telaAtividades.setVisible(true);
            setVisible(false);
            return false; 
        } else {
            return false; 
        }
    }
}

