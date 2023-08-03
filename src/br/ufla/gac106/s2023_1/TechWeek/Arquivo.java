/*
 * Classe responsável por salvar um arquivo binário com os dados cadastrados no sistema
 * ele recebe um objeto de um tipo genérico como parametro para salvar e devolve 
 * o mesmo tipo de objeto quando carrega os dados para o sistema.  
 * 
 */
package br.ufla.gac106.s2023_1.TechWeek;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Arquivo<T> {

    private String nomeArquivo;
    private Class<T> classeTipo;

    public Arquivo(String nomeArquivo, Class<T> classeTipo) {
        if (nomeArquivo == null || nomeArquivo.isEmpty()) {
            throw new IllegalArgumentException("O nome do arquivo não pode ser nulo ou vazio.");
        }
        this.nomeArquivo = nomeArquivo;
        this.classeTipo = classeTipo;
    }

    public void salvarDados(T objeto) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(objeto);
            //System.out.println("Dados salvos em " + nomeArquivo);
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("Erro ao salvar os dados em arquivo");
        }
    }

    public T getDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            return classeTipo.cast(ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}

