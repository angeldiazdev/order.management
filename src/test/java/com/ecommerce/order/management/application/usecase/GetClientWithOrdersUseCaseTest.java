package com.ecommerce.order.management.application.usecase;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.order.management.application.dto.ClientResponseDTO;
import com.ecommerce.order.management.domain.model.Client;
import com.ecommerce.order.management.domain.model.Order;
import com.ecommerce.order.management.domain.repository.ClientRepository;
import com.ecommerce.order.management.domain.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class GetClientWithOrdersUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private OrderRepository orderRepository;

    private GetClientWithOrdersUseCase getClientWithOrdersUseCase;

    @BeforeEach
    void setUp() {
        getClientWithOrdersUseCase = new GetClientWithOrdersUseCase(clientRepository, orderRepository);
    }

    @Test
    void shouldGetClientWithOrdersSuccessfully() {
        String userId = "75c97531-abf5-4524-8107-90aa48d08efc";
        Client mockClient = Client.builder()
                .userId(userId)
                .firstName("Barry")
                .paternalLastName("Allen")
                .maternalLastName("Perez")
                .email("barry.allen@example.com")
                .orders(List.of())
                .build();

        List<Order> mockOrders = List.of(
            Order.builder().orderRef("3010091676").userId(userId).build(),
            Order.builder().orderRef("2025121636").userId("different-user-id").build(),
            Order.builder().orderRef("632005897").userId(userId).build()
        );

        when(clientRepository.findById(userId)).thenReturn(Optional.of(mockClient));
        when(orderRepository.fetchAllOrders()).thenReturn(mockOrders);

        ClientResponseDTO response = getClientWithOrdersUseCase.execute(userId);

        assertNotNull(response);
        assertEquals(2, response.getOrders().size());
        assertTrue(response.getOrders().contains("3010091676"));
        assertTrue(response.getOrders().contains("632005897"));
        assertFalse(response.getOrders().contains("2025121636"));

        verify(clientRepository, times(1)).findById(userId);
        verify(orderRepository, times(1)).fetchAllOrders();
        verify(clientRepository, times(1)).update(mockClient);
    }

    @Test
    void shouldThrowExceptionWhenClientDoesNotExist() {
        String userId = "non-existent-id";
        when(clientRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            getClientWithOrdersUseCase.execute(userId)
        );

        assertEquals("Client not found with ID: " + userId, exception.getMessage());
        verify(clientRepository, times(1)).findById(userId);
        verifyNoInteractions(orderRepository);
    }
}