/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inserts;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import tabelas.Componente;

/**
 *
 * @author mari
 */
public class Insercao {

    //O objetivo deste método é inserir no banco os dados da tabela componente
    public static void inserirDadosComponente(JdbcTemplate conexaoAzure, JdbcTemplate conexaoMysql, Integer fkMaquina) {
        //CRIA A CONEXÃO COM O LOOCA
        Looca looca = new Looca();

        //Acessando as classes
        Processador cpu = looca.getProcessador();
        Memoria memoria = looca.getMemoria();
        DiscoGrupo grupoDeDiscos = looca.getGrupoDeDiscos();
        List<Disco> discos = grupoDeDiscos.getDiscos();

        //Inserindo o Disco na tabela Componente
        for (int i = 0; i < discos.size(); i++) {
            Disco disco = discos.get(i);
            Double totalDisco = disco.getTamanho() / Math.pow(10, 9);

            //MARI VC TEM Q VALIDAR OS NULL DO FK
            //INSERT DO DISCO AZURE
            conexaoAzure.update("INSERT INTO Componente (nomeComponente, total, modelo, fkMaquina) VALUES (?, ?, ?, ?);",
                    "Disco Rígido", totalDisco, disco.getModelo(), fkMaquina);
            //INSERT DO DISCO DOCKER
            conexaoMysql.update("INSERT INTO Componente (nomeComponente, total, modelo, fkMaquina) VALUES (?, ?, ?, ?);",
                    "Disco Rígido", totalDisco, disco.getModelo(), fkMaquina);
        }

        //Inserindo o CPU na tabela Componente
        String modeloCpu = cpu.getNome();
        Double totalCpu = cpu.getFrequencia() / Math.pow(10, 9);
        //INSERT DA CPU AZURE
        conexaoAzure.update("INSERT INTO Componente (nomeComponente, total, modelo, fkMaquina) VALUES (?, ?, ?, ?);",
                "Processador", totalCpu, modeloCpu, fkMaquina);
        //INSERT DA CPU DOCKER
        conexaoMysql.update("INSERT INTO Componente (nomeComponente, total, modelo, fkMaquina) VALUES (?, ?, ?, ?);",
                "Processador", totalCpu, modeloCpu, fkMaquina);

        //Inserindo a RAM na tabela Componente
        Double totalRam = memoria.getTotal() / Math.pow(10, 9);
        //INSERT DA CPU AZURE
        conexaoAzure.update("INSERT INTO Componente (nomeComponente, total, modelo, fkMaquina) VALUES (?, ?, ?, ?);",
                "Memória RAM", totalRam, null, fkMaquina);
        //INSERT DA CPU DOCKER
        conexaoMysql.update("INSERT INTO Componente (nomeComponente, total, modelo, fkMaquina) VALUES (?, ?, ?, ?);",
                "Memória RAM", totalRam, null, fkMaquina);

    }

    //O objetivo deste método é inserir no banco os dados da tabela log
    public static void inserirDadosLog(JdbcTemplate conexaoAzure, JdbcTemplate conexaoMysql, Componente componente) {
        //CRIA A CONEXÃO COM O LOOCA
        Looca looca = new Looca();

        //Acessando as classes
        Processador cpu = looca.getProcessador();
        Memoria memoria = looca.getMemoria();
        DiscoGrupo grupoDeDiscos = looca.getGrupoDeDiscos();
        List<Disco> discos = grupoDeDiscos.getDiscos();
        List<Volume> volumes = grupoDeDiscos.getVolumes();

        //A GENTE VAI INSERIR NA TABELA DE LOG OS DADOS REFERENTE AO COMPONENTE
        if (componente.getNomeComponente().equalsIgnoreCase("Processador")) {
            //Inserindo o emUso da CPU na tabela Log
            Double cpuEmUso = cpu.getUso();

            //INSERT DO DISCO AZURE
            conexaoAzure.update("INSERT INTO Log (momentoCaptura, emUso, fkComponente, fkMaquina) VALUES (GETDATE(), ?, ?, ?);",
                    cpuEmUso, componente.getIdComponente(), componente.getFkMaquina());
            // INSERT DA CPU DOCKER
            conexaoMysql.update("INSERT INTO Log (momentoCaptura, emUso, fkComponente, fkMaquina) VALUES (CURRENT_TIMESTAMP, ?, ?, null);",
                    cpuEmUso, componente.getIdComponente());
        } else if (componente.getNomeComponente().equalsIgnoreCase("Memória RAM")) {
            //Inserindo o emUso da RAM na tabela Log
            Double ramEmUso = memoria.getEmUso() / Math.pow(10, 9);
            //INSERT DO DISCO AZURE
            conexaoAzure.update("INSERT INTO Log (momentoCaptura, emUso, fkComponente, fkMaquina) VALUES (GETDATE(), ?, ?, ?);",
                    ramEmUso, componente.getIdComponente(), componente.getFkMaquina());
            //INSERT DA RAM DOCKER
            conexaoMysql.update("INSERT INTO Log (momentoCaptura, emUso, fkComponente, fkMaquina) VALUES (CURRENT_TIMESTAMP, ?, ?, null);",
                    ramEmUso, componente.getIdComponente());
        } else if (componente.getNomeComponente().equalsIgnoreCase("Disco Rígido")) {
            for (int i = 0; i < discos.size(); i++) {
                if (discos.get(i).getModelo().equalsIgnoreCase(componente.getModelo())) {
                    Volume volume = volumes.get(i);
                    Double discoEmUso = (volume.getTotal() - volume.getDisponivel()) / Math.pow(10, 9);

                    //INSERT DO DISCO AZURE
                    conexaoAzure.update("INSERT INTO Log (momentoCaptura, emUso, fkComponente, fkMaquina) VALUES (GETDATE(), ?, ?, ?);",
                            discoEmUso, componente.getIdComponente(), componente.getFkMaquina());
                    //INSERT DO DISCO DOCKER
                    conexaoMysql.update("INSERT INTO Log (momentoCaptura, emUso, fkComponente, fkMaquina) VALUES (CURRENT_TIMESTAMP, ?, ?, null);",
                            discoEmUso, componente.getIdComponente());
                }
            }
        }
        
        System.out.println(String.format("Dados inseridos para o componente %s", componente.getNomeComponente()));

//        //Inserindo o emUso do Disco na tabela Log
//        for (int i = 0; i < discos.size(); i++) {
//            Volume volume = volumes.get(i);
//            Double discoEmUso = (volume.getTotal() - volume.getDisponivel()) / Math.pow(10, 9);
//
//            //INSERT DO DISCO AZURE
//            conexaoAzure.update("INSERT INTO Log (momentoCaptura, emUso, fkComponente, fkNivelAlerta, fkMaquina, fkGestor) VALUES (?, ?, ?, ?, ?, ?);",
//                    LocalDateTime.now(), discoEmUso, fkComponente, fkNivelAlerta, fkMaquina, fkGestor);
//            //INSERT DO DISCO DOCKER
//            conexaoMysql.update("INSERT INTO Log (momentoCaptura, emUso, fkComponente, fkNivelAlerta, fkMaquina, fkGestor) VALUES (?, ?, ?, ?, ?, ?);",
//                    LocalDateTime.now(), discoEmUso, fkComponente, fkNivelAlerta, fkMaquina, fkGestor);
//        }
//
//        //Inserindo o emUso da CPU na tabela Log
//        Double cpuEmUso = cpu.getUso();
//        //INSERT DO DISCO AZURE
//        conexaoAzure.update("INSERT INTO Log (momentoCaptura, emUso, fkComponente, fkNivelAlerta, fkMaquina, fkGestor) VALUES (?, ?, ?, ?, ?, ?);",
//                LocalDateTime.now(), cpuEmUso, fkComponente, fkNivelAlerta, fkMaquina, fkGestor);
//        //INSERT DO DISCO DOCKER
//        conexaoMysql.update("INSERT INTO Log (momentoCaptura, emUso, fkComponente, fkNivelAlerta, fkMaquina, fkGestor) VALUES (?, ?, ?, ?, ?, ?);",
//                LocalDateTime.now(), cpuEmUso, fkComponente, fkNivelAlerta, fkMaquina, fkGestor);
//
//        //Inserindo o emUso da RAM na tabela Log
//        Double ramEmUso = memoria.getEmUso() / Math.pow(10, 9);
//        //INSERT DO DISCO AZURE
//        conexaoAzure.update("INSERT INTO Log (momentoCaptura, emUso, fkComponente, fkNivelAlerta, fkMaquina, fkGestor) VALUES (?, ?, ?, ?, ?, ?);",
//                LocalDateTime.now(), ramEmUso, fkComponente, fkNivelAlerta, fkMaquina, fkGestor);
//        //INSERT DO DISCO DOCKER
//        conexaoMysql.update("INSERT INTO Log (momentoCaptura, emUso, fkComponente, fkNivelAlerta, fkMaquina, fkGestor) VALUES (?, ?, ?, ?, ?, ?);",
//                LocalDateTime.now(), ramEmUso, fkComponente, fkNivelAlerta, fkMaquina, fkGestor);
    }

}
