package br.com.xpto.core.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalRequestDTO {
    private String itemId;
    private String renterId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal expectedTotalAmount;
}
