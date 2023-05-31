package telas;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author leonardo.prado
 */
public class LoginCLI {
    private static final Logger logger = Logger.getLogger(LoginCLI.class.getName());

    public static void main(String[] args) {
        try {
            logFormatacao();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Sobrenome: ");
        String sobrenome = scanner.nextLine();

        System.out.print("HostName: ");
        String hostname = scanner.nextLine();

        // Simulating the retrieval of data from the database
        List<Maquina> maquinas = retrieveMaquinasFromDatabase(hostname);

        if (maquinas.size() > 0) {
            Maquina maquina = maquinas.get(0);
            if (maquina.getNomeDono().equals(nome) && maquina.getSobrenomeDono().equals(sobrenome) && maquina.getHostName().equals(hostname)) {
                System.out.println("Login realizado com sucesso!");
                logger.info("Login realizado por " + nome + " efetuado com sucesso!!");
                // Call the desired method or perform the desired actions after successful login
            } else {
                System.out.println("Credenciais inválidas!");
                logger.severe("Login realizado por " + nome + " falhou!!");
            }
        } else {
            System.out.println("Não encontrado");
        }
    }

    public static List<Maquina> retrieveMaquinasFromDatabase(String hostname) {
        // Simulating the retrieval of data from the database
        List<Maquina> maquinas = new ArrayList<>();

        // Perform the necessary database query and populate the maquinas list
        
        // Dummy data for illustration
        Maquina maquina = new Maquina();
        maquina.setNomeDono("John");
        maquina.setSobrenomeDono("Doe");
        maquina.setHostName(hostname);
        maquinas.add(maquina);

        return maquinas;
    }

    public static void logFormatacao() throws IOException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dataFormatada = dateFormat.format(date);
        Path pathW = Paths.get("C://Users//leonardo.prado//OneDrive - BANCO ARBI SA//Área de Trabalho//JAR");

        if (!Files.exists(pathW)) {
            Files.createDirectory(pathW);
        }

        FileHandler fileHandler = new FileHandler(String.format("C://Users//leonardo.prado//OneDrive - BANCO ARBI SA//Área de Trabalho//JAR%s.txt", dataFormatada), true);

        fileHandler.setFormatter(new Formatter() {
            private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd >> HH:mm:ss");

            public String format(LogRecord record) {
                StringBuilder builder = new StringBuilder();
                builder.append(dateFormat.format(new Date())).append(" ");
                builder.append(record.getLevel()).append(": ");
                builder.append(record.getMessage()).append(" (");
                builder.append(record.getSourceClassName()).append(".");
                builder.append(record.getSourceMethodName()).append(")");
                builder.append(System.lineSeparator());
                return builder.toString();
            }
        });

        logger.addHandler(fileHandler);
        logger.setLevel(Level.ALL);
    }

    // Dummy Maquina class for illustration
    private static class Maquina {
        private String nomeDono;
        private String sobrenomeDono;
        private String hostName;

        public String getNomeDono() {
            return nomeDono;
        }

        public void setNomeDono(String nomeDono) {
            this.nomeDono = nomeDono;
        }

        public String getSobrenomeDono() {
            return sobrenomeDono;
        }

        public void setSobrenomeDono(String sobrenomeDono) {
            this.sobrenomeDono = sobrenomeDono;
        }

        public String getHostName() {
            return hostName;
        }

        public void setHostName(String hostName) {
            this.hostName = hostName;
        }
    }
}
                        