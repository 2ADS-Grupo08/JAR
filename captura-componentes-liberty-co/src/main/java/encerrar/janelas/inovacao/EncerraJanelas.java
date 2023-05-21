/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package encerrar.janelas.inovacao;

import com.github.britooo.looca.api.core.Looca;
import java.io.IOException;

/**
 *
 * @author mari
 */
public class EncerraJanelas {

    static Looca looca = new Looca();

    public static void verificarFabricante(String pid) throws IOException {
        String fabricante = looca.getSistema().getFabricante().toUpperCase();

        System.out.println("Encontrei o Fabricante, e é: " + fabricante);
        
        if (fabricante.contains("LINUX")) {
            terminalLinux(pid);
        } else if (fabricante.contains("WINDOWS")) {
//            terminalWindows();
        }
        
    }

    public static void terminalLinux(String pid) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("kill -9 " + pid);
        
        System.out.println("entrei no método terminalLinux");
        
        try {
            int codigoDeSaida = process.waitFor();
            System.out.println("Código de saída " + codigoDeSaida);
        } catch (InterruptedException e){
        }
    }

//    public static void terminalWindows() {
//        try {
//            // Nome da tarefa/processo a ser encerrado
//            String taskName = "nome_da_tarefa.exe";
//
//            // Comando para encerrar a tarefa
//            String command = "taskkill /F /IM " + taskName;
//
//            // Executa o comando no prompt de comando do Windows
//            Process process = Runtime.getRuntime().exec(command);
//
//            // Aguarda o término do processo
//            int exitCode = process.waitFor();
//            System.out.println("Tarefa encerrada. Código de saída: " + exitCode);
//
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
