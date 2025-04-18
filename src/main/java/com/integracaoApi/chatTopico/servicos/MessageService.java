package com.integracaoApi.chatTopico.servicos;

import com.integracaoApi.chatTopico.MensagensAlegres;
import com.integracaoApi.chatTopico.MensagensReflexivas;
import com.integracaoApi.chatTopico.MensagensTristes;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class MessageService {
    private final SimpMessagingTemplate messagingTemplate;
    private final Random random = new Random();

    private final Map<String, List<String>> mensagens = Map.of(
            "triste", MensagensTristes.MENSAGENS,
            "reflexivo", MensagensReflexivas.MENSAGENS
    );


    public MessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void enviarMensagens() {
        mensagens.forEach((tema, listaMensagens) -> {
            String mensagem = listaMensagens.get(random.nextInt(listaMensagens.size()));
            messagingTemplate.convertAndSend("/topic/" + tema, mensagem);
        });
    }
}
