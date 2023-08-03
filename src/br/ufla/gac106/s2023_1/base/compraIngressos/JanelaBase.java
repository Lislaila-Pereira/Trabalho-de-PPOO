package br.ufla.gac106.s2023_1.base.compraIngressos;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.formdev.flatlaf.FlatDarkLaf;

/**
 * Classe que define uma Janela com um painel superior com intruções, um painel central a ser defindo 
 * por uma subclasse e um painel inferior com, possivelmente, botões Voltar, Avançar e Finalizar
 * 
 * Obs.: repare que, diferentemente do exemplo usando em sala de aula, a classe JanelaBase herda de
 *       JFrame em vez de ter um atributo JFrame.
 */
public abstract class JanelaBase extends JFrame {

    // janela anterior (usado quando a tela tem um botão voltar)
    private JanelaBase janelaAnterior;

    /**
     * Cria a janela com as informações passadas.
     * 
     * @param titulo Título da janela
     * @param titulo Instruções a serem exibidas no painel superior
     * @param largura Largura da janela
     * @param altura Altura da janela
     * @param temBotaoVoltar Indica se a tela terá um botão Voltar
     * @param janelaAnterior Janela a ser chamada ao clicar no botão Voltar (se ele não estiver visível passe null)
     * @param temBotaoAvancar Indica se a tela terá um botão Avançar
     * @param temBotaoFinalizar Indica se a tela terá um botão Finalizar
     */
    public JanelaBase(String titulo, String instrucoes, int largura, int altura, 
                      boolean temBotaoVoltar, JanelaBase janelaAnterior, boolean temBotaoAvancar, boolean temBotaoFinalizar) {
        super(titulo);                 // chamar construtor da classe JFrame
        FlatDarkLaf.setup();           // define layout com tema escuro
        setSize(largura, altura);      // define tamanho da janela
        setLocationRelativeTo(null); // centraliza janela na tela
        setLayout(new BorderLayout()); // define o layout da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // define o que acontece quando a tela é fechada
        
        // guarda a janela anterior passada
        this.janelaAnterior = janelaAnterior; 

        // cria o painel superior com as instruções passadas
        criarPainelSuperior(instrucoes); 

        // chama o método que cria o painel central e o adiciona na janela
        add(criarPainelCentral(), BorderLayout.CENTER);

        // cria o painel inferior com, possivelmente, os botões Voltar, Avançar e Finalizar
        criarPainelInferior(temBotaoVoltar, temBotaoAvancar, temBotaoFinalizar);
    }

    /**
     * Cria o painel superior com as instruções passadas
     * 
     * @param instrucoes String com as instruções a serem colocadas em um JLabel
     */
    private void criarPainelSuperior(String instrucoes) {
        // O painel superior tem apenas um rótulo com as instruções passadas
        
        // cria e configura o painel
        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(new GridLayout(1, 1));
        painelSuperior.setBackground(Color.DARK_GRAY.brighter());

        // cria e configura o rótulo
        JLabel rotuloInstrucoes = new JLabel("    " + instrucoes);
        rotuloInstrucoes.setFont(rotuloInstrucoes.getFont().deriveFont(16.0f));
        rotuloInstrucoes.setPreferredSize(new Dimension(this.getWidth(), 50));

        // adiciona o rótulo ao painel
        painelSuperior.add(rotuloInstrucoes);
        
        // adiciona o painel à janela
        add(painelSuperior, BorderLayout.NORTH);
    }

    /**
     * Cria e retorna o painel central da janela
     * - Deve ser sobrescrito para incluir o que a tela realmente faz
     */
    protected abstract JPanel criarPainelCentral();

    /**
     * Cria o painel inferior da janela com, possivelmente, os botões Voltar, Avançar e Finalizar
     */
    private void criarPainelInferior(boolean temBotaoVoltar, boolean temBotaoAvancar, boolean temBotaoFinalizar) {
        // cria o painel
        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new GridLayout(1, 3));

        // trata os botões, criando-os e tratando seus eventos, se necessário
        tratarBotaoVoltar(temBotaoVoltar, painelInferior);
        tratarBotaoAvancar(temBotaoAvancar, painelInferior);
        tratarBotaoFinalizar(temBotaoFinalizar, painelInferior);

        // adiciona o painel à janela
        add(painelInferior, BorderLayout.SOUTH);
    }

    /**
     * Trata o botão Voltar, criando-o e configurando seu evento, se necessário
     */
    private void tratarBotaoVoltar(boolean temBotaoVoltar, JPanel painelInferior) {
        if (temBotaoVoltar) {
            JButton botaoVoltar = new JButton("Voltar");
            // Ao clicar em Voltar é chamado o método `aoVoltar`. Se ele retornar true fecha a janela e exibe a janela anterior.
            botaoVoltar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (aoVoltar()) {
                        setVisible(false);
                        if (janelaAnterior != null) {
                            janelaAnterior.setVisible(true);
                        }
                    }
                }
            });
            painelInferior.add(botaoVoltar);
        }
        else {
            painelInferior.add(new JPanel()); // painel vazio para deixar o espaço vazio na tela
        }
    }

    /**
     * Trata o botão Avancar, criando-o e configurando seu evento, se necessário
     */
    private void tratarBotaoAvancar(boolean temBotaoAvancar, JPanel painelInferior) {
        if (temBotaoAvancar) {
            JButton botaoAvancar = new JButton("Avançar");
            // Ao clicar em Avançar é chamado o método `aoAvancar`. Se ele retornar true a tela é fechada.
            botaoAvancar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (aoAvancar()) {
                        dispose();
                    }
                }
            });
            painelInferior.add(botaoAvancar);
        }
        else {
            painelInferior.add(new JPanel()); // painel vazio para deixar o espaço vazio na tela
        }
    }

    /**
     * Trata o botão Finalizar, criando-o e configurando seu evento, se necessário
     */
    private void tratarBotaoFinalizar(boolean temBotaoFinalizar, JPanel painelInferior) {
        if (temBotaoFinalizar) {
            JButton botaoFinalizar = new JButton("Finalizar");
            // Ao clicar em Finalizar é chamado o método `aoFinalizar`. Se ele retornar true a tela é fechada.
            botaoFinalizar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (aoFinalizar()) {
                        dispose();
                    }
                }
            });
            painelInferior.add(botaoFinalizar);
        }
        else {
            painelInferior.add(new JPanel()); // painel vazio para deixar o espaço vazio na tela
        }
    }

    /**
     * Método a ser sobrescrito para tratar o que acontece quando o usuário clica em "Voltar"
     * Obs.: não é necessário tratar a exibição da janela anterior (isso já é tratado na classe JanelaBase)
     * 
     * @return O retorno do método é usado para indicar se vai voltar mesmo ou não
     */
    protected boolean aoVoltar() {
        return true;
    }

    /**
     * Método a ser sobrescrito para tratar o que acontece quando o usuário clica em "Avançar"
     * 
     * @return O retorno do método é usado para indicar se vai avançar mesmo ou não
     */
    protected boolean aoAvancar() {
        return true;
    }

    /**
     * Método a ser sobrescrito para tratar o que acontece quando o usuário clica em "Finalizar"
     * 
     * @return O retorno do método é usado para indicar se vai finalizar mesmo ou não
     */
    protected boolean aoFinalizar() {
        return true;
    }

    /**
     * Método útil para carregar um JList com os dados de uma lista de Strings
     */
    public static void carregarJList(JList<String> lista, List<String> dados) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String dado : dados) {
            model.addElement(dado);
        }
        lista.setModel(model);
    }
}
