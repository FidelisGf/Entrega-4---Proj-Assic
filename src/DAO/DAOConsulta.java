package DAO;

import Model.Consulta;
import Model.Usuario; // Certifique-se de importar a classe Usuario
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DAOConsulta {
    private Connection connection;

    public DAOConsulta() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void inserirConsulta(Consulta consulta) {
        String sql = "INSERT INTO consultas (data_hora, id_paciente) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(consulta.getDataHora()));
            stmt.setInt(2, consulta.getPaciente().getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir consulta: " + e.getMessage(), e);
        }
    }

    public void atualizarConsulta(Consulta consulta) {
        String sql = "UPDATE consultas SET data_hora = ?, id_paciente = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(consulta.getDataHora()));
            stmt.setInt(2, consulta.getPaciente().getId());
            stmt.setString(3, consulta.getStatus());
            stmt.setInt(4, consulta.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar consulta: " + e.getMessage(), e);
        }
    }

    public void excluirConsulta(int id) {
        String sql = "DELETE FROM consultas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir consulta: " + e.getMessage(), e);
        }
    }

    public List<Consulta> consultaWeek(){
        String sql = """
                SELECT *
                    FROM agenda_pacientes
                    WHERE YEARWEEK(data_hora, 1) = YEARWEEK(CURRENT_DATE(), 1);
           """;
        List<Consulta> consultas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario paciente = new Usuario(
                        rs.getInt("id_paciente"),
                        rs.getString("paciente_nome"),
                        rs.getString("paciente_cpf")
                );
                consultas.add(new Consulta(
                        rs.getInt("id_consulta"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        paciente,
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas: " + e.getMessage(), e);
        }
        return consultas;
    }


    public List<Consulta> consultaSameMonth(){
        String sql = """
                 SELECT *
               FROM agenda_pacientes
              WHERE YEAR(data_hora) = YEAR(CURRENT_DATE()) AND MONTH(data_hora) = MONTH(CURRENT_DATE());
           """;
        List<Consulta> consultas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario paciente = new Usuario(
                        rs.getInt("id_paciente"),
                        rs.getString("paciente_nome"),
                        rs.getString("paciente_cpf")
                );
                consultas.add(new Consulta(
                        rs.getInt("id_consulta"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        paciente,
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas: " + e.getMessage(), e);
        }
        return consultas;
    }

    public List<Consulta> consultaActualDate(){
        String sql = """
              SELECT *
              FROM agenda_pacientes
              WHERE DATE(data_hora) >= CURRENT_DATE();
           """;
        List<Consulta> consultas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario paciente = new Usuario(
                        rs.getInt("id_paciente"),
                        rs.getString("paciente_nome"),
                        rs.getString("paciente_cpf")
                );
                consultas.add(new Consulta(
                        rs.getInt("id_consulta"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        paciente,
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas: " + e.getMessage(), e);
        }
        return consultas;
    }

    public Consulta buscarConsultaPorId(int id) {
        String sql = """
            SELECT c.id, c.data_hora, c.status, c.id_paciente, u.cpf, u.nome 
            FROM consultas c 
            INNER JOIN usuarios u ON c.id_paciente = u.id 
            WHERE c.id = ?
            """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario paciente = new Usuario(
                        rs.getInt("id_paciente"),
                        rs.getString("nome"),
                        rs.getString("cpf")
                );
                return new Consulta(
                        rs.getInt("id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        paciente,
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consulta: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Consulta> listarConsultas() {
        String sql = """
            SELECT c.id, c.data_hora, c.status, c.id_paciente, u.cpf, u.nome 
            FROM consultas c 
            INNER JOIN usuarios u ON c.id_paciente = u.id 
            """;
        List<Consulta> consultas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario paciente = new Usuario(
                        rs.getInt("id_paciente"),
                        rs.getString("nome"),
                        rs.getString("cpf")
                );
                consultas.add(new Consulta(
                        rs.getInt("id"),
                        rs.getTimestamp("data_hora").toLocalDateTime(),
                        paciente,
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas: " + e.getMessage(), e);
        }
        return consultas;
    }
}
