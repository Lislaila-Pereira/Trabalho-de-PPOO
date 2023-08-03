package br.ufla.gac106.s2023_1.TechWeek.ingressos;

import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class IngressoPDF {
    private static final Font SMALL_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
    private static final Font BIG_BOLD_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
    private String outputPath;
    private static int nroIngresso = 0;

    public IngressoPDF(List<Ingresso> ingressos, String outputPath) {
        this.outputPath = outputPath;
        gerarPDF(ingressos);
    }

    //gera um pdf com todos os ingressos comprados 
    private void gerarPDF(List<Ingresso> ingressos) {
        Document document = new Document(new Rectangle(550, 250)); 
        document.setMargins(20, 20, 20, 20);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath + "./" + "ingressos_id_" + nroIngresso + ".pdf"));
            document.open();

            for (Ingresso ingresso : ingressos) {
                // Criar um retângulo colorido como fundo do ingresso
                Rectangle rect = new Rectangle(550, 250);
                rect.setBackgroundColor(BaseColor.LIGHT_GRAY);
                document.add(rect);
                
                // Criar um retângulo preto como margem
                Rectangle marginRect = new Rectangle(535, 235);
                marginRect.setBorder(Rectangle.BOX);
                marginRect.setBorderWidth(1f); // Espessura da borda
                marginRect.setBorderColor(BaseColor.BLACK);
                document.add(marginRect);

                // Exibir o tipo da atividade e o nome da atividade em letras pequenas e centralizado
                Paragraph type = new Paragraph(ingresso.getAtividade().getTipoAtividade() + " " + ingresso.getAtividade().getNome(), SMALL_FONT);
                type.setAlignment(Element.ALIGN_CENTER);
                document.add(type);

                // Exibir o nome do evento em letras maiores, negrito e centralizado
                Paragraph name = new Paragraph(ingresso.getAtividade().getEvento().getNome(), BIG_BOLD_FONT);
                name.setAlignment(Element.ALIGN_CENTER);
                document.add(name);

                document.add(new Paragraph(" "));

                // Exibir data, hora, tipo de ingresso, valor e local
                String dataHoraLocal = ingresso.getAtividade().getDataInicio() + " às " + ingresso.getAtividade().getHoraInicio()+ " - " + ingresso.getAtividade().getLocalAcademico().getNome() + "\n";
                
                String valor = ingresso.getTipo() + "\n"+"R$" + String.valueOf(ingresso.getValor()) + "\n";
                
                Paragraph info = new Paragraph( dataHoraLocal + " " + valor, SMALL_FONT);
                
                info.setAlignment(Element.ALIGN_CENTER);
                document.add(info);
                document.add(new Paragraph(" "));

                // Exibir o nome do comprador centralizado
                Paragraph buyer = new Paragraph(ingresso.getComprador(), SMALL_FONT);
                buyer.setAlignment(Element.ALIGN_CENTER);
                document.add(buyer);

                document.newPage(); // Criar uma nova página para o próximo ingresso
            }

            document.close();
            //System.out.println("Os ingressos em PDF foram gerados com sucesso.");
            nroIngresso++;
        } catch (DocumentException e) {
            // Em caso de falha na criação do PDF.
            System.err.println("Erro ao gerar o PDF dos ingressos. Por favor, tente novamente.");
        } catch (FileNotFoundException e) {
            // Em caso de falha ao encontrar o arquivo de saída.
            System.err.println("Erro ao encontrar o diretório de saída do PDF dos ingressos. Por favor, tente novamente.");
        }
    }
}


