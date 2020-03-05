package br.com.alurachat.ichat_alura.module;

import android.app.Application;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import br.com.alurachat.ichat_alura.service.ChatService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ROSANGELA on 15/02/2018.
 */

@Module
public class ChatModule{
    private Application app;

    public ChatModule(Application app) {
        this.app = app;
    }

    /*Metodo e um provedor de dependencias*/
    @Provides
    public ChatService getChatService() {
        String ipDoServidor = "192.168.0.208:8080";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ipDoServidor + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatService chatService = retrofit.create(ChatService.class);
        return chatService;
    }

    @Provides
    public EventBus getEventBus() {
        return EventBus.builder().build();
    }

    @Provides
    public Picasso getPicasso() {
        Picasso picasso = new Picasso.Builder(app).build();
        return picasso;
    }
}
