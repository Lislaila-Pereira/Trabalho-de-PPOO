package br.ufla.gac106.s2023_1.TechWeek.modulos;

import java.util.List;

import br.ufla.gac106.s2023_1.TechWeek.Arquivo;
import br.ufla.gac106.s2023_1.TechWeek.AtividadeException;
import br.ufla.gac106.s2023_1.TechWeek.atividades.Atividade;
import br.ufla.gac106.s2023_1.TechWeek.atividades.Evento;
import br.ufla.gac106.s2023_1.TechWeek.atividades.LocalAcademico;
import br.ufla.gac106.s2023_1.TechWeek.atividades.Minicurso;
import br.ufla.gac106.s2023_1.TechWeek.atividades.Palestra;
import br.ufla.gac106.s2023_1.TechWeek.atividades.TipoAtividade;
import br.ufla.gac106.s2023_1.TechWeek.telas.TelaEventos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
 
//conterá os métodos para cadastrar, listar, detalhar e remover os eventos, locais e atividades.
public class ModuloAdministracao implements Serializable{
    private List<Evento> eventos;
    private List<LocalAcademico> locais;
    private List<Atividade> atividades;
    private static Arquivo<ModuloAdministracao> arquivo;
    private static ModuloAdministracao instancia;

    public ModuloAdministracao(){
       eventos = new ArrayList<Evento>();
       locais = new ArrayList<LocalAcademico>();
       atividades = new ArrayList<Atividade>();
       arquivo = new Arquivo<ModuloAdministracao>("dados_adm.bin", ModuloAdministracao.class);
    }

    //-----Lidando com arquivos---------
    public static ModuloAdministracao getInstancia(){
        if(instancia==null){ //verifica se ja foi instanciado
            arquivo = new Arquivo<ModuloAdministracao>("dados_adm.bin", ModuloAdministracao.class);
            instancia = arquivo.getDados(); //obtem instancia do arquivo
            if(instancia==null){ //verifica se exsite algum arquivo salvo
                instancia = new ModuloAdministracao();
                return instancia;
            }
        }
        return instancia; //existe instancia, retorna a instancia atual
    }

    public void encerrar(){
        arquivo.salvarDados(instancia); //salva instancia atual
    }
    
    //--Iniciando modulo de ingressos---
    public void moduloIngresso(){
        //ModuloIngresso moduloIngresso = new ModuloIngresso();
        TelaEventos telaEventos = new TelaEventos();
        telaEventos.setVisible(true);
    }
    
    //modulo de graficos
    public void moduloGraficos() {
        ModuloGraficos graficos = new ModuloGraficos();
        graficos.setVisible(true);
    }
    
    //---------Cadastro-----------------
    public boolean cadastrarEvento(String nome, String tema, String universidade, int ano) {
        if(encontrarEventoPorNome(nome) == null){// nao encontrou evento com mesmo nome, pode ser cadastrado
            return eventos.add(new Evento(nome, tema, universidade, ano));
        } else {
            return false;
        }
    }
    
    public boolean cadastrarLocal(String nomeLocal, String departamento, int capacidade) {
        if(encontrarLocalPorNome(nomeLocal) == null){ // nao encontrou local com mesmo nome, pode ser cadastrado
            return locais.add(new LocalAcademico(nomeLocal, departamento, capacidade));
        }else {
            return false;
        }  
    }

    public void cadastrarAtividade(String nomeAtividade, String nomeEvento, String nomeLocal, TipoAtividade tipo, String data, String hora, String palestrante_instrutor, int duracao_cargaHoraria) throws AtividadeException {
        // Verificar se o LocalAcademico e o Evento existem
        LocalAcademico local = encontrarLocalPorNome(nomeLocal);
        Evento evento = encontrarEventoPorNome(nomeEvento);
    
        if (local == null) {
            throw new AtividadeException("Local acadêmico não encontrado.");
        } else if (evento == null) {
            throw new AtividadeException("Evento não encontrado.");
        } else {
            for (Atividade atividade : atividades) {
                if (atividade.getLocalAcademico().equals(local) && atividade.getDataInicio().equals(data) && atividade.getHoraInicio().equals(hora)) {
                    throw new AtividadeException("Já existe uma atividade cadastrada para o mesmo local, data e hora.");
                }
            }
    
            // Se chegou aqui, pode cadastrar a atividade
            Atividade atividade;
            if (tipo == TipoAtividade.PALESTRA) {
                atividade = new Palestra(nomeAtividade, evento, tipo, local, data, hora, palestrante_instrutor, duracao_cargaHoraria);
            } else {
                atividade = new Minicurso(nomeAtividade, evento, tipo, local, data, hora, palestrante_instrutor, duracao_cargaHoraria);
            }
            atividades.add(atividade);
        }
    }
    
    
    //----------Remover-----------------
    public boolean removerEvento(String nomeEvento) {
        if(!temAtividadeNoEvento(nomeEvento)){ //verifica se nao tem nenhuma atividade atrelada ao evento
            for (Evento e : eventos) {
                if(e.getNome().equals(nomeEvento)){
                    return eventos.remove(e);
                }
            }
        } 
        return false;
    }

    public boolean removerLocal(String nomeLocal) {
        if(!temAtividadeNoLocal(nomeLocal)){ //verifica se nao tem nenhuma atividade atrelada ao local
            for (LocalAcademico local : locais) {
                if(local.getNome().equals(nomeLocal)){
                    return locais.remove(local);
                }
            }
        } 
        return false;
    }

    public boolean removerAtividade(String nome) {
        for (Atividade atividade : atividades) { //nesse caso qualquer atividade pode entrar
            if(atividade.getNome().equals(nome)){
                return atividades.remove(atividade);
            }
        }
        return false;
    }
    
    //----------Detalhar-----------------
    public String detalharEvento(String nomeEvento) {
        Evento evento = encontrarEventoPorNome(nomeEvento);
        if(evento != null){
            return evento.getDescricao();
        }
        return null;
    }
    
    public String detalharAtividade(String nomeAtividade) {
        Atividade atividade = encontrarAtividadePorNome(nomeAtividade);
        if(atividade != null){
            return atividade.getDescricao();
        }
        return null;
    }
    
    public String detalharLocal(String nomeLocal) {
        LocalAcademico local = encontrarLocalPorNome(nomeLocal);
        if (local != null){
            return local.getDescricao();
        }
        return null;
    }

    //----------Listar-----------------
    //utilizados em métodos que precisam da lista de nomes ao inves do obj inteiro. metodos usados externamente
    public List<String> getListaNomeEventos(){
        ArrayList<String> nomeEventos = new ArrayList<String>();
        for (Evento evento : eventos) {
            nomeEventos.add(evento.getNome());
        }
        return nomeEventos;
    }

    public List<String> getListaNomeLocais(){
        ArrayList<String> nomeLocais = new ArrayList<String>();
        for (LocalAcademico local : locais) {
            nomeLocais.add(local.getNome());
        }
        return nomeLocais;
    }

    public List<String> getListaNomeAtividades(String nomeEvento) { 
        ArrayList<String> nomeAtividade = new ArrayList<>();
        for (Atividade atividade : atividades) {
            if(atividade.getEvento().getNome().equals(nomeEvento)){
                nomeAtividade.add(atividade.getNome());
            }
        }
        return nomeAtividade;
    }


    //métodos auxiliares
    //Encontrar um obj a partir de seu nome. metodos usados internamente
    private Evento encontrarEventoPorNome(String nomeEvento) {
        for (Evento evento : eventos) {
            if (evento.getNome().equals(nomeEvento)) {
                return evento;
            }
        }
        return null; 
    }
    
    private LocalAcademico encontrarLocalPorNome(String nomeLocal) {
        for (LocalAcademico localAcademico : locais) {
            if (localAcademico.getNome().equals(nomeLocal)) {
                return localAcademico;
            }
        }
        return null;
    }

    private Atividade encontrarAtividadePorNome(String nomeAtividade) {
        for (Atividade atividade : atividades) {
            if(atividade.getNome().equals(nomeAtividade)) {
                return atividade;
            }
        }
        return null; 
    }

    //utilizados na remocao. metodos usados internamente 
    //Se existe uma atividade no local ele nao pode ser removido
    private boolean temAtividadeNoLocal(String nomeLocal){
        for (Atividade atividade : atividades) {
            if(atividade.getLocalAcademico().getNome().equals(nomeLocal)){
                return true;
            }
        }
        return false;
    }
    
    //Se existe uma atividade no evento ele nao pode ser removido
    private boolean temAtividadeNoEvento(String nomeEvento){
        for (Atividade atividade : atividades) {
            if(atividade.getEvento().getNome().equals(nomeEvento)){
                return true;
            }
        }
        return false;
    }
    
    //listas imodificaveis. usadas externamente
    public List<Evento> getEventosObj(){
        return Collections.unmodifiableList(eventos);
    }

    public List<LocalAcademico> getLocaisObj(){
        return Collections.unmodifiableList(locais);
    }
    
    public List<Atividade> getAtividadesObj(){
        return Collections.unmodifiableList(atividades);
    }
    

}
