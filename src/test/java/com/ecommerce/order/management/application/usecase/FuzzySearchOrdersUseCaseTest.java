package com.ecommerce.order.management.application.usecase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.order.management.application.dto.OrderSearchResponseDTO;
import com.ecommerce.order.management.domain.model.Item;
import com.ecommerce.order.management.domain.model.Order;
import com.ecommerce.order.management.domain.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class FuzzySearchOrdersUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    private FuzzySearchOrdersUseCase fuzzySearchOrdersUseCase;

    @BeforeEach
    void setUp() {
        fuzzySearchOrdersUseCase = new FuzzySearchOrdersUseCase(orderRepository);
    }

    @Test
    void shouldReturnAllOrdersWhenKeywordIsEmpty() {
        List<Order> mockOrders = List.of(
            Order.builder().orderRef("111").storeName("Liverpool Centro").items(List.of()).build()
        );
        List<Item> mockItems = List.of();

        when(orderRepository.fetchAllOrders()).thenReturn(mockOrders);
        when(orderRepository.fetchAllItems()).thenReturn(mockItems);

        List<OrderSearchResponseDTO> result = fuzzySearchOrdersUseCase.execute("");

        assertEquals(1, result.size());
        assertEquals("111", result.get(0).getOrderRef());
        verify(orderRepository, times(1)).fetchAllOrders();
    }

    @Test
    void shouldFindOrderIgnoringCaseAndAccents() {
        List<Order> mockOrders = List.of(
            Order.builder().orderRef("222").storeName("Liverpool Angelópolis").items(List.of()).build()
        );
        List<Item> mockItems = List.of();

        when(orderRepository.fetchAllOrders()).thenReturn(mockOrders);
        when(orderRepository.fetchAllItems()).thenReturn(mockItems);

        List<OrderSearchResponseDTO> result = fuzzySearchOrdersUseCase.execute("angelopolis");

        assertEquals(1, result.size());
        assertEquals("222", result.get(0).getOrderRef());
    }

    @Test
    void shouldFindOrderWithMinorTyposUsingLevenshtein() {
        List<Order> mockOrders = List.of(
            Order.builder().orderRef("333").storeName("Liverpool Galerías Toluca").items(List.of()).build()
        );
        List<Item> mockItems = List.of();

        when(orderRepository.fetchAllOrders()).thenReturn(mockOrders);
        when(orderRepository.fetchAllItems()).thenReturn(mockItems);

        List<OrderSearchResponseDTO> result = fuzzySearchOrdersUseCase.execute("tolka");

        assertEquals(1, result.size());
        assertEquals("333", result.get(0).getOrderRef());
    }
}