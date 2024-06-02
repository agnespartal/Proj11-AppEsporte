package com.example.proj11_appesporte.controller;

import com.example.proj11_appesporte.model.Jogador;
import com.example.proj11_appesporte.persistence.JogadorDao;

import java.sql.SQLException;
import java.util.List;

public class JogadorController implements IController<Jogador> {

    private final JogadorDao jd;

    public JogadorController(JogadorDao jd) {
        this.jd = jd;
    }

    @Override
    public void inserir(Jogador jogador) throws SQLException {
        if(jd.open() == null){
            jd.open();
        }
        jd.close();
        jd.insert(jogador);
    }

    @Override
    public void editar(Jogador jogador) throws SQLException {
        if(jd.open() == null){
            jd.open();
        }
        jd.update(jogador);
        jd.close();
    }

    @Override
    public void deletar(Jogador jogador) throws SQLException {
        if(jd.open() == null){
            jd.open();
        }
        jd.delete(jogador);
        jd.close();
    }

    @Override
    public Jogador buscar(Jogador jogador) throws SQLException {
        if(jd.open() == null){
            jd.open();
        }
        return jd.findOne(jogador);
    }

    @Override
    public List<Jogador> listar() throws SQLException {
        if(jd.open() == null){
            jd.open();
        }
        return jd.findAll();
    }
}
