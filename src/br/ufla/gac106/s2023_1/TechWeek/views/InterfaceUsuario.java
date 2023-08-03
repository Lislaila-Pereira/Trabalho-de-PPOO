package br.ufla.gac106.s2023_1.TechWeek.views;

import java.util.ArrayList;

import br.ufla.gac106.s2023_1.TechWeek.AtividadeException;
import br.ufla.gac106.s2023_1.TechWeek.Informacoes;
import br.ufla.gac106.s2023_1.TechWeek.atividades.TipoAtividade;
import br.ufla.gac106.s2023_1.TechWeek.modulos.ModuloAdministracao;

public class InterfaceUsuario {
    private Menus menu;
    private boolean sairDoPrograma = false;
    public InterfaceUsuario() {
        menu = new Menus();
    }
    
    public void executar() {
        int opcaoMenuModulo = 0;
        do {
            opcaoMenuModulo = menu.exibirMenuModulo();
            while(opcaoMenuModulo < 0 || opcaoMenuModulo > 4)
                opcaoMenuModulo = menu.exibirMenuModulo();

            switch (Modulos.values()[opcaoMenuModulo - 1]) {
                case ADMINISTRACAO:
                    executarModuloAdministracao();
                    break;
                case INGRESSOS:
                    executarModuloIngressos();
                    break;
                case GRAFICOS:
                    executarModuloGraficos();
                    break;
                case SAIR:
                    encerrarPrograma();
                    sairDoPrograma = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (!sairDoPrograma);
    }

    private void executarModuloAdministracao() {
        int opcaoMenuPrincipal, opcaoMenuTipo = 0;
        do {
            opcaoMenuPrincipal = menu.exibirMenuAdministracao();
            if (opcaoMenuPrincipal < 5 && opcaoMenuPrincipal > 0) {
                opcaoMenuTipo = menu.exibirMenuTipo();
            }

            tratarMenu(opcaoMenuPrincipal, opcaoMenuTipo);

        } while (opcaoMenuPrincipal != 5);
    }
    
    private void executarModuloIngressos() {
        ModuloAdministracao.getInstancia().moduloIngresso();
    }

    private void executarModuloGraficos() {
        ModuloAdministracao.getInstancia().moduloGraficos();
    }

    private void tratarMenu(int opcaoMenuPrincipal, int opcaoMenuTipo) {
        switch (opcaoMenuPrincipal) {
            case 1:
                if(opcaoMenuTipo == 1) {
                    cadastrarEvento();
                } else if(opcaoMenuTipo == 2) {
                    cadastrarLocal();
                } else {
                    cadastrarAtividade();
                }
                break;
            case 2:
                if(opcaoMenuTipo == 1) {
                    removerEvento();
                } else if(opcaoMenuTipo == 2) {
                    removerLocal();
                } else {
                    removerAtividade();
                }
                break;
            case 3:
                if(opcaoMenuTipo == 1) {
                    detalharEvento();
                } else if(opcaoMenuTipo == 2) {
                    detalharLocal();
                } else {
                    detalharAtividade();
                }
                break;
            case 4:
                if(opcaoMenuTipo == 1) {
                    listarEventos();
                } else if(opcaoMenuTipo == 2) {
                    listarLocais();
                } else {
                    listarAtividades();
                }
                break;
            case 5:
                executar();
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
        // se o usuário não estiver saindo do programa, pede para ele digitar ENTER antes de exibir o menu novamente
        //menu.aguardarContinuar();
    }

    private void cadastrarEvento() {
        System.out.println("-----CADASTRO DE EVENTO-----");
        String nome, tema, universidade;
        int ano;
        nome = Informacoes.getString("Nome do evento: ");
        tema = Informacoes.getString("Tema do evento: ");
        universidade = Informacoes.getString("Universidade sede:");
        ano = Informacoes.getInt("Ano de realizacao: ");

        if (ModuloAdministracao.getInstancia().cadastrarEvento(nome, tema, universidade, ano)){
            System.out.println("Evento cadastrado com sucesso!");
        } else {
            System.out.println("Nao foi possivel cadastrar. Ja existe um evento com o mesmo nome.");
        }
    }

    private void cadastrarAtividade() {
        try {
            System.out.println("-----CADASTRO DE ATIVIDADE-----");
            String nomeAtividade, nomeEvento, nomeLocal, dataInicio, horaInicio;
            TipoAtividade tipo = TipoAtividade.PALESTRA; //inicializando o tipo
        
            nomeAtividade = Informacoes.getString("Nome da Atividade:");
            nomeEvento = Informacoes.getString("Digite o nome do evento:");
            nomeLocal = Informacoes.getString("Digite o nome do local academico:");
            dataInicio = Informacoes.getString("Data da Atividade (dd/mm)");
            horaInicio = Informacoes.getString("Horario de inicio (hh:mm)");
    
            int op = 0;
            do{
                System.out.println("É uma:\n1- Palestra\n2- Minicurso");
                op = Informacoes.getInt("Digite sua opção:");
            }while (op < 1 || op > 2);
            if (op == 1) {
                tipo = TipoAtividade.PALESTRA;
                String nomePalestrante = Informacoes.getString("Qual o nome do palestrante?");
                int duracao = Informacoes.getInt("Qual a duracao em horas da palestra?");
                ModuloAdministracao.getInstancia().cadastrarAtividade(nomeAtividade, nomeEvento, nomeLocal, tipo, dataInicio, horaInicio, nomePalestrante, duracao);
            } else {
                tipo = TipoAtividade.MINICURSO;
                String instrutor = Informacoes.getString("Qual o nome do Instrutor?");
                int cargaHoraria = Informacoes.getInt("Qual a carga horária do minicurso?");
                ModuloAdministracao.getInstancia().cadastrarAtividade(nomeAtividade, nomeEvento, nomeLocal, tipo, dataInicio, horaInicio, instrutor, cargaHoraria);
            } 
            System.out.println("Cadastrado com sucesso!\n");
        } catch (AtividadeException e) {
            System.out.println("Erro ao cadastrar atividade: " + e.getMessage());
        }

    }
    
    private void cadastrarLocal() {
        System.out.println("-----CADASTRO DO LOCAL-----");
        String nomeLocal, departamento;
        int capacidade;
    
        nomeLocal = Informacoes.getString("Nome do local:");
        departamento = Informacoes.getString("Departamento:");
        capacidade = Informacoes.getInt("Capacidade:");
    
        if (ModuloAdministracao.getInstancia().cadastrarLocal(nomeLocal, departamento, capacidade)) {
            System.out.println("Local cadastrado com sucesso!");
        } else {
            System.out.println("Nao foi possivel cadastrar");
        }
    }
    
    private void removerEvento() {
        String nome;
        nome = Informacoes.getString("Digite o nome do evento a ser removido:");
    
        if (ModuloAdministracao.getInstancia().removerEvento(nome)) {
            System.out.println("Evento removido com sucesso!");
        } else {
            System.out.println("Nao foi possivel remover. O evento nao foi encontrado ou existe atividades cadastradas nele.");
        }
    }
    
    private void removerAtividade() {
        String nome;
        nome = Informacoes.getString("Digite o nome da atividade a ser removida:");
    
        if (ModuloAdministracao.getInstancia().removerAtividade(nome)) {
            System.out.println("Atividade removida com sucesso!");
        } else {
            System.out.println("Nao foi possivel remover.");
        }
    }
    
    private void removerLocal() {
        String nomeLocal;
        nomeLocal = Informacoes.getString("Digite o nome do local a ser removido:");
    
        if (ModuloAdministracao.getInstancia().removerLocal(nomeLocal)) {
            System.out.println("Local removido com sucesso!");
        } else {
            System.out.println("Nao foi possivel remover");
            System.out.println("O local nao foi encontrado ou existe atividades cadastradas para acontecer nele.");
        }
    }
    
    private void detalharEvento() {
        String nomeEvento = Informacoes.getString("Qual o nome do evento?");
        String detalhes = ModuloAdministracao.getInstancia().detalharEvento(nomeEvento);
        if(detalhes != null) {
            System.out.println(detalhes);
        } else {
            System.out.println("Evento nao encontado");
        }
    }
   
    private void detalharAtividade() {
        String nomeAtividade = Informacoes.getString("Qual o nome da atividade?");
        String detalhes = ModuloAdministracao.getInstancia().detalharAtividade(nomeAtividade);
        if (detalhes != null) {
            System.out.println(detalhes);
        } else {
            System.out.println("Atividade nao encontrada!");
        }
    }
   
    private void detalharLocal() {
        String nomeLocal = Informacoes.getString("Qual o nome do local?");
        String detalhes = ModuloAdministracao.getInstancia().detalharLocal(nomeLocal);
        if(detalhes != null) {
            System.out.println(detalhes);
        } else {
            System.out.println("Local nao encontrado");
        }
    }
    
    private void listarEventos() {
        ArrayList<String> listaNomesEventos = new ArrayList<>(ModuloAdministracao.getInstancia().getListaNomeEventos());

        if(listaNomesEventos.size() == 0){
            System.out.println("Nenhum evento encontrado");
        } else {
            System.out.println("Eventos:");
            for (String nomeEvento : listaNomesEventos) {
                System.out.println(nomeEvento);
            }
        }
    }

    private void listarAtividades() {
        String nomeEvento = Informacoes.getString("Listar atividades de qual evento?");
        ArrayList<String> listaNomesAtividades = new ArrayList<>(ModuloAdministracao.getInstancia().getListaNomeAtividades(nomeEvento));
        
        if(listaNomesAtividades.size() == 0){
            System.out.println("Nenhuma atividade encontrada para esse evento.");
        } else {
            System.out.println("Atividades para o eventos " + nomeEvento + ":");
            for (String nomeAtividade : listaNomesAtividades) {
                System.out.println(nomeAtividade);
            }
        }
    }

    private void listarLocais() {
        ArrayList<String> listaNomesLocais = new ArrayList<>(ModuloAdministracao.getInstancia().getListaNomeLocais());

        if(listaNomesLocais.size() == 0){
            System.out.println("Nenhum local encontrado");
        } else {
            System.out.println("Locais Academicos:");
            for (String nomeLocal : listaNomesLocais) {
                System.out.println(nomeLocal);
            }
        }
    }

    private void encerrarPrograma() {
        System.out.println("Encerrando programa...");
        ModuloAdministracao.getInstancia().encerrar();
    }
}
