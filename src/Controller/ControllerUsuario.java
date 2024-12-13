package Controller;

import DAO.DAOUsuario;
import Model.Usuario;
import java.util.List;

public class ControllerUsuario {
    private DAOUsuario daoUsuario;

    public ControllerUsuario() {
        this.daoUsuario = new DAOUsuario();
    }

    public void adicionarUsuario(Usuario usuario) {
        daoUsuario.inserirUsuario(usuario);
    }

    public void atualizarUsuario(Usuario usuario) {
        daoUsuario.atualizarUsuario(usuario);
    }

    public void excluirUsuario(int id) {
        daoUsuario.excluirUsuario(id);
    }

    public Usuario buscarUsuario(int id) {
        return daoUsuario.buscarUsuario(id);
    }

    public List<Usuario> listarUsuarios() {
        return daoUsuario.listarUsuarios();
    }
}
