package br.ufla.gac106.s2023_1.TechWeek.views;

import br.ufla.gac106.s2023_1.TechWeek.Informacoes;

public class Menus {
    
    public int exibirMenuModulo() {
        System.out.println("Módulos disponíveis:");
        System.out.println("1- Módulo de Administração");
        System.out.println("2- Módulo de Ingressos");
        System.out.println("3- Módulo de Gráficos");
        System.out.println("4- Sair");
        return Informacoes.getInt("Digite sua opcao: ");
    }

    public int exibirMenuAdministracao() {
        System.out.println("Cadastrar, remover, detalhar ou listar eventos/atividades/locais?");
        System.out.println("1- Cadastrar");
        System.out.println("2- Remover");
        System.out.println("3- Detalhar");
        System.out.println("4- Listar");
        System.out.println("5- Voltar ao Menu de Modulos");
        return Informacoes.getInt("Digite sua opcao: ");
    }

    public int exibirMenuTipo() {
        System.out.println("1- Evento");
        System.out.println("2- Local");
        System.out.println("3- Atividade");
        return Informacoes.getInt("Digite sua opcao: ");
    }

    public void aguardarContinuar() {
        Informacoes.teclaEnter("\nDigite ENTER para continuar!");
    }
}

