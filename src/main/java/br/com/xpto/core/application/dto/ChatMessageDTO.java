package br.com.xpto.core.application.dto;

import br.com.xpto.core.domain.entities.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private String id;
    private String rentalId;
    private String senderId;
    private String content;
    private LocalDateTime timestamp;

    public static ChatMessageDTO fromDomain(ChatMessage message) {
        return ChatMessageDTO.builder()
                .id(message.getId())
                .rentalId(message.getRentalId())
                .senderId(message.getSenderId())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();
    }
}
