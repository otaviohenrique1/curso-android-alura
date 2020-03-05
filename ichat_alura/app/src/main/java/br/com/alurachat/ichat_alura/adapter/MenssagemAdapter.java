package br.com.alurachat.ichat_alura.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import br.com.alurachat.ichat_alura.R;
import br.com.alurachat.ichat_alura.modelo.Mensagem;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ROSANGELA on 13/02/2018.
 */

public class MenssagemAdapter extends BaseAdapter {
    private List<Mensagem> mensagems;
    private Activity activity;
    private int idDoCliente;

    @BindView(R.id.iv_avatar_mensagem)
    ImageView avatar;

    @BindView(R.id.lv_mensagens)
    TextView texto;

    @Inject
    Picasso picasso;

    public MenssagemAdapter(int idDoCliente, List<Mensagem> mensagems, Activity activity) {
        this.idDoCliente = idDoCliente;
        this.mensagems = mensagems;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return mensagems.size();
    }

    @Override
    public Mensagem getItem(int i) {
        return mensagems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View linha = activity.getLayoutInflater().inflate(R.layout.menssagem, viewGroup, false);

        /*Inicia o ButterKnife*/
        ButterKnife.bind(this, linha);

        Mensagem mensagem = getItem(i);

        int idDaMensagem = mensagem.getId();

        /*API que carrega imagens direto de um site*/
        picasso.with(activity).load("https://api.adorable.io/avatars/285/" + idDaMensagem + ".png").into(avatar);

        /*ImageView avatar = (ImageView) linha.findViewById(R.id.iv_avatar_mensagem);
        TextView texto = (TextView) linha.findViewById(R.id.tv_texto);*/

        if (idDoCliente != mensagem.getId()){
            linha.setBackgroundColor(Color.CYAN);
        }
        texto.setText(mensagem.getTexto());
        return linha;
    }
}
