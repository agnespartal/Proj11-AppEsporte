package com.example.proj11_appesporte.persistence;

import java.sql.SQLException;

public interface ITimeDao {

    public TimeDao open() throws SQLException;
    public void close();
}
