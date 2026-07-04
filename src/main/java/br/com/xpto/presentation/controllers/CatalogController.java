package br.com.xpto.presentation.controllers;

import br.com.xpto.core.application.dto.ItemResponse;
import br.com.xpto.core.application.usecases.CatalogUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog/public")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogUseCase catalogUseCase;

    @GetMapping("/items")
    public ResponseEntity<List<ItemResponse>> getAvailableItems() {
        return ResponseEntity.ok(catalogUseCase.getAvailableItems());
    }
}
