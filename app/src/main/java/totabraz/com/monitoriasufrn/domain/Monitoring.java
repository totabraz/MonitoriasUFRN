package totabraz.com.monitoriasufrn.domain;

public class Monitoring extends Turma {
    private Monitor monitor;
    private String horarioM;
    private String horarioT;
    private String horarioN;
    private String setor;
    private String sala;


    public Monitoring() {
        horarioM = null;
        horarioT = null;
        horarioN = null;
        setor = null;
        sala = null;
    }

    public String getClassTime(){
        String time = "";
        if (horarioM.length()>0) time = horarioM + " - ";
        if (horarioT.length()>0) time = horarioT + " - ";
        if (horarioN.length()>0) time = horarioN + " - ";
        return time;
    }


    public String getHorarioM() {
        return horarioM;
    }

    public void setHorarioM(String horarioM) {
        this.horarioM = horarioM;
    }

    public String getHorarioT() {
        return horarioT;
    }

    public void setHorarioT(String horarioT) {
        this.horarioT = horarioT;
    }

    public String getHorarioN() {
        return horarioN;
    }

    public void setHorarioN(String horarioN) {
        this.horarioN = horarioN;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }
}
