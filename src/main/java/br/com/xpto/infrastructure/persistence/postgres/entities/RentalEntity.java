package br.com.xpto.infrastructure.persistence.postgres.entities;

import br.com.xpto.core.domain.entities.Rental;
import br.com.xpto.core.domain.entities.RentalStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String itemId;

    @Column(nullable = false)
    private String renterId;

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static RentalEntity fromDomain(Rental rental) {
        return RentalEntity.builder()
                .id(rental.getId())
                .itemId(rental.getItemId())
                .renterId(rental.getRenterId())
                .ownerId(rental.getOwnerId())
                .startDate(rental.getStartDate())
                .endDate(rental.getEndDate())
                .status(rental.getStatus())
                .totalAmount(rental.getTotalAmount())
                .createdAt(rental.getCreatedAt())
                .updatedAt(rental.getUpdatedAt())
                .build();
    }

    public Rental toDomain() {
        return new Rental(
                this.id,
                this.itemId,
                this.renterId,
                this.ownerId,
                this.startDate,
                this.endDate,
                this.status,
                this.totalAmount,
                this.createdAt,
                this.updatedAt
        );
    }
}
