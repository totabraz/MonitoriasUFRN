package totabraz.com.monitoriasufrn.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User extends UserApi {
    private List<Vinculo> vinculos;

    public User() {
    }

    public List<Vinculo> getVinculos() { return vinculos; }

    public void setVinculos(List<Vinculo> vinculos) { this.vinculos = vinculos; }
}
