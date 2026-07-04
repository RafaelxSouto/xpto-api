package br.com.xpto.core.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Rental {
    private String id;
    private String itemId;
    private String renterId;
    private String ownerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private RentalStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Rental(String id, String itemId, String renterId, String ownerId, LocalDateTime startDate, LocalDateTime endDate, RentalStatus status, BigDecimal totalAmount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.itemId = itemId;
        this.renterId = renterId;
        this.ownerId = ownerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Rental createRequest(String itemId, String renterId, String ownerId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal totalAmount) {
        return new Rental(null, itemId, renterId, ownerId, startDate, endDate, RentalStatus.PENDING, totalAmount, LocalDateTime.now(), LocalDateTime.now());
    }

    public void accept() {
        if (this.status != RentalStatus.PENDING) throw new IllegalStateException("Only pending rentals can be accepted.");
        this.status = RentalStatus.ACCEPTED;
        this.updatedAt = LocalDateTime.now();
    }

    public void reject() {
        if (this.status != RentalStatus.PENDING) throw new IllegalStateException("Only pending rentals can be rejected.");
        this.status = RentalStatus.REJECTED;
        this.updatedAt = LocalDateTime.now();
    }

    public void start() {
        if (this.status != RentalStatus.ACCEPTED) throw new IllegalStateException("Only accepted rentals can be started.");
        this.status = RentalStatus.IN_PROGRESS;
        this.updatedAt = LocalDateTime.now();
    }

    public void complete() {
        if (this.status != RentalStatus.IN_PROGRESS) throw new IllegalStateException("Only in-progress rentals can be completed.");
        this.status = RentalStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    public void openDispute() {
        this.status = RentalStatus.DISPUTED;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getItemId() { return itemId; }
    public String getRenterId() { return renterId; }
    public String getOwnerId() { return ownerId; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public RentalStatus getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
