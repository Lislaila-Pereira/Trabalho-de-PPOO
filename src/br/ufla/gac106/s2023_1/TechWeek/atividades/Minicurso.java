package br.ufla.gac106.s2023_1.TechWeek.atividades;

public class Minicurso extends Atividade{
    private String instrutor;
    private int cargaHoraria;
    
    public Minicurso(String nome, Evento evento, TipoAtividade tipoAtividade, LocalAcademico localAcademico,
    String dataInicio, String horaInicio, String instrutor, int cargaHoraria) {
        super(nome, evento, tipoAtividade, localAcademico, dataInicio, horaInicio, 100.0, 30);
        this.instrutor = instrutor;
        this.cargaHoraria = cargaHoraria;
    }

    public String getInstrutor() {
        return instrutor;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    } 

    @Override
    public String getDescricao() {
        return "Minicurso " + getNome() + " ,com carga horaria de " + getCargaHoraria() + ", ministrada por " + getInstrutor() + " ,no " + getEvento() + " que ocorreu no " + getLocalAcademico().getNome() + " no dia " + getDataInicio() + " as " + getHoraInicio();
    }
}
