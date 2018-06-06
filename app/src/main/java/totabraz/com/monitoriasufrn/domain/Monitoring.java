package totabraz.com.monitoriasufrn.domain;

public class Monitoring {
    private User monitor;
    private String horarioM;
    private String horarioT;
    private String horarioN;
    private String setor;
    private String sala;
    private String dia;
    private String codigoComponente;
    private String nomeComponente;
    private String siglaComponente;
    private String Observacao;


    public Monitoring() {
        horarioM = null;
        horarioT = null;
        horarioN = null;
        setor = null;
        sala = null;
    }

    public String getClassTime() {
        String time = "";
        if (horarioM.length() > 0) time += horarioM;
        if (horarioT.length() > 0) time += horarioT;
        if (horarioN.length() > 0) time += horarioN;
        return time;
    }

    public User getMonitor() {
        return monitor;
    }

    public void setMonitor(User monitor) {
        this.monitor = monitor;
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

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getCodigoComponente() {
        return codigoComponente;
    }

    public void setCodigoComponente(String codigoComponente) {
        this.codigoComponente = codigoComponente;
    }

    public String getNomeComponente() {
        return nomeComponente;
    }

    public void setNomeComponente(String nomeComponente) {
        this.nomeComponente = nomeComponente;
    }

    public String getSiglaComponente() {
        return siglaComponente;
    }

    public void setSiglaComponente(String siglaComponente) {
        this.siglaComponente = siglaComponente;
    }

    public String getObservacao() {
        return Observacao;
    }

    public void setObservacao(String observacao) {
        Observacao = observacao;
    }
}
