package com.ecommerce.order.management.infrastructure.adapters.inbound.rest;

import com.ecommerce.order.management.application.dto.ClientRequestDTO;
import com.ecommerce.order.management.application.dto.ClientResponseDTO;
import com.ecommerce.order.management.application.usecase.GetClientWithOrdersUseCase;
import com.ecommerce.order.management.application.usecase.SaveClientUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients")
@Tag(name = "Client Management", description = "Endpoints for creating, updating, and retrieving clients with their orders")
public class ClientController {

    private final SaveClientUseCase saveClientUseCase;
    private final GetClientWithOrdersUseCase getClientWithOrdersUseCase;

    public ClientController(SaveClientUseCase saveClientUseCase, GetClientWithOrdersUseCase getClientWithOrdersUseCase) {
        this.saveClientUseCase = saveClientUseCase;
        this.getClientWithOrdersUseCase = getClientWithOrdersUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a new client in the system")
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody ClientRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveClientUseCase.execute(request));
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update an existing client's information")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable String userId, @RequestBody ClientRequestDTO request) {
        request.setUserId(userId);
        return ResponseEntity.ok(saveClientUseCase.execute(request));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Retrieve a client and synchronize their orders from the external API")
    public ResponseEntity<ClientResponseDTO> getClient(@PathVariable String userId) {
        return ResponseEntity.ok(getClientWithOrdersUseCase.execute(userId));
    }
}