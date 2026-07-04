package br.com.xpto.core.application.usecases;

import br.com.xpto.core.application.dto.ChatMessageDTO;
import br.com.xpto.core.domain.entities.ChatMessage;
import br.com.xpto.core.domain.repositories.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatUseCase {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageDTO sendMessage(String rentalId, String senderId, String content) {
        ChatMessage message = ChatMessage.create(rentalId, senderId, content);
        ChatMessage savedMessage = chatMessageRepository.save(message);
        return ChatMessageDTO.fromDomain(savedMessage);
    }

    public List<ChatMessageDTO> getChatHistory(String rentalId) {
        return chatMessageRepository.findByRentalIdOrderByTimestampAsc(rentalId).stream()
                .map(ChatMessageDTO::fromDomain)
                .collect(Collectors.toList());
    }
}
