package br.com.xpto.core.domain.entities;

import java.time.LocalDateTime;

public class ChatMessage {
    private String id;
    private String rentalId;
    private String senderId;
    private String content;
    private LocalDateTime timestamp;

    public ChatMessage(String id, String rentalId, String senderId, String content, LocalDateTime timestamp) {
        this.id = id;
        this.rentalId = rentalId;
        this.senderId = senderId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public static ChatMessage create(String rentalId, String senderId, String content) {
        return new ChatMessage(null, rentalId, senderId, content, LocalDateTime.now());
    }

    // Getters
    public String getId() { return id; }
    public String getRentalId() { return rentalId; }
    public String getSenderId() { return senderId; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
