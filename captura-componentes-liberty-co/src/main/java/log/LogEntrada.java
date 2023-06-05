/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author mari
 */
public class LogEntrada extends Log{
    public LogEntrada(String nomeDocumento, String nomeLogado, String descricao) {
        super(nomeDocumento, nomeLogado, descricao);
    }    

    @Override
    public void criarLog() {
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatada = dataAtual.format(formato);
        

        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataHoraFormatada = dataHoraAtual.format(formato2);
        
        
        
        setNomeDocumento(dataFormatada);
        
        String titulo = String.format("%s_sistema.txt", dataFormatada);
        File arquivo = new File(titulo);
        
        
        if(!arquivo.exists()){
            try {
                arquivo.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(LogEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        List<String> lista = new ArrayList<>();
                    
        lista.add(dataHoraFormatada + " " + getnomeLogado() + " Entrou no sistema!" );
          
          
        try {
            Files.write(Paths.get(arquivo.getPath()), lista, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            Logger.getLogger(LogEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }

    public String getNomeLogado() {
        return nomeLogado;
    }

    public void setNomeLogado(String nomeLogado) {
        this.nomeLogado = nomeLogado;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
