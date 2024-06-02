package com.example.proj11_appesporte.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proj11_appesporte.model.Jogador;
import com.example.proj11_appesporte.model.Time;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JogadorDao implements IJogadorDao, ICRUDDao<Jogador>{
    private final Context context;
    private GenericDAO gd;
    private SQLiteDatabase db;

    public JogadorDao(Context context) {
        this.context = context;
    }


    @Override
    public void insert(Jogador jogador) throws SQLException {
        ContentValues contentValues = getContentValues(jogador);
        db.insert("jogador", null, contentValues);
    }

    @Override
    public int update(Jogador jogador) throws SQLException {
        ContentValues contentValues = getContentValues(jogador);
        int ret = db.update("jogador", contentValues, "id  = " + jogador.getId(), null);
        return ret;
    }

    @Override
    public void delete(Jogador jogador) throws SQLException {
        db.delete("jogador", "id  = " + jogador.getId(), null);
    }

    @SuppressLint("Range")
    @Override
    public Jogador findOne(Jogador jogador) throws SQLException {
        String sql = "Select id, nome, dtNasc, altura, peso, codigoTime, b.nome as nomeTime, b.cidade as cidadeTime from jogador a INNER JOIN time b ON a.codigoTime = b.codigo where id = " + jogador.getId();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()){
            Time time = new Time();
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigoTime")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nomeTime")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            jogador.setId(cursor.getInt(cursor.getColumnIndex("id")));
            jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));
            jogador.setDtNasc(LocalDate.parse(cursor.getString(cursor.getColumnIndex("dtNasc"))));
            jogador.setTime(time);
        }
        cursor.close();
        return jogador;
    }

    @SuppressLint("Range")
    @Override
    public List<Jogador> findAll() throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "Select id, nome, dtNasc, altura, peso, codigoTime, b.nome as nomeTime, b.cidade as cidadeTime from jogador a INNER JOIN time b ON a.codigoTime = b.codigo";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()){
            Time time = new Time();
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigoTime")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nomeTime")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            Jogador jogador = new Jogador();
            jogador.setId(cursor.getInt(cursor.getColumnIndex("id")));
            jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));
            jogador.setDtNasc(LocalDate.parse(cursor.getString(cursor.getColumnIndex("dtNasc"))));
            jogador.setTime(time);

            jogadores.add(jogador);
            cursor.moveToNext();
        }
        cursor.close();
        return jogadores;
    }

    private static ContentValues getContentValues(Jogador jogador) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", jogador.getId());
        contentValues.put("nome", jogador.getNome());
        contentValues.put("altura", jogador.getAltura());
        contentValues.put("peso", jogador.getPeso());
        contentValues.put("dtNasc", jogador.getDtNasc().toString());
        contentValues.put("codigoTime", jogador.getTime().getCodigo());
        return contentValues;
    }

    @Override
    public JogadorDao open() throws SQLException {
        gd = new GenericDAO(context);
        db = gd.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gd.close();
    }
}
