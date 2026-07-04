package br.com.xpto.core.application.dto;

import br.com.xpto.core.domain.entities.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private String id;
    private String title;
    private String description;
    private BigDecimal dailyRate;
    private BigDecimal depositAmount;
    private List<String> imageUrls;
    private String categoryId;

    public static ItemResponse fromDomain(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .dailyRate(item.getDailyRate())
                .depositAmount(item.getDepositAmount())
                .imageUrls(item.getImageUrls())
                .categoryId(item.getCategoryId())
                .build();
    }
}
