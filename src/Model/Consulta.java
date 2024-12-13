package Model;

import java.time.LocalDateTime;

public class Consulta {
    private int id;
    private LocalDateTime dataHora;
    private Usuario paciente;
    private String status; 

    public Consulta() {}

    public Consulta(LocalDateTime dataHora, Usuario paciente, String status) {
        this.dataHora = dataHora;
        this.paciente = paciente;
        this.status = status;
    }

    public Consulta(LocalDateTime dataHora, Usuario paciente) {
        this.dataHora = dataHora;
        this.paciente = paciente;

    }

    public Consulta(int id, LocalDateTime dataHora, Usuario paciente, String status) {
        this.id = id;
        this.dataHora = dataHora;
        this.paciente = paciente;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Usuario getPaciente() {
        return paciente;
    }

    public void setPaciente(Usuario paciente) {
        this.paciente = paciente;
    }

    public String getStatus() {
        return status;
    }

    public String getPacienteCpf() {
        return paciente.getCpf();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", dataHora=" + dataHora +
                ", paciente=" + paciente +
                ", status='" + status + '\'' +
                '}';
    }
}
