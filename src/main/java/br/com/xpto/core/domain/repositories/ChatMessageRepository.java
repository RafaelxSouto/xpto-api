package br.com.xpto.core.domain.repositories;

import br.com.xpto.core.domain.entities.ChatMessage;
import java.util.List;

public interface ChatMessageRepository {
    ChatMessage save(ChatMessage message);
    List<ChatMessage> findByRentalIdOrderByTimestampAsc(String rentalId);
}
