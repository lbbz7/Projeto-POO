package org.example.projetopoo.data;

import org.example.projetopoo.model.Agendamento;
import org.example.projetopoo.model.Servico;
import org.example.projetopoo.model.StatusAgendamento;
import org.example.projetopoo.model.TipoUsuario;
import org.example.projetopoo.model.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class DataService {

    private static final String USUARIOS_FILE = "usuarios.txt";
    private static final String SERVICOS_FILE = "servicos.txt";
    private static final String AGENDAMENTOS_FILE = "agendamentos.txt";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Métodos para persistência de Usuários
    public void saveUsuarios(List<Usuario> usuarios) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USUARIOS_FILE))) {
            for (Usuario usuario : usuarios) {
                writer.printf("%s,%s,%s,%s\n",
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getSenha(),
                        usuario.getTipo());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    public List<Usuario> loadUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(USUARIOS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    usuarios.add(new Usuario(
                            parts[0],
                            parts[1],
                            parts[2],
                            TipoUsuario.valueOf(parts[3])));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de usuários não encontrado. Criando um novo...");
            // Criar um usuário admin padrão se o arquivo não existir
            Usuario admin = new Usuario("Admin", "admin@email.com", "123", TipoUsuario.ADMIN);
            usuarios.add(admin);
            saveUsuarios(usuarios);
        }
        return usuarios;
    }

    // Métodos para persistência de Serviços
    public void saveServicos(List<Servico> servicos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SERVICOS_FILE))) {
            for (Servico servico : servicos) {
                writer.printf("%d,%s,%s,%.2f\n",
                        servico.getId(),
                        servico.getNome(),
                        servico.getDescricao().replace(",", ";"), // Substitui vírgula por ponto e vírgula
                        servico.getPreco());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar serviços: " + e.getMessage());
        }
    }

    public List<Servico> loadServicos() {
        List<Servico> servicos = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(SERVICOS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    try {
                        servicos.add(new Servico(
                                Integer.parseInt(parts[0]),
                                parts[1],
                                parts[2].replace(";", ","), // Reverte a substituição
                                Double.parseDouble(parts[3])));
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter dados do serviço: " + e.getMessage());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de serviços não encontrado. Criando um novo...");
            Servico servicoPadrao = new Servico(1, "Manutenção de Ar", "Manutenção preventiva em ar condicionado", 150.0);
            servicos.add(servicoPadrao);
            saveServicos(servicos);
        }
        return servicos;
    }

    // Métodos para persistência de Agendamentos
    public void saveAgendamentos(List<Agendamento> agendamentos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(AGENDAMENTOS_FILE))) {
            for (Agendamento agendamento : agendamentos) {
                writer.printf("%d,%s,%d,%s,%s\n",
                        agendamento.getId(),
                        agendamento.getCliente().getEmail(),
                        agendamento.getServico().getId(),
                        agendamento.getDataHora().format(DATE_TIME_FORMATTER),
                        agendamento.getStatus());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar agendamentos: " + e.getMessage());
        }
    }

    public List<Agendamento> loadAgendamentos(List<Usuario> usuarios, List<Servico> servicos) {
        List<Agendamento> agendamentos = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(AGENDAMENTOS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String clienteEmail = parts[1];
                        int servicoId = Integer.parseInt(parts[2]);
                        LocalDateTime dataHora = LocalDateTime.parse(parts[3], DATE_TIME_FORMATTER);
                        StatusAgendamento status = StatusAgendamento.valueOf(parts[4]);

                        Optional<Usuario> cliente = usuarios.stream()
                                .filter(u -> u.getEmail().equals(clienteEmail))
                                .findFirst();

                        Optional<Servico> servico = servicos.stream()
                                .filter(s -> s.getId() == servicoId)
                                .findFirst();

                        if (cliente.isPresent() && servico.isPresent()) {
                            agendamentos.add(new Agendamento(id, cliente.get(), servico.get(), dataHora, status));
                        }
                    } catch (IllegalArgumentException e) {
                        System.err.println("Erro ao converter dados do agendamento: " + e.getMessage());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de agendamentos não encontrado. Criando um novo...");
        }
        return agendamentos;
    }
}