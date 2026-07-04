package br.com.xpto.infrastructure.persistence.postgres.entities;

import br.com.xpto.core.domain.entities.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyRate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal depositAmount;

    @ElementCollection
    @CollectionTable(name = "item_images", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    @Column(nullable = false)
    private String categoryId;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static ItemEntity fromDomain(Item item) {
        return ItemEntity.builder()
                .id(item.getId())
                .ownerId(item.getOwnerId())
                .title(item.getTitle())
                .description(item.getDescription())
                .dailyRate(item.getDailyRate())
                .depositAmount(item.getDepositAmount())
                .imageUrls(item.getImageUrls())
                .categoryId(item.getCategoryId())
                .available(item.isAvailable())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    public Item toDomain() {
        return new Item(
                this.id,
                this.ownerId,
                this.title,
                this.description,
                this.dailyRate,
                this.depositAmount,
                this.imageUrls,
                this.categoryId,
                this.available,
                this.createdAt,
                this.updatedAt
        );
    }
}
