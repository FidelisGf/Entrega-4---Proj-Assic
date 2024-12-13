package DAO;

import Model.Endereco;
import Model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOEndereco {
    private Connection connection;

    public DAOEndereco() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void inserirEndereco(Endereco endereco) {
        String sql = "INSERT INTO enderecos (estado, cidade, rua, numero_residencia, cep, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, endereco.getEstado());
            stmt.setString(2, endereco.getCidade());
            stmt.setString(3, endereco.getRua());
            stmt.setInt(4, endereco.getNumeroResidencia());
            stmt.setString(5, endereco.getCep());
            stmt.setInt(6, endereco.getUsuario().getId());
            stmt.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir endereço: " + e.getMessage(), e);
        }
    }

    public void atualizarEndereco(Endereco endereco) {
        String sql = "UPDATE enderecos SET estado = ?, cidade = ?, rua = ?, numero_residencia = ?, cep = ? WHERE id_endereco = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, endereco.getEstado());
            stmt.setString(2, endereco.getCidade());
            stmt.setString(3, endereco.getRua());
            stmt.setInt(4, endereco.getNumeroResidencia());
            stmt.setString(5, endereco.getCep());
            stmt.setInt(6, endereco.getIdEndereco());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar endereço: " + e.getMessage(), e);
        }
    }

    public void excluirEndereco(int id) {
        String sql = "DELETE FROM enderecos WHERE id_endereco = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir endereço: " + e.getMessage(), e);
        }
    }

    public Endereco buscarEnderecoPorId(int id) {
        String sql = "SELECT * FROM enderecos WHERE id_endereco = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario(); // Aqui, carregue o usuário relacionado (opcional)
                usuario.setId(rs.getInt("id_usuario"));

                return new Endereco(
                    rs.getInt("id_endereco"),
                    rs.getString("estado"),
                    rs.getString("cidade"),
                    rs.getString("rua"),
                    rs.getInt("numero_residencia"),
                    rs.getString("cep"),
                    usuario
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereço por ID: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Endereco> listarEnderecos() {
        String sql = "SELECT * FROM enderecos";
        List<Endereco> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(); // Aqui, carregue o usuário relacionado (opcional)
                usuario.setId(rs.getInt("id_usuario"));

                Endereco endereco = new Endereco(
                    rs.getInt("id_endereco"),
                    rs.getString("estado"),
                    rs.getString("cidade"),
                    rs.getString("rua"),
                    rs.getInt("numero_residencia"),
                    rs.getString("cep"),
                    usuario
                );
                lista.add(endereco);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar endereços: " + e.getMessage(), e);
        }
        return lista;
    }
}
