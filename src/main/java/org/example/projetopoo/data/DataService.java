package org.example.projetopoo.data;

import org.example.projetopoo.model.Agendamento;
import org.example.projetopoo.model.Servico;
import org.example.projetopoo.model.StatusAgendamento;
import org.example.projetopoo.model.TipoUsuario;
import org.example.projetopoo.model.Usuario;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataService {

    private static final String USUARIOS_FILE = "usuarios.txt";
    private static final String AGENDAMENTOS_FILE = "agendamentos.txt";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void saveUsuarios(List<Usuario> usuarios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USUARIOS_FILE))) {
            for (Usuario usuario : usuarios) {
                String line = String.format("%s,%s,%s,%s%n",
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getSenha(),
                        usuario.getTipo());
                writer.write(line);
            }
            System.out.println("Usuários salvos com sucesso em " + USUARIOS_FILE);
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    public List<Usuario> loadUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USUARIOS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String nome = parts[0];
                    String email = parts[1];
                    String senha = parts[2];
                    TipoUsuario tipo = TipoUsuario.valueOf(parts[3].toUpperCase().trim());

                    Usuario usuario = new Usuario(nome, email, senha, tipo);
                    usuarios.add(usuario);
                }
            }
            System.out.println("Usuários carregados com sucesso do arquivo.");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de usuários não encontrado. Iniciando com lista vazia.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de formato de dados no arquivo de usuários: " + e.getMessage());
        }
        return usuarios;
    }

    public void saveAgendamentos(List<Agendamento> agendamentos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AGENDAMENTOS_FILE))) {
            for (Agendamento agendamento : agendamentos) {
                String line = String.format("%d,%s,%s,%s,%s%n",
                        agendamento.getId(),
                        agendamento.getCliente().getEmail(),
                        agendamento.getServico().getNome(),
                        agendamento.getDataHora().format(DATE_TIME_FORMATTER),
                        agendamento.getStatus());
                writer.write(line);
            }
            System.out.println("Agendamentos salvos com sucesso em " + AGENDAMENTOS_FILE);
        } catch (IOException e) {
            System.err.println("Erro ao salvar agendamentos: " + e.getMessage());
        }
    }

    public List<Agendamento> loadAgendamentos(List<Usuario> usuarios, List<Servico> servicos) {
        List<Agendamento> agendamentos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(AGENDAMENTOS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String clienteEmail = parts[1];
                    String servicoNome = parts[2];
                    LocalDateTime dataHora = LocalDateTime.parse(parts[3], DATE_TIME_FORMATTER);
                    StatusAgendamento status = StatusAgendamento.valueOf(parts[4].trim());

                    Usuario cliente = usuarios.stream()
                            .filter(u -> u.getEmail().equals(clienteEmail))
                            .findFirst()
                            .orElse(null);

                    Servico servico = servicos.stream()
                            .filter(s -> s.getNome().equals(servicoNome))
                            .findFirst()
                            .orElse(null);

                    if (cliente != null && servico != null) {
                        Agendamento agendamento = new Agendamento(id, cliente, servico, dataHora, status);
                        agendamentos.add(agendamento);
                    }
                }
            }
            System.out.println("Agendamentos carregados com sucesso.");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de agendamentos não encontrado. Iniciando com lista vazia.");
        } catch (IOException | IllegalArgumentException e) { // <-- Linha corrigida
            System.err.println("Erro ao carregar agendamentos: " + e.getMessage());
        }
        return agendamentos;
    }
}