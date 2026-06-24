package com.ecommerce.order.management.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.order.management.application.dto.ClientRequestDTO;
import com.ecommerce.order.management.application.dto.ClientResponseDTO;
import com.ecommerce.order.management.domain.model.Client;
import com.ecommerce.order.management.domain.repository.ClientRepository;

@ExtendWith(MockitoExtension.class)
class SaveClientUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    private SaveClientUseCase saveClientUseCase;

    @BeforeEach
    void setUp() {
        saveClientUseCase = new SaveClientUseCase(clientRepository);
    }

    @Test
    void shouldSaveClientSuccessfully() {
        ClientRequestDTO request = ClientRequestDTO.builder()
                .userId("75c97531-abf5-4524-8107-90aa48d08efc")
                .firstName("Barry")
                .paternalLastName("Reverse")
                .maternalLastName("Flash")
                .email("barry.allen@example.com")
                .shippingAddress("CDMX")
                .build();

        Client mockClient = Client.builder()
                .userId("75c97531-abf5-4524-8107-90aa48d08efc")
                .firstName("Barry")
                .paternalLastName("Reverse")
                .maternalLastName("Flash")
                .email("barry.allen@example.com")
                .orders(java.util.List.of())
                .build();

        when(clientRepository.save(any(Client.class))).thenReturn(mockClient);

        ClientResponseDTO response = saveClientUseCase.execute(request);

        assertNotNull(response);
        assertEquals("75c97531-abf5-4524-8107-90aa48d08efc", response.getUserId());
        assertEquals("Barry Reverse Flash", response.getFullName());
        verify(clientRepository, times(1)).save(any(Client.class));
    }
}