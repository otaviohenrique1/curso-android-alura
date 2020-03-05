package br.com.alurachat.ichat_alura.component;

import br.com.alurachat.ichat_alura.activity.MainActivity;
import br.com.alurachat.ichat_alura.adapter.MenssagemAdapter;
import br.com.alurachat.ichat_alura.module.ChatModule;
import dagger.Component;

/**
 * Created by ROSANGELA on 15/02/2018.
 */

@Component(modules=ChatModule.class)
public interface ChatComponent {
    void inject(MainActivity activity);
    void inject(MenssagemAdapter adapter);
}
