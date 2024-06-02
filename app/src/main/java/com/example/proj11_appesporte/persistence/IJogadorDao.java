package com.example.proj11_appesporte.persistence;

import java.sql.SQLException;

public interface IJogadorDao {

    public JogadorDao open() throws SQLException;
    public void close();
}
