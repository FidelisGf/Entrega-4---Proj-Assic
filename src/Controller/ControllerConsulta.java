package Controller;

import DAO.DAOConsulta;
import Model.Consulta;
import java.util.List;

public class ControllerConsulta {
    private DAOConsulta daoConsulta;

    public ControllerConsulta() {
        this.daoConsulta = new DAOConsulta();
    }

    public void adicionarConsulta(Consulta consulta) {
        daoConsulta.inserirConsulta(consulta);
    }

    public void atualizarConsulta(Consulta consulta) {
        daoConsulta.atualizarConsulta(consulta);
    }

    public List<Consulta> consultaWeek(){
        return daoConsulta.consultaWeek();
    }

    public List<Consulta> consultaActualDate(){
        return daoConsulta.consultaActualDate();
    }

    public List<Consulta> consultaActualMonth(){
        return daoConsulta.consultaSameMonth();
    }

    public void excluirConsulta(int id) {
        daoConsulta.excluirConsulta(id);
    }

    public Consulta buscarConsulta(int id) {
        return daoConsulta.buscarConsultaPorId(id);
    }

    public List<Consulta> listarConsultas() {
        return daoConsulta.listarConsultas();
    }
}
