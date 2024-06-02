package com.example.proj11_appesporte;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proj11_appesporte.controller.JogadorController;
import com.example.proj11_appesporte.controller.TimeController;
import com.example.proj11_appesporte.model.Jogador;
import com.example.proj11_appesporte.model.Time;
import com.example.proj11_appesporte.persistence.JogadorDao;
import com.example.proj11_appesporte.persistence.TimeDao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public class JogadorFragment extends Fragment {

    private View view;

    private EditText etCodigoJogador;
    private EditText etNomeJogador;
    private EditText etAlturaJogador;
    private EditText etPesoJogador;
    private EditText etdtNascJogador;

    private Button btnInserirJogador, btnBuscarJogador, btnListarJogador, btnEditarJogador, btnExcluirJogador;
    private TextView tvListaJogador;
    private Spinner spTime;

    private JogadorController jControl;

    private TimeController tControl;

    private List<Time> times;


    public JogadorFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_jogador, container, false);

        etCodigoJogador = view.findViewById(R.id.etCodigoJogador);
        etNomeJogador = view.findViewById(R.id.etNomeJogador);
        etAlturaJogador = view.findViewById(R.id.etAlturaJogador);
        etPesoJogador = view.findViewById(R.id.etPesoJogador);
        etdtNascJogador = view.findViewById(R.id.etdtNascJogador);
        btnBuscarJogador = view.findViewById(R.id.btnBuscaJogador);
        btnEditarJogador = view.findViewById(R.id.btnEditarJogador);
        btnExcluirJogador = view.findViewById(R.id.btnExcluirJogador);
        btnListarJogador = view.findViewById(R.id.btnListarJogador);
        btnInserirJogador = view.findViewById(R.id.btnInserirJogador);
        spTime = view.findViewById(R.id.spTime);
        tvListaJogador = view.findViewById(R.id.tvListaJogador);
        tvListaJogador.setMovementMethod(new ScrollingMovementMethod());

        jControl = new JogadorController(new JogadorDao(view.getContext()));
        tControl = new TimeController(new TimeDao(view.getContext()));
        populaSpinner();

        btnInserirJogador.setOnClickListener(op -> inserir());
        btnEditarJogador.setOnClickListener(op -> editar());
        btnExcluirJogador.setOnClickListener(op -> excluir());
        btnBuscarJogador.setOnClickListener(op -> buscar());
        btnListarJogador.setOnClickListener(op -> listar());

        return view;
    }

    private void inserir() {
        int spPosicao = spTime.getSelectedItemPosition();
        if(spPosicao > 0){
            Jogador j = JogadorDados();
            try {
                jControl.inserir(j);
                Toast.makeText(view.getContext(),"Jogador inserido com sucesso!", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
            }
            LimpaCampos();
        }
        else{
            Toast.makeText(view.getContext(),"Selecione um Time", Toast.LENGTH_LONG).show();
        }
    }

    private void editar() {
        int spPosicao = spTime.getSelectedItemPosition();
        if(spPosicao > 0){
            Jogador j = JogadorDados();
            try {
                jControl.editar(j);
                Toast.makeText(view.getContext(),"Jogador atualizado com sucesso!", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
            }
            LimpaCampos();
        }
        else{
            Toast.makeText(view.getContext(),"Selecione um Time", Toast.LENGTH_LONG).show();
        }
    }

    private void excluir() {
        Jogador j = JogadorDados();
        try {
            jControl.deletar(j);
            Toast.makeText(view.getContext(),"Jogador excluído com sucesso!", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
        LimpaCampos();
    }

    private void buscar() {
        Jogador j = JogadorDados();
        try {
            times = tControl.listar();
            j = jControl.buscar(j);
            if (j.getNome() != null){
                PreencherCampos(j);
            }
            else{
                Toast.makeText(view.getContext(),"Jogador não encontrado", Toast.LENGTH_LONG).show();
                LimpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void listar() {
        try {
            List<Jogador> jogadores = jControl.listar();
            StringBuffer sb = new StringBuffer();
            for (Jogador j : jogadores){
                sb.append(j.toString() + "\n");
            }
            tvListaJogador.setText(sb.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Jogador JogadorDados(){
        Jogador j = new Jogador();
        j.setId(Integer.parseInt(etCodigoJogador.getText().toString()));
        j.setNome(etNomeJogador.getText().toString());
        j.setDtNasc(LocalDate.parse(etdtNascJogador.getText().toString()));
        j.setAltura(Float.parseFloat(etAlturaJogador.getText().toString()));
        j.setPeso(Float.parseFloat(etPesoJogador.getText().toString()));
        j.setTime((Time) spTime.getSelectedItem());

        return j;
    }

    private void PreencherCampos(Jogador j){
        etCodigoJogador.setText(String.valueOf(j.getId()));
        etNomeJogador.setText(String.valueOf(j.getNome()));
        etdtNascJogador.setText(String.valueOf(j.getDtNasc()));
        etAlturaJogador.setText(String.valueOf(j.getAltura()));
        etPesoJogador.setText(String.valueOf(j.getPeso()));

        int cont = 1;
        for (Time t : times){
            if (t.getCodigo() == j.getTime().getCodigo()){
                spTime.setSelection(cont);
            }
            else{
                cont++;
            }
        }
        if (cont > times.size()){
            spTime.setSelection(0);
        }
    }

    private void LimpaCampos(){
        etNomeJogador.setText("");
        etCodigoJogador.setText("");
        etdtNascJogador.setText("");
        etPesoJogador.setText("");
        etAlturaJogador.setText("");
        spTime.setSelection(0);
    }


    private void populaSpinner() {
        Time tInicial = new Time();
        tInicial.setCodigo(0);
        tInicial.setNome("Selecione um Time");
        tInicial.setCidade("");

        try {
            times = tControl.listar();
            times.add(0, tInicial);

            ArrayAdapter ad = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, times);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTime.setAdapter(ad);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}