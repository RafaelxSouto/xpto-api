package br.com.xpto.infrastructure.persistence.mongo.entities;

import br.com.xpto.core.domain.entities.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_messages")
public class ChatMessageDocument {

    @Id
    private String id;
    private String rentalId;
    private String senderId;
    private String content;
    private LocalDateTime timestamp;

    public static ChatMessageDocument fromDomain(ChatMessage message) {
        return ChatMessageDocument.builder()
                .id(message.getId())
                .rentalId(message.getRentalId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();
    }

    public ChatMessage toDomain() {
        return new ChatMessage(
                this.id,
                this.rentalId,
                this.senderId,
                this.content,
                this.timestamp
        );
    }
}
