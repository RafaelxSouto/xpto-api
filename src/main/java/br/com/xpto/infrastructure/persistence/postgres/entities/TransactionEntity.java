package br.com.xpto.infrastructure.persistence.postgres.entities;

import br.com.xpto.core.domain.entities.Transaction;
import br.com.xpto.core.domain.entities.TransactionStatus;
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
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String rentalId;

    @Column(nullable = false)
    private String stripePaymentIntentId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static TransactionEntity fromDomain(Transaction transaction) {
        return TransactionEntity.builder()
                .id(transaction.getId())
                .rentalId(transaction.getRentalId())
                .stripePaymentIntentId(transaction.getStripePaymentIntentId())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }

    public Transaction toDomain() {
        return new Transaction(
                this.id,
                this.rentalId,
                this.stripePaymentIntentId,
                this.amount,
                this.status,
                this.createdAt,
                this.updatedAt
        );
    }
}
