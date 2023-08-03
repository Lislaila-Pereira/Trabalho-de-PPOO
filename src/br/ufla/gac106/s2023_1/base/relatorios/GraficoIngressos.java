package br.ufla.gac106.s2023_1.base.relatorios;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;  
import javax.swing.SwingUtilities;
  
import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

/**
 * Classe utilizada para exibir uma tela com um gráfico de barras com os dados passados
 */
public class GraficoIngressos {
    
    /**
     * Cria e exibe (assincronamente) uma tela com um gráfico de barras com os dados de vendas dos ingressos
     * 
     * @param identificadorDoConjuntoDeDados Indica a que se refere a lista de dados passada (ex: "Filmes", "Compradores", )
     * @param dados Lista com as estatísticas para cada evento ou comprador
     * @param valorArrecadado Se for `true` criar um gráfico com os valores arrecadados. 
     *                        Se `false` cria um gráfico com a quantidade de ingressos vendidos.
     */
    public void exibir(String identificadorDoConjuntoDeDados, List<ContabilizadorIngressos> dados, boolean valorArrecadado) {
        SwingUtilities.invokeLater(() -> {
            TelaGraficoBarra tela = new TelaGraficoBarra(identificadorDoConjuntoDeDados, dados, valorArrecadado);
            tela.setAlwaysOnTop(true);
            tela.pack();
            tela.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
            tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tela.setVisible(true);
        });  
    }  
    
    /**
     * Classe interna criada para deifnir uma tela com gráfico de barras
     */
    private class TelaGraficoBarra extends JFrame {  
          
        private static final long serialVersionUID = 1L;
        
        /**
         * Constrói a tela com o gráfico de barras
         * 
         * @param identificadorDoConjuntoDeDados Indica a que se refere a lista de dados passada (ex: "Filmes", "Compradores", )
         * @param dados Lista com as estatísticas para cada evento ou atividade
         * @param valorArrecadado Se for `true` criar um gráfico com os valores arrecadas. 
         *                        Se `false` cria um gráfico com a quantidade de ingressos vendidos.
         */
        public TelaGraficoBarra(String identificadorDoConjuntoDeDados, List<ContabilizadorIngressos> dados, boolean valorArrecadado) {  
            super(identificadorDoConjuntoDeDados);  
              
            DefaultCategoryDataset dataset = criarDataset(identificadorDoConjuntoDeDados, dados, valorArrecadado);
            
            String rotuloEixoY;
            if (valorArrecadado) {
                rotuloEixoY = "Valor arrecadado";
            }
            else {
                rotuloEixoY = "Número de ingressos";
            }
            String tituloGrafico = rotuloEixoY + " - " + identificadorDoConjuntoDeDados;


            JFreeChart graficoBarra = ChartFactory.createBarChart(
                tituloGrafico,                  // Titulo do Grafico
                identificadorDoConjuntoDeDados, // Eixo X
                rotuloEixoY,                    // Eixo Y
                dataset);
            
            // Exibe os valores nas barras com formatação
            BarRenderer renderizador = (BarRenderer) graficoBarra.getCategoryPlot().getRenderer();
            if (valorArrecadado) {
                renderizador.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("R$ {2}", new DecimalFormat("0.00")));
            }
            else {
                renderizador.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            }
            renderizador.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER));
            renderizador.setBaseItemLabelsVisible(true);
            
            // Exibindo os rótulos do eixo X na vertical
            graficoBarra.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);                        
            // Permitindo que os rótulos do eixo X tenham até 3 linhas
            graficoBarra.getCategoryPlot().getDomainAxis().setMaximumCategoryLabelLines(3);
            // Escondendo a legenda
            graficoBarra.getLegend().setVisible(false);

            ChartPanel painel = new ChartPanel(graficoBarra);  
            setContentPane(painel); 
        }  
        
        /**
         * Cria um dataset a partir dos dados recebidos
         */
        private DefaultCategoryDataset criarDataset(String titulo, List<ContabilizadorIngressos> dados, boolean valorArrecadado) {          
            String serie = titulo;
            
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
            
            for (ContabilizadorIngressos estatistica : dados) {
                if (valorArrecadado) {
                    dataset.addValue(estatistica.valorTotal(), serie, estatistica.identificador());
                }
                else {
                    dataset.addValue((int)estatistica.quantidadeIngressos(), serie, estatistica.identificador());
                }
                
            }
            
            return dataset;  
        }       
    }
}
