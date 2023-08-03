package br.ufla.gac106.s2023_1.TechWeek.modulos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatDarkLaf;

import br.ufla.gac106.s2023_1.TechWeek.graficos.SistemaVendas;

public class ModuloGraficos extends JFrame {

    public ModuloGraficos() {
        super("Módulo de Relatórios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        FlatDarkLaf.setup();
        setLocationRelativeTo(null);
        setSize(500, 500);

        JPanel panel = new JPanel();

        // Criação dos botões para exibir os gráficos
        JButton graficoPorEventoButton = criarBotao("Gráfico por Evento", "Vendas por Evento");
        JButton graficoPorAtividadeButton = criarBotao("Gráfico por Atividade", "Vendas por Atividade");
        JButton graficoPorCompradorButton = criarBotao("Gráfico por Comprador", "Vendas por Comprador");

        // Adiciona os botões ao painel
        panel.add(graficoPorEventoButton);
        panel.add(graficoPorAtividadeButton);
        panel.add(graficoPorCompradorButton);

        // Adiciona o painel ao JFrame
        add(panel);
    }

    /**
     * Cria e configura um botão com o nome especificado e adiciona um ActionListener
     * que chamará o método mostrarGrafico do SistemaVendas com o título correto quando o botão for clicado.
     * 
     * @param nomeBotao   O nome a ser exibido no botão
     * @param tituloGrafico O título do gráfico que será exibido ao clicar no botão
     * @return O botão criado
     */
    private JButton criarBotao(String nomeBotao, String tituloGrafico) {
        JButton botao = new JButton(nomeBotao);
        botao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SistemaVendas.getInstancia().mostrarGrafico(tituloGrafico);
            }
        });
        return botao;
    }
}
