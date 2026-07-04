package br.com.xpto.presentation.controllers;

import br.com.xpto.core.application.dto.RentalRequestDTO;
import br.com.xpto.core.application.dto.RentalResponseDTO;
import br.com.xpto.core.application.usecases.RentalUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalUseCase rentalUseCase;

    @PostMapping("/request")
    public ResponseEntity<RentalResponseDTO> requestRental(@RequestBody RentalRequestDTO request, Authentication authentication) {
        // Ideally, map authentication principal to get the renter's ID.
        // For simplicity, we assume the DTO is populated with it or we inject it here.
        // request.setRenterId(authentication.getName()); // Assuming name is ID/Email
        return ResponseEntity.ok(rentalUseCase.requestRental(request));
    }

    @PostMapping("/{rentalId}/accept")
    public ResponseEntity<RentalResponseDTO> acceptRental(@PathVariable String rentalId, Authentication authentication) {
        return ResponseEntity.ok(rentalUseCase.acceptRental(rentalId, authentication.getName()));
    }

    @PostMapping("/{rentalId}/dispute")
    public ResponseEntity<RentalResponseDTO> openDispute(@PathVariable String rentalId) {
        return ResponseEntity.ok(rentalUseCase.openDispute(rentalId));
    }

    @GetMapping("/renter")
    public ResponseEntity<List<RentalResponseDTO>> getMyRentals(Authentication authentication) {
        return ResponseEntity.ok(rentalUseCase.getRentalsByRenter(authentication.getName()));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<RentalResponseDTO>> getMyItemRentals(Authentication authentication) {
        return ResponseEntity.ok(rentalUseCase.getRentalsByOwner(authentication.getName()));
    }
}
