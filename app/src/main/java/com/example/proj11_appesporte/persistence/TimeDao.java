package com.example.proj11_appesporte.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proj11_appesporte.model.Time;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimeDao implements ITimeDao, ICRUDDao<Time>{
    
    private final Context context;
    private GenericDAO gd;
    private SQLiteDatabase db;
    
    public TimeDao(Context context){
        this.context = context;
    }
    
    @Override
    public void insert(Time time) throws SQLException {
        ContentValues contentValues = getContentValues(time);
        db.insert("time", null, contentValues);
    }

    @Override
    public int update(Time time) throws SQLException {
        ContentValues contentValues = getContentValues(time);
        int ret = db.update("time", contentValues, "codigo = " + time.getCodigo(), null);
        return ret;
    }

    @Override
    public void delete(Time time) throws SQLException {
        db.delete("time", "codigo = " + time.getCodigo(), null);
    }

    @SuppressLint("Range")
    @Override
    public Time findOne(Time time) throws SQLException {
        String sql = "Select codigo, nome, cidade from time where codigo = " + time.getCodigo();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()){
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
        }
        cursor.close();
        return time;
    }

    @SuppressLint("Range")
    @Override
    public List<Time> findAll() throws SQLException {
        List<Time> times = new ArrayList<>();
        String sql = "Select codigo, nome, cidade from time";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Time time = new Time();
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            times.add(time);
            cursor.moveToNext();
        }
        cursor.close();
        return times;
    }

    private static ContentValues getContentValues(Time time) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("codigo", time.getCodigo());
        contentValues.put("nome", time.getNome());
        contentValues.put("cidade", time.getCidade());
        return contentValues;
    }

    @Override
    public TimeDao open() throws SQLException {
        gd = new GenericDAO(context);
        db = gd.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gd.close();
    }
}
