package totabraz.com.monitoriasufrn.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Subject {

    @JsonProperty("codigo-componente")
    private String codigoComponente;
    @JsonProperty("codigo-turma")
    private String codigoTurma;
    @JsonProperty("descricao-horario")
    private String descricaoHorario;
    @JsonProperty("id-componente")
    private int idComponente;
    @JsonProperty("id-discente")
    private int idDiscente;
    @JsonProperty("id-docente")
    private int idDocente;
    @JsonProperty("id-docente-externo")
    private int idDocenteExterno;
    @JsonProperty("id-situacao-turma")
    private int idSituacao;
    @JsonProperty("id-turma")
    private int idTurma;
    @JsonProperty("id-unidade")
    private int idUnidade;
    @JsonProperty("nome-componente")
    private String nomeComponente;
    @JsonProperty("sigla-nivel")
    private String siglaNivel;
    private String local;
    private int ano;
    private int periodo;
    private boolean subturma;

    public Subject() {
    }

    public String getCodigoComponente() {
        return codigoComponente;
    }

    public void setCodigoComponente(String codigoComponente) {
        this.codigoComponente = codigoComponente;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public String getDescricaoHorario() {
        return descricaoHorario;
    }

    public void setDescricaoHorario(String descricaoHorario) {
        this.descricaoHorario = descricaoHorario;
    }

    public int getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(int idComponente) {
        this.idComponente = idComponente;
    }

    public int getIdDiscente() {
        return idDiscente;
    }

    public void setIdDiscente(int idDiscente) {
        this.idDiscente = idDiscente;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public int getIdDocenteExterno() {
        return idDocenteExterno;
    }

    public void setIdDocenteExterno(int idDocenteExterno) {
        this.idDocenteExterno = idDocenteExterno;
    }

    public int getIdSituacao() {
        return idSituacao;
    }

    public void setIdSituacao(int idSituacao) {
        this.idSituacao = idSituacao;
    }

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public int getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(int idUnidade) {
        this.idUnidade = idUnidade;
    }

    public String getNomeComponente() {
        return nomeComponente;
    }

    public void setNomeComponente(String nomeComponente) {
        this.nomeComponente = nomeComponente;
    }

    public String getSiglaNivel() {
        return siglaNivel;
    }

    public void setSiglaNivel(String siglaNivel) {
        this.siglaNivel = siglaNivel;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public boolean isSubturma() {
        return subturma;
    }

    public void setSubturma(boolean subturma) {
        this.subturma = subturma;
    }
}
