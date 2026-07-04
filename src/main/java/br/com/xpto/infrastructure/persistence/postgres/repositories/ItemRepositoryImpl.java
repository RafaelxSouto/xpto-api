package br.com.xpto.infrastructure.persistence.postgres.repositories;

import br.com.xpto.core.domain.entities.Item;
import br.com.xpto.core.domain.repositories.ItemRepository;
import br.com.xpto.infrastructure.persistence.postgres.entities.ItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final SpringDataItemJpaRepository jpaRepository;

    @Override
    public Item save(Item item) {
        ItemEntity entity = ItemEntity.fromDomain(item);
        ItemEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Item> findById(String id) {
        return jpaRepository.findById(id).map(ItemEntity::toDomain);
    }

    @Override
    public List<Item> findAllAvailable() {
        return jpaRepository.findByAvailableTrue().stream()
                .map(ItemEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> findByOwnerId(String ownerId) {
        return jpaRepository.findByOwnerId(ownerId).stream()
                .map(ItemEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        jpaRepository.deleteById(id);
    }
}
