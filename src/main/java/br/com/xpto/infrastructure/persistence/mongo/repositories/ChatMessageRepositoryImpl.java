package br.com.xpto.infrastructure.persistence.mongo.repositories;

import br.com.xpto.core.domain.entities.ChatMessage;
import br.com.xpto.core.domain.repositories.ChatMessageRepository;
import br.com.xpto.infrastructure.persistence.mongo.entities.ChatMessageDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final SpringDataChatMessageMongoRepository repository;

    @Override
    public ChatMessage save(ChatMessage message) {
        ChatMessageDocument doc = ChatMessageDocument.fromDomain(message);
        ChatMessageDocument savedDoc = repository.save(doc);
        return savedDoc.toDomain();
    }

    @Override
    public List<ChatMessage> findByRentalIdOrderByTimestampAsc(String rentalId) {
        return repository.findByRentalIdOrderByTimestampAsc(rentalId).stream()
                .map(ChatMessageDocument::toDomain)
                .collect(Collectors.toList());
    }
}
