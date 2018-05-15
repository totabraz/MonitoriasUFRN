package totabraz.com.monitoriasufrn.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Turma {


    @JsonProperty("codigo-componente")
    private String codigoComponente;

    @JsonProperty("codigo-turma")
    private String codigoTurma;

    @JsonProperty("descricao-horario")
    private String descricaoHorario;

    @JsonProperty("id-componente")
    private long idComponente;

    @JsonProperty("id-discente")
    private long idDiscente;

    @JsonProperty("id-docente")
    private long idDocente;

    @JsonProperty("id-docente-externo")
    private long idDocenteExterno;

    @JsonProperty("id-situacao-turma")
    private long idSituacaoTurma;

    @JsonProperty("id-turma")
    private long idTurma;

    @JsonProperty("id-unidade")
    private long idUnidade;

    @JsonProperty("nome-componente")
    private String nomeComponente;

    @JsonProperty("sigla-nivel")
    private String siglaNivel;
    private long ano;
    private String local;
    private long periodo;
    private boolean subturma;

    public Turma() {
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

    public long getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(long idComponente) {
        this.idComponente = idComponente;
    }

    public long getIdDiscente() {
        return idDiscente;
    }

    public void setIdDiscente(long idDiscente) {
        this.idDiscente = idDiscente;
    }

    public long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(long idDocente) {
        this.idDocente = idDocente;
    }

    public long getIdDocenteExterno() {
        return idDocenteExterno;
    }

    public void setIdDocenteExterno(long idDocenteExterno) {
        this.idDocenteExterno = idDocenteExterno;
    }

    public long getIdSituacaoTurma() {
        return idSituacaoTurma;
    }

    public void setIdSituacaoTurma(long idSituacaoTurma) {
        this.idSituacaoTurma = idSituacaoTurma;
    }

    public long getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(long idTurma) {
        this.idTurma = idTurma;
    }

    public long getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(long idUnidade) {
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

    public long getAno() {
        return ano;
    }

    public void setAno(long ano) {
        this.ano = ano;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public long getPeriodo() {
        return periodo;
    }

    public void setPeriodo(long periodo) {
        this.periodo = periodo;
    }

    public boolean isSubturma() {
        return subturma;
    }

    public void setSubturma(boolean subturma) {
        this.subturma = subturma;
    }
}
