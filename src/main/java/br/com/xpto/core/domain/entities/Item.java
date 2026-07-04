package br.com.xpto.core.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Item {
    private String id;
    private String ownerId;
    private String title;
    private String description;
    private BigDecimal dailyRate;
    private BigDecimal depositAmount;
    private List<String> imageUrls;
    private String categoryId;
    private boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Item(String id, String ownerId, String title, String description, BigDecimal dailyRate, BigDecimal depositAmount, List<String> imageUrls, String categoryId, boolean available, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.dailyRate = dailyRate;
        this.depositAmount = depositAmount;
        this.imageUrls = imageUrls;
        this.categoryId = categoryId;
        this.available = available;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Item create(String ownerId, String title, String description, BigDecimal dailyRate, BigDecimal depositAmount, List<String> imageUrls, String categoryId) {
        return new Item(null, ownerId, title, description, dailyRate, depositAmount, imageUrls, categoryId, true, LocalDateTime.now(), LocalDateTime.now());
    }

    public void markAsUnavailable() {
        this.available = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsAvailable() {
        this.available = true;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getOwnerId() { return ownerId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public BigDecimal getDailyRate() { return dailyRate; }
    public BigDecimal getDepositAmount() { return depositAmount; }
    public List<String> getImageUrls() { return imageUrls; }
    public String getCategoryId() { return categoryId; }
    public boolean isAvailable() { return available; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
