package totabraz.com.monitoriasufrn.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by totabraz on 27/02/18.
 */

public class Vinculo {

    @JsonProperty("id-lotacao")
    private String idLotacao;
    @JsonProperty("id-tipo-vinculo")
    private String idTipoVinculo;
    @JsonProperty("id-vinculo")
    private String idVinculo;
    @JsonProperty("tipo-vinculo")
    private String tipoVinculo;
    private String ativo;
    private String identificador;
    private String lotacao;
    private String situacao;
    private ArrayList<String> profAvaliados;

    public ArrayList<String> getProfAvaliados() { return profAvaliados; }

    public void setProfAvaliados(ArrayList<String> profAvaliados) {this.profAvaliados = profAvaliados; }

    public String getIdLotacao() {
        return idLotacao;
    }

    public void setIdLotacao(String idLotacao) {
        this.idLotacao = idLotacao;
    }

    public String getIdTipoVinculo() {
        return idTipoVinculo;
    }

    public void setIdTipoVinculo(String idTipoVinculo) {
        this.idTipoVinculo = idTipoVinculo;
    }

    public String getIdVinculo() {
        return idVinculo;
    }

    public void setIdVinculo(String idVinculo) {
        this.idVinculo = idVinculo;
    }

    public String getTipoVinculo() {
        return tipoVinculo;
    }

    public void setTipoVinculo(String tipoVinculo) {
        this.tipoVinculo = tipoVinculo;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getLotacao() {
        return lotacao;
    }

    public void setLotacao(String lotacao) {
        this.lotacao = lotacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
