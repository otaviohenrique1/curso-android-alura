package br.com.applista.listadecompras.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.applista.listadecompras.R;
import br.com.applista.listadecompras.model.Produto;

public class ListaComprasAdapter extends BaseAdapter{

    private final List<Produto> produtos;
    private final Context context;

    public ListaComprasAdapter(Context context, List<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    /*Retorma o tamanho da lista*/
    @Override
    public int getCount() {
        return produtos.size();
    }

    /*Retorna algum item da lista*/
    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    /*Retorna o id de algum produto da lista*/
    @Override
    public long getItemId(int position) {
        return produtos.get(position).getId();
    }

    /*Retorna a View da lista*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produto produto = produtos.get(position);
        View view = convertView;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (view == null) {
            view = inflater.inflate(R.layout.item_compra, parent, false);
        }

        TextView campoNome = view.findViewById(R.id.item_compras_nome);
        if (campoNome != null) {
            campoNome.setText(produto.getNome());
        }

        TextView campoQuantidade = view.findViewById(R.id.item_compras_quantidade);
        if (campoQuantidade != null) {
            campoQuantidade.setText(produto.getQuantidade().toString());
        }

        return view;
    }
}
