package totabraz.com.monitoriasufrn.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {
    @JsonProperty("chave-foto")
    private String chaveFoto;
    @JsonProperty("cpf-cnpj")
    private String cpfCnpj;
    @JsonProperty("id-foto")
    private String idFoto;
    @JsonProperty("id-unidade")
    private String idUnidade;
    @JsonProperty("id-usuario")
    private String idUsuario;
    @JsonProperty("nome-pessoa")
    private String nomePessoa;
    private String ativo;
    private String email;
    private String login;
    private List<Vinculo> vinculos;

    public User() {
    }

    public String getChaveFoto() {
        return chaveFoto;
    }

    public void setChaveFoto(String chaveFoto) {
        this.chaveFoto = chaveFoto;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(String idFoto) {
        this.idFoto = idFoto;
    }

    public String getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(String idUnidade) {
        this.idUnidade = idUnidade;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Vinculo> getVinculos() { return vinculos; }

    public void setVinculos(List<Vinculo> vinculos) { this.vinculos = vinculos; }
}
