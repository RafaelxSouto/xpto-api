package br.com.xpto.core.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private String rentalId;
    private String stripePaymentIntentId;
    private BigDecimal amount;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Transaction(String id, String rentalId, String stripePaymentIntentId, BigDecimal amount, TransactionStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rentalId = rentalId;
        this.stripePaymentIntentId = stripePaymentIntentId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Transaction create(String rentalId, String stripePaymentIntentId, BigDecimal amount) {
        return new Transaction(null, rentalId, stripePaymentIntentId, amount, TransactionStatus.PENDING_PAYMENT, LocalDateTime.now(), LocalDateTime.now());
    }

    public void markAsPaid() {
        this.status = TransactionStatus.PAID;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsRefunded() {
        this.status = TransactionStatus.REFUNDED;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsFailed() {
        this.status = TransactionStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getRentalId() { return rentalId; }
    public String getStripePaymentIntentId() { return stripePaymentIntentId; }
    public BigDecimal getAmount() { return amount; }
    public TransactionStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
