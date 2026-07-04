package br.com.xpto.core.application.usecases;

import br.com.xpto.core.application.dto.ItemResponse;
import br.com.xpto.core.domain.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogUseCase {

    private final ItemRepository itemRepository;

    @Cacheable(value = "availableItems", key = "'all'")
    public List<ItemResponse> getAvailableItems() {
        return itemRepository.findAllAvailable()
                .stream()
                .map(ItemResponse::fromDomain)
                .collect(Collectors.toList());
    }
}
