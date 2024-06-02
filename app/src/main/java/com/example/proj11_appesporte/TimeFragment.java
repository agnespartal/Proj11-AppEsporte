package com.example.proj11_appesporte;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proj11_appesporte.controller.TimeController;
import com.example.proj11_appesporte.model.Time;
import com.example.proj11_appesporte.persistence.TimeDao;

import java.sql.SQLException;
import java.util.List;


public class TimeFragment extends Fragment {

    private View view;

    private EditText etCodigoTime;
    private EditText etNomeTime;
    private EditText etCidadeTime;

    private Button btnInserirTime, btnBuscarTime, btnListarTime, btnEditarTime, btnExcluirTime;
    private TextView tvListaTime;

    private TimeController control;


    public TimeFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_time, container, false);

        etCodigoTime = view.findViewById(R.id.etCodigoTime);
        etNomeTime = view.findViewById(R.id.etNomeTime);
        etCidadeTime = view.findViewById(R.id.etCidadeTime);
        btnBuscarTime = view.findViewById(R.id.btnBuscarTime);
        btnExcluirTime = view.findViewById(R.id.btnExcluirTime);
        btnEditarTime = view.findViewById(R.id.btnEditarTime);
        btnInserirTime = view.findViewById(R.id.btnInserirTime);
        btnListarTime = view.findViewById(R.id.btnListarTime);
        tvListaTime = view.findViewById(R.id.tvListaTime);
        tvListaTime.setMovementMethod(new ScrollingMovementMethod());

        control = new TimeController(new TimeDao(view.getContext()));

        btnInserirTime.setOnClickListener(op -> inserir());
        btnEditarTime.setOnClickListener(op -> editar());
        btnExcluirTime.setOnClickListener(op -> excluir());
        btnBuscarTime.setOnClickListener(op -> buscar());
        btnListarTime.setOnClickListener(op -> listar());

        return view;
    }

    private void inserir() {
        Time time = TimeDados();
        try {
            control.inserir(time);
            Toast.makeText(view.getContext(),"Time inserido com sucesso!", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
        LimpaCampos();
    }

    private void editar() {
        Time time = TimeDados();
        try {
            control.editar(time);
            Toast.makeText(view.getContext(),"Time atualizado com sucesso!", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
        LimpaCampos();
    }

    private void excluir() {
        Time time = TimeDados();
        try {
            control.deletar(time);
            Toast.makeText(view.getContext(),"Time excluído com sucesso!", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
        LimpaCampos();
    }

    private void buscar() {
        Time time = TimeDados();
        try {
            time = control.buscar(time);
            if (time.getNome() != null){
                PreencherCampos(time);
            }
            else {
                Toast.makeText(view.getContext(),"Time não encontrado!", Toast.LENGTH_LONG).show();
                LimpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void listar() {
        try {
            List<Time> times = control.listar();
            StringBuffer sb = new StringBuffer();
            for (Time t : times){
                sb.append(t.toString() + "\n");
            }
            tvListaTime.setText(sb.toString());

        } catch (SQLException e) {
            Toast.makeText(view.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Time TimeDados(){
        Time t = new Time();
        t.setCodigo(Integer.parseInt(etCodigoTime.getText().toString()));
        t.setNome(etNomeTime.getText().toString());
        t.setCidade(etCidadeTime.getText().toString());

        return t;
    }

    private void PreencherCampos(Time time){
        etCodigoTime.setText(String.valueOf(time.getCodigo()));
        etNomeTime.setText(String.valueOf(time.getNome()));
        etCidadeTime.setText(String.valueOf(time.getCidade()));
    }

    private void LimpaCampos(){
        etCodigoTime.setText("");
        etNomeTime.setText("");
        etCidadeTime.setText("");
    }

}