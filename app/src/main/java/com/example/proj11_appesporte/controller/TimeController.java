package com.example.proj11_appesporte.controller;

import com.example.proj11_appesporte.model.Time;
import com.example.proj11_appesporte.persistence.TimeDao;

import java.sql.SQLException;
import java.util.List;

public class TimeController implements IController<Time>{

    private final TimeDao td;

    public TimeController(TimeDao td){
        this.td = td;
    }

    @Override
    public void inserir(Time time) throws SQLException {
        if(td.open() == null){
            td.open();
        }
        td.insert(time);
        td.close();
    }

    @Override
    public void editar(Time time) throws SQLException {
        if(td.open() == null){
            td.open();
        }
        td.update(time);
        td.close();
    }

    @Override
    public void deletar(Time time) throws SQLException {
        if(td.open() == null){
            td.open();
        }
        td.delete(time);
        td.close();
    }

    @Override
    public Time buscar(Time time) throws SQLException {
        if(td.open() == null){
            td.open();
        }
        return td.findOne(time);
    }

    @Override
    public List<Time> listar() throws SQLException {
        if(td.open() == null){
            td.open();
        }
        return td.findAll();
    }


}
