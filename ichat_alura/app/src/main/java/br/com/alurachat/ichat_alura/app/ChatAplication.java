package br.com.alurachat.ichat_alura.app;

import android.app.Application;

import br.com.alurachat.ichat_alura.component.ChatComponent;
import br.com.alurachat.ichat_alura.component.DaggerChatComponent;
import br.com.alurachat.ichat_alura.module.ChatModule;

/**
 * Created by ROSANGELA on 15/02/2018.
 */

public class ChatAplication extends Application{
    private ChatComponent component;

    @Override
    public void onCreate() {
        component = DaggerChatComponent.builder()
                .chatModule(new ChatModule(this))
                .build();
    }

    public ChatComponent getComponent() {
        return component;
    }
}
