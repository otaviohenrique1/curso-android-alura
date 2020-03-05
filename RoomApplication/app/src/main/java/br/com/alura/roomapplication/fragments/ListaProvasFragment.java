package br.com.alura.roomapplication.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

import br.com.alura.roomapplication.ProvasDelegate;
import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.AluraDatabase;
import br.com.alura.roomapplication.database.GeradorDeBancoDeDados;
import br.com.alura.roomapplication.database.conversor.ConversorDeData;
import br.com.alura.roomapplication.database.daos.ProvaDao;
import br.com.alura.roomapplication.models.Prova;

public class ListaProvasFragment extends Fragment {
    private ProvasDelegate delegate;
    private FloatingActionButton botaoAdd;
    private ListView lista;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (ProvasDelegate) getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.lista_provas_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_lista_prova_calendario) {
            Context contexto = getContext();

            LinearLayout linearLayout = new LinearLayout(contexto);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            final EditText campoInicio = new EditText(contexto);
            campoInicio.setHint("Inicio");
            campoInicio.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);

            final EditText campoFim = new EditText(contexto);
            campoFim.setHint("Fim");
            campoFim.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);

            linearLayout.addView(campoInicio);
            linearLayout.addView(campoFim);

            new AlertDialog.Builder(contexto)
                    .setView(linearLayout)
                    .setMessage("Digite as datas para busca")
                    .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String dataInicioString = campoInicio.getText().toString();
                            String dataFimString = campoFim.getText().toString();

                            Calendar dataInicio = ConversorDeData.converte(dataInicioString);
                            Calendar dataFim = ConversorDeData.converte(dataFimString);

                            GeradorDeBancoDeDados geradorDeBancoDeDados = new GeradorDeBancoDeDados();
                            AluraDatabase aluraDatabase = geradorDeBancoDeDados.gera(getContext());
                            ProvaDao provaDao = aluraDatabase.getProvaDao();
                            List<Prova> provas = provaDao.buscaPeloPeriodo(dataInicio, dataFim);

                            final ArrayAdapter<Prova> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, provas);
                            lista.setAdapter(adapter);

                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lista, container, false);/*attachToRoot -> nao adiciona na root*/

        configuraComponentesDa(view);
        return view;
    }

    private void configuraComponentesDa(View view) {
        configuraListagem(view);
        configuraFAB(view);
    }

    private void configuraFAB(View view) {
        botaoAdd = view.findViewById(R.id.fragment_lista_fab);
        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.lidaComOClickFAB();
            }
        });
    }

    private void configuraListagem(View view) {
        lista = view.findViewById(R.id.fragment_lista);

        Context contexto = getContext();

        GeradorDeBancoDeDados geradorDeBancoDeDados = new GeradorDeBancoDeDados();
        AluraDatabase database = geradorDeBancoDeDados.gera(contexto);
        final ProvaDao provaDao = database.getProvaDao();
        List<Prova> provas = provaDao.busca();

        final ArrayAdapter<Prova> adapter = new ArrayAdapter<>(contexto, android.R.layout.simple_list_item_1, provas);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Prova prova = (Prova) lista.getItemAtPosition(posicao);
                delegate.lidaComProvaSelecionada(prova);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                final Prova prova = (Prova) lista.getItemAtPosition(posicao);
                String mensagem = "Excluir prova " + prova.getMateria() + " ?";
                Snackbar.make(botaoAdd, mensagem, Snackbar.LENGTH_LONG)
                        .setAction("Sim", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                provaDao.deleta(prova);
                                adapter.remove(prova);
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        delegate.alteraNomeDaActivity("Lista de Provas");
    }
}