package View;

import Controller.ControllerEndereco;
import Controller.ControllerUsuario;
import Controller.ControllerConsulta;
import Model.Endereco;
import Model.Usuario;
import Model.Consulta;
import java.time.LocalDateTime;


import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MenuView {
    private ControllerUsuario controllerUsuario;
    private ControllerEndereco controllerEndereco;
    private ControllerConsulta controllerConsulta;
    private Scanner scanner;

    public MenuView() {
        this.controllerUsuario = new ControllerUsuario();
        this.controllerEndereco = new ControllerEndereco();
        this.controllerConsulta = new ControllerConsulta();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Adicionar Usuário");
            System.out.println("2. Atualizar Usuário");
            System.out.println("3. Buscar Usuario");
            System.out.println("4. Excluir Usuário");
            System.out.println("5. Listar Usuários");
            System.out.println("6. Adicionar Endereço");
            System.out.println("7. Atualizar Endereço");
            System.out.println("8. Excluir Endereço");
            System.out.println("9. Listar Endereços");
            System.out.println("10. Adicionar Consulta");
            System.out.println("11. Atualizar Consulta");
            System.out.println("12. Buscar Consulta");
            System.out.println("13. Excluir Consulta");
            System.out.println("14. Listar Consultas");
            System.out.println("15. Consultas na semana");
            System.out.println("16. Consultas para hoje");
            System.out.println("17. Consultas no mês");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1 -> adicionarUsuario();
                case 2 -> atualizarUsuario();
                case 3 -> buscarUsuario();
                case 4 -> excluirUsuario();
                case 5 -> listarUsuarios();
                case 6 -> adicionarEndereco();
                case 7 -> atualizarEndereco();
                case 8 -> excluirEndereco();
                case 9 -> listarEnderecos();
                case 10 -> adicionarConsulta();
                case 11 -> atualizarConsulta();
                case 12 -> buscarConsulta();
                case 13 -> excluirConsulta();
                case 14 -> listarConsultas();
                case 15 -> consultaWeek();
                case 16 -> consultaActualDate();
                case 17 -> consultaMonth();
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void adicionarConsulta() {
        System.out.println("\n=== Adicionar Consulta ===");
        System.out.print("Digite o ID do usuário associado: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite a data e hora da consulta (formato: dd/MM/yyyy HH:mm:ss): ");
        String dataHoraInput = scanner.nextLine();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            LocalDateTime dataHora = LocalDateTime.parse(dataHoraInput, formatter);

            Usuario usuario = controllerUsuario.buscarUsuario(idUsuario);
            if (usuario != null) {
                Consulta consulta = new Consulta(dataHora, usuario);
                controllerConsulta.adicionarConsulta(consulta);
                System.out.println("Consulta adicionada com sucesso!");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data e hora inválido. Use o formato: dd/MM/yyyy HH:mm:ss");
        }
    }


    private void atualizarConsulta() {
        System.out.println("\n=== Atualizar Consulta ===");
        System.out.print("Digite o ID da consulta: ");
        int idConsulta = scanner.nextInt();
        scanner.nextLine();
        Consulta consulta = controllerConsulta.buscarConsulta(idConsulta);
        if (consulta == null) {
            System.out.println("Consulta não encontrada.");
            return;
        }

        System.out.print("Digite o novo status da consulta: [Cancelado, Pendente, Concluido]: ");
        consulta.setStatus(scanner.nextLine());
        System.out.printf("Digite o id do novo usuario, se necessário. (Id atual %d)", consulta.getPaciente().getId());
        String novoId = scanner.nextLine();
        if (!novoId.isEmpty()) {
            consulta.getPaciente().setId(Integer.parseInt(novoId));
        }

        controllerConsulta.atualizarConsulta(consulta);
        System.out.println("Consulta atualizada com sucesso!");

    }

    private void buscarConsulta() {
        System.out.println("\n=== Buscar Consulta ===");
        System.out.print("Digite o ID da consulta: ");
        int idConsulta = scanner.nextInt();
        Consulta consulta = controllerConsulta.buscarConsulta(idConsulta);
        if (consulta != null) {
            System.out.printf("ID: %d | Status: %s | Usuário ID: %d | Nome Usuário: %s%n",
                    consulta.getId(), consulta.getStatus(), consulta.getPaciente().getId(), consulta.getPaciente().getNome());
        } else {
            System.out.println("Consulta não encontrada.");
        }
    }

    private void excluirConsulta() {
        System.out.println("\n=== Excluir Consulta ===");
        System.out.print("Digite o ID da consulta: ");
        int idConsulta = scanner.nextInt();
        controllerConsulta.excluirConsulta(idConsulta);
        System.out.println("Consulta excluída com sucesso!");
    }

    private void listarConsultas() {
        System.out.println("\n=== Lista de Consultas ===");
        for (Consulta consulta : controllerConsulta.listarConsultas()) {
            System.out.printf("ID: %d | Status: %s | Usuário ID: %d | Nome Usuário: %s%n",
                    consulta.getId(), consulta.getStatus(), consulta.getPaciente().getId(), consulta.getPaciente().getNome());
        }
    }


    private void consultaWeek(){
        System.out.println("\n=== Lista de Consultas ===");
        for (Consulta consulta : controllerConsulta.consultaWeek()) {
            System.out.printf("ID: %d | Status: %s | Usuário ID: %d | Nome Usuário: %s%n",
                    consulta.getId(), consulta.getStatus(), consulta.getPaciente().getId(), consulta.getPaciente().getNome());
        }
    }
    private void consultaMonth(){
        System.out.println("\n=== Lista de Consultas ===");
        for (Consulta consulta : controllerConsulta.consultaActualMonth()) {
            System.out.printf("ID: %d | Status: %s | Usuário ID: %d | Nome Usuário: %s%n",
                    consulta.getId(), consulta.getStatus(), consulta.getPaciente().getId(), consulta.getPaciente().getNome());
        }
    }



    private void consultaActualDate(){
        System.out.println("\n=== Lista de Consultas ===");
        for (Consulta consulta : controllerConsulta.consultaActualDate()) {
            System.out.printf("ID: %d | Status: %s | Usuário ID: %d | Nome Usuário: %s%n",
                    consulta.getId(), consulta.getStatus(), consulta.getPaciente().getId(), consulta.getPaciente().getNome());
        }
    }



    private void adicionarUsuario() {
        System.out.println("\n=== Adicionar Usuário ===");
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o e-mail: ");
        String email = scanner.nextLine();
        System.out.print("Digite o cpf: ");
        String cpf = scanner.nextLine();
        System.out.print("Digite o telefone: ");
        String telefone = scanner.nextLine();


        Usuario usuario = new Usuario(0, nome, cpf, email, telefone);
        controllerUsuario.adicionarUsuario(usuario);
        System.out.println("Usuário adicionado com sucesso!");
    }

    private void buscarUsuario() {
        System.out.println("\n=== Buscar Usuário ===");
        System.out.print("Digite o id do usuario: ");
        int id = scanner.nextInt();
        Usuario usuario = controllerUsuario.buscarUsuario(id);
        if (usuario != null) {
            System.out.printf( "ID: %d | Nome: %s | Cpf: %s | Email: %s | Telefone: %s| ",
                    id, usuario.getNome(), usuario.getCpf(), usuario.getEmail(), usuario.getTelefone());
        }else {
            System.out.printf("Usuário não encontrado!");
        }
    }

    private void atualizarUsuario() {
        System.out.println("\n=== Atualizar Usuário ===");
        System.out.print("Digite o ID do usuário: ");
        int idUsuario = scanner.nextInt();

        Usuario usuario = controllerUsuario.buscarUsuario(idUsuario);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        scanner.nextLine();

        System.out.print("Digite o novo nome (ou pressione Enter para manter: " + usuario.getNome() + "): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isEmpty()) {
            usuario.setNome(novoNome);
        }
        System.out.print("Digite o novo e-mail (ou pressione Enter para manter: " + usuario.getEmail() + "): ");
        String novoEmail = scanner.nextLine();
        if (!novoEmail.isEmpty()) {
            usuario.setEmail(novoEmail);
        }
        System.out.print("Digite o novo CPF (ou pressione Enter para manter: " + usuario.getCpf() + "): ");
        String novoCpf = scanner.nextLine();
        if (!novoCpf.isEmpty()) {
            usuario.setCpf(novoCpf);
        }
        System.out.print("Digite o novo telefone (ou pressione Enter para manter: " + usuario.getTelefone() + "): ");
        String novoTelefone = scanner.nextLine();
        if (!novoTelefone.isEmpty()) {
            usuario.setTelefone(novoTelefone);
        }
        controllerUsuario.atualizarUsuario(usuario);
        System.out.println("Usuário atualizado com sucesso!");
    }

    private void excluirUsuario() {
        System.out.println("\n=== Excluir Usuário ===");
        System.out.print("Digite o ID do usuário: ");
        int idUsuario = scanner.nextInt();

        controllerUsuario.excluirUsuario(idUsuario);
        System.out.println("Usuário excluído com sucesso!");
    }

    private void listarUsuarios() {
        System.out.println("\n=== Lista de Usuários ===");
        for (Usuario usuario : controllerUsuario.listarUsuarios()) {
            System.out.printf("ID: %d | Nome: %s | E-mail: %s%n", usuario.getId(), usuario.getNome(), usuario.getEmail());
        }
    }

    private void adicionarEndereco() {
        System.out.println("\n=== Adicionar Endereço ===");
        System.out.print("Digite o ID do usuário: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha
        System.out.print("Digite o CEP: ");
        String cep = scanner.nextLine();
        System.out.print("Digite o número da residência: ");
        int numeroResidencia = scanner.nextInt();

        Usuario usuario = controllerUsuario.buscarUsuario(idUsuario);

        if (usuario != null) {
            controllerEndereco.adicionarEndereco(cep, numeroResidencia, usuario);
            System.out.println("Endereço adicionado com sucesso!");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    private void atualizarEndereco() {
        System.out.println("\n=== Atualizar Endereço ===");
        System.out.print("Digite o ID do endereço: ");
        int idEndereco = scanner.nextInt();
        scanner.nextLine();
        Endereco endereco = controllerEndereco.buscarEndereco(idEndereco);
        System.out.print("Digite o novo CEP: ");
        String cep = scanner.nextLine();
        System.out.print("Digite o novo número da residência: ");
        int numeroResidencia = scanner.nextInt();
        Usuario usuario = controllerUsuario.buscarUsuario(endereco.getUsuario().getId());
        controllerEndereco.atualizarEndereco(idEndereco, cep, numeroResidencia, usuario);
        System.out.println("Endereço atualizado com sucesso!");
    }

    private void excluirEndereco() {
        System.out.println("\n=== Excluir Endereço ===");
        System.out.print("Digite o ID do endereço: ");
        int idEndereco = scanner.nextInt();
        controllerEndereco.excluirEndereco(idEndereco);
        System.out.println("Endereço excluído com sucesso!");
    }

    private void listarEnderecos() {
        System.out.println("\n=== Lista de Endereços ===");
        for (Endereco endereco : controllerEndereco.listarEnderecos()) {
            System.out.printf(
                "ID: %d | Estado: %s | Cidade: %s | Rua: %s | Número: %d | CEP: %s | Usuário ID: %d%n",
                endereco.getIdEndereco(),
                endereco.getEstado(),
                endereco.getCidade(),
                endereco.getRua(),
                endereco.getNumeroResidencia(),
                endereco.getCep(),
                endereco.getUsuario().getId()
            );
        }
    }

    public static void main(String[] args) {
        MenuView menu = new MenuView();
        menu.exibirMenu();
    }
}
