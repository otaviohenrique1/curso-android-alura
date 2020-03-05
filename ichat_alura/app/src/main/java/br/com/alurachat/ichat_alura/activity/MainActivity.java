package br.com.alurachat.ichat_alura.activity;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import br.com.alurachat.ichat_alura.app.ChatAplication;
import br.com.alurachat.ichat_alura.R;
import br.com.alurachat.ichat_alura.adapter.MenssagemAdapter;
import br.com.alurachat.ichat_alura.component.ChatComponent;
import br.com.alurachat.ichat_alura.event.MensagemEvent;
import br.com.alurachat.ichat_alura.modelo.Mensagem;
import br.com.alurachat.ichat_alura.callback.EnviarMensagemCallback;
import br.com.alurachat.ichat_alura.callback.OuvirMensagensCallback;
import br.com.alurachat.ichat_alura.service.ChatService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private int idDoCliente = new Random().nextInt();
    /*private int idDoCliente = 1;*/

    @BindView(R.id.et_texto)
    EditText editText;

    @BindView(R.id.btn_enviar)
    Button button;

    @BindView(R.id.lv_mensagens)
    ListView listaDeMensagem;

    @BindView(R.id.iv_avatar_usuario)
    ImageView avatar;

    private List<Mensagem> mensagens;

    @Inject
    ChatService chatService;

    @Inject
    Picasso picasso;

    @Inject
    EventBus eventBus;

    private ChatComponent component;

    /*private BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Mensagem mensagem = (Mensagem) intent.getSerializableExtra("mensagem");
            colocaNaLista(mensagem);
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        /*API que carrega imagens direto de um site*/
        picasso.with(this).load("https://api.adorable.io/avatars/285/" + idDoCliente + ".png").into(avatar);

        ChatAplication app = (ChatAplication) getApplication();
        component = app.getComponent();
        component.inject(this);

        if (savedInstanceState != null) {
            mensagens = (List<Mensagem>) savedInstanceState.getSerializable("mensagens");
        } else {
            mensagens = new ArrayList<>();
        }

        mensagens = new ArrayList<>();

        MenssagemAdapter adapter = new MenssagemAdapter(idDoCliente, mensagens, this);

        listaDeMensagem.setAdapter(adapter);

        Call<Mensagem> call= chatService.ouvirMensagens();
        call.enqueue(new OuvirMensagensCallback(eventBus, this));

        /*Registra EventBus*/
        eventBus.register(this);


        /*LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(reciver, new IntentFilter("nova_mensagem"));*/

        /*listaDeMensagem = (ListView) findViewById(R.id.lv_mensagens);
        editText = (EditText) findViewById(R.id.et_texto);
        button = (Button) findViewById(R.id.btn_enviar);*/

        /*ChatModule app = (ChatModule) getApplication();
        this.chatService = app.getChatService();*/

        /*String ipDoServidor = "192.168.0.208:8080";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipDoServidor + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chatService = retrofit.create(ChatService.class);*/
        
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatService.enviar(new Mensagem(idDoCliente, editText.getText().toString())).enqueue(new EnviarMensagemCallback());
            }
        });*/
    }

    /*Salva lista quando a activity for destroida quando a tela for virada*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("mensagens", (ArrayList<Mensagem>) mensagens);
    }

    /*Acao do botao enviar*/
    @OnClick(R.id.btn_enviar)
    public void  enviarMensagem() {
        chatService.enviar(new Mensagem(idDoCliente, editText.getText().toString())).enqueue(new EnviarMensagemCallback());

        editText.getText().clear();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Subscribe
    public void colocaNaLista(MensagemEvent mensagemEvent) {
        mensagens.add(mensagemEvent.mensagem);
        MenssagemAdapter adapter = new MenssagemAdapter(idDoCliente, mensagens, this);

        listaDeMensagem.setAdapter(adapter);
        ouvirMensagem();
    }

    @Subscribe
    public void ouvirMensagem() {
        Call<Mensagem> call= chatService.ouvirMensagens();
        call.enqueue(new OuvirMensagensCallback(eventBus, this));
    }

    @Override
    protected void onStop() {
        super.onStop();

        eventBus.unregister(this);
    }
}
