package totabraz.com.monitoriasufrn.domain;

import java.util.ArrayList;

public class Monitor extends User {

    private ArrayList<Monitoring> monitorings;

    public Monitor() {
    }

    public ArrayList<Monitoring> getMonitorings() {
        return monitorings;
    }

    public void setMonitorings(ArrayList<Monitoring> monitorings) {
        this.monitorings = monitorings;
    }

    public void setupUser(User user) {
        this.setChaveFoto(user.getChaveFoto());
        this.setCpfCnpj(user.getCpfCnpj());
        this.setIdFoto(user.getIdFoto());
        this.setIdUnidade(user.getIdUnidade());
        this.setIdUsuario(user.getIdUsuario());
        this.setNomePessoa(user.getNomePessoa());
        this.setAtivo(user.getAtivo());
        this.setEmail(user.getEmail());
        this.setLogin(user.getLogin());
        this.setVinculos(user.getVinculos());
    }
}
