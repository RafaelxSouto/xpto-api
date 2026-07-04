package br.com.xpto.presentation.controllers;

import br.com.xpto.core.application.dto.ChatMessageDTO;
import br.com.xpto.core.application.usecases.ChatUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatUseCase chatUseCase;

    @PostMapping("/{rentalId}")
    public ResponseEntity<ChatMessageDTO> sendMessage(
            @PathVariable String rentalId,
            @RequestBody String content,
            Authentication authentication) {
        
        return ResponseEntity.ok(chatUseCase.sendMessage(rentalId, authentication.getName(), content));
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<List<ChatMessageDTO>> getChatHistory(@PathVariable String rentalId) {
        return ResponseEntity.ok(chatUseCase.getChatHistory(rentalId));
    }
}
