package br.com.xpto.core.application.dto;

import br.com.xpto.core.domain.entities.Rental;
import br.com.xpto.core.domain.entities.RentalStatus;
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
public class RentalResponseDTO {
    private String id;
    private String itemId;
    private String renterId;
    private String ownerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private RentalStatus status;
    private BigDecimal totalAmount;

    public static RentalResponseDTO fromDomain(Rental rental) {
        return RentalResponseDTO.builder()
                .id(rental.getId())
                .itemId(rental.getItemId())
                .renterId(rental.getRenterId())
                .ownerId(rental.getOwnerId())
                .startDate(rental.getStartDate())
                .endDate(rental.getEndDate())
                .status(rental.getStatus())
                .totalAmount(rental.getTotalAmount())
                .build();
    }
}
