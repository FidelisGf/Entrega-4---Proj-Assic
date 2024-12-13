package Controller;

import DAO.DAOEndereco;
import Model.Endereco;
import Model.Usuario;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;

public class ControllerEndereco {
    private DAOEndereco daoEndereco;

    public ControllerEndereco() {
        this.daoEndereco = new DAOEndereco();
    }

    public List<Endereco> listarEnderecos() {
        return daoEndereco.listarEnderecos();
    }

    private JSONObject consultarCep(String cep) {
        try {
            String apiUrl = "https://viacep.com.br/ws/" + cep + "/json/";
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new JSONObject(response.toString());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar CEP: " + e.getMessage(), e);
        }
    }

    public void adicionarEndereco(String cep, int numeroResidencia, Usuario usuario) {
        JSONObject enderecoInfo = consultarCep(cep);
        System.out.println("id:" + usuario.getId());
        if (!enderecoInfo.has("erro")) {
            Endereco endereco = new Endereco(
                0,
                enderecoInfo.getString("uf"),
                enderecoInfo.getString("localidade"),
                enderecoInfo.getString("logradouro"),
                numeroResidencia,
                cep,
                usuario
            );
            daoEndereco.inserirEndereco(endereco);
        } else {
            throw new RuntimeException("CEP inválido ou não encontrado.");
        }
    }

    public void atualizarEndereco(int idEndereco, String cep, int numeroResidencia, Usuario usuario) {
        JSONObject enderecoInfo = consultarCep(cep);
        if (!enderecoInfo.has("erro")) {
            Endereco endereco = new Endereco(
                idEndereco,
                enderecoInfo.getString("uf"),
                enderecoInfo.getString("localidade"),
                enderecoInfo.getString("logradouro"),
                numeroResidencia,
                cep,
                usuario
            );
            daoEndereco.atualizarEndereco(endereco);
        } else {
            throw new RuntimeException("CEP inválido ou não encontrado.");
        }
    }

    public Endereco buscarEndereco(int id) {
        return daoEndereco.buscarEnderecoPorId(id);
    }

    public void excluirEndereco(int id) {
        daoEndereco.excluirEndereco(id);
    }
}
