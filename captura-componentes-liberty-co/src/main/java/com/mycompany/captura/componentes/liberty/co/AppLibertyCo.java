/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.captura.componentes.liberty.co;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.group.janelas.JanelaGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import java.util.ArrayList;
import java.util.List;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

/**
 *
 * @author mari
 */
public class AppLibertyCo {

    public static void main(String[] args) {
        Looca looca = new Looca();
        Auxiliar aux = new Auxiliar();

        String separador = aux.separador();
        
        //PROCESSADOR
        //Acessando a classe Processador
        Processador cpu = looca.getProcessador();

        //Puxando o nome da CPU
        String nomeCpu = cpu.getNome();
        //Puxando a informação da Frequência da CPU
        //Convertendo a saída do valor para se assemelhar com o que estamos acostumados (Ex: 2400000000 GHz para 2.4 GHz)
        Double frequenciaCpu = cpu.getFrequencia() / Math.pow(10, 9);

        //Puxar a porcentagem em uso da CPU
        Double emUsoCpu = cpu.getUso();

        String fraseCpu = String.format("PROCESSADOR"
                + "\nNome: %s "
                + "\nFrequência: %.2f GHz"
                + "\nPorcentagem de Uso: %.2f",
                nomeCpu, frequenciaCpu, emUsoCpu);

        //MEMÓRIA RAM
        //Acessando a classe Memoria
        Memoria memoria = looca.getMemoria();

        //Puxando a informação total da Memória RAM
        //Convertendo a saída para facilitar
        Double totalRam = memoria.getTotal() / Math.pow(10, 9);

        Double emUsoRam = memoria.getEmUso() / Math.pow(10, 9);

        String fraseRam = String.format("MEMÓRIA RAM"
                + "\nTotal de RAM: %.2f GB"
                + "\nEm uso: %.2f GB",
                totalRam, emUsoRam);

        //DISCO RÍGIDO
        //Criação do gerenciador
        DiscoGrupo grupoDeDiscos = looca.getGrupoDeDiscos();

        //Obtendo lista de discos a partir do getter
        List<Disco> discos = grupoDeDiscos.getDiscos();
//        List<Volume> espacoLivreHD = grupoDeDiscos.get;
        //Obtendo lista de volumes dos discos
        List<Volume> volumes = grupoDeDiscos.getVolumes();
        List<String> hdInfo = new ArrayList();
        
        for (int i = 0; i < discos.size(); i++) {
            // Armazenando o disco da iteração numa variável para ficar mais fácil de manipular os métodos do objeto
            Disco disco = discos.get(i);
            Double tamanhoDisco = disco.getTamanho() / Math.pow(10, 9);
            Volume volume = volumes.get(i);
            Double emUso = (volume.getTotal() - volume.getDisponivel())/ Math.pow(10, 9);
            hdInfo.add(String.format("Disco Rígido %d" +
                                           "\nModelo: %s" +
                                           "\nTamanho total: %.2fGB" +
                                           "\nDisco em uso: %.2fGB", i + 1, disco.getModelo(), tamanhoDisco, emUso));
        }

      

        //JANELAS OU PROCESSOS
        JanelaGrupo grupoDeJanelas = looca.getGrupoDeJanelas();
        
        List<Janela> janelas = grupoDeJanelas.getJanelas();
        List<String> fraseJanela = new ArrayList();
        
        Integer cont = 1;
        for (Janela janela : janelas) {
            if (janela.isVisivel() == true) {
                fraseJanela.add((String.format("\n\nJanelas %d"
                        + "\nPID: %d"
                        + "\nId da Janela: %d"
                        + "\nTítulo: %s"
                        , cont, janela.getPid(), janela.getJanelaId(), janela.getTitulo())));
                cont++;
            }
        }
        System.out.println(fraseCpu);
        System.out.println(separador);
        System.out.println(fraseRam);
        System.out.println(separador);
        System.out.println(hdInfo);
        System.out.println(fraseJanela);
    }
}
