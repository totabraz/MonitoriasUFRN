package totabraz.com.monitoriasufrn.domain;

import java.util.ArrayList;

public class Monitor extends User {

    private String siapeProfessor;
    private ArrayList<Monitoring> monitorings;

    public Monitor() {
    }

    public ArrayList<Monitoring> getMonitorings() {
        return monitorings;
    }

    public void setMonitorings(ArrayList<Monitoring> monitorings) {
        this.monitorings = monitorings;
    }

    public String getSiapeProfessor() {
        return siapeProfessor;
    }

    public void setSiapeProfessor(String siapeProfessor) {
        this.siapeProfessor = siapeProfessor;
    }
}
