package br.ufla.gac106.s2023_1.TechWeek.atividades;

public class Palestra extends Atividade {
    private String nomePalestrante;
    private int duracaoEmHoras;
    
    public Palestra(String nome, Evento evento, TipoAtividade tipoAtividade, LocalAcademico localAcademico,
    String dataInicio, String horaInicio, String nomePalestrante, int duracaoEmHoras) {
        super(nome, evento, tipoAtividade, localAcademico, dataInicio, horaInicio, 50.0, 50);
        this.nomePalestrante = nomePalestrante;
        this.duracaoEmHoras = duracaoEmHoras;
    }

    public String getNomePalestrante() {
        return nomePalestrante;
    }

    public int getDuracaoEmHoras() {
        return duracaoEmHoras;
    }

    @Override
    public String getDescricao() {
        return "Palestra " + getNome() + " ,com duracao de " + getDuracaoEmHoras() + " horas, ministrada por " + getNomePalestrante() + " ,no " + getEvento() + " que ocorreu no " + getLocalAcademico().getNome() + " no dia " + getDataInicio() + " as " + getHoraInicio();
    }
    
}
