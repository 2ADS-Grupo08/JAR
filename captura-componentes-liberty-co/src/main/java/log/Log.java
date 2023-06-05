/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package log;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mari
 */
public abstract class Log {
    protected String nomeDocumento;
    protected String nomeLogado;
    protected List<String> status;
    protected String descricao;

    public Log(String nomeDocumento, String nomeLogado, String descricao) {
        this.nomeDocumento = nomeDocumento;
        this.nomeLogado = nomeLogado;
        this.status = new ArrayList<>();
        this.descricao = descricao;
    }
    
    
    
    public abstract void criarLog();


    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }
    
    public String getnomeLogado() {
        return nomeLogado;
    }

    public void setnomeLogado(String nomeLogado) {
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
