package br.com.xpto.core.domain.repositories;

import br.com.xpto.core.domain.entities.Item;
import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);
    Optional<Item> findById(String id);
    List<Item> findAllAvailable();
    List<Item> findByOwnerId(String ownerId);
    void deleteById(String id);
}
