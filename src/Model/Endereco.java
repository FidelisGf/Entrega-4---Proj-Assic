package Model;

public class Endereco {
    private int idEndereco;
    private String estado;
    private String cidade;
    private String rua;
    private int numeroResidencia;
    private String cep;
    private Usuario usuario;

    public Endereco() {}

    public Endereco(int idEndereco, String estado, String cidade, String rua, int numeroResidencia, String cep, Usuario usuario) {
        this.idEndereco = idEndereco;
        this.estado = estado;
        this.cidade = cidade;
        this.rua = rua;
        this.numeroResidencia = numeroResidencia;
        this.cep = cep;
        this.usuario = usuario;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumeroResidencia() {
        return numeroResidencia;
    }

    public void setNumeroResidencia(int numeroResidencia) {
        this.numeroResidencia = numeroResidencia;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
