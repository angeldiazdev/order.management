package com.ecommerce.order.management.application.usecase;

import java.text.Normalizer;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

import com.ecommerce.order.management.application.dto.ItemSearchDTO;
import com.ecommerce.order.management.application.dto.OrderSearchResponseDTO;
import com.ecommerce.order.management.domain.model.Item;
import com.ecommerce.order.management.domain.model.Order;
import com.ecommerce.order.management.domain.repository.OrderRepository;

@Service
public class FuzzySearchOrdersUseCase {

    private final OrderRepository orderRepository;
    private static final int MAX_TYPO_TOLERANCE = 2;

    public FuzzySearchOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderSearchResponseDTO> execute(String keyword) {
        List<Order> externalOrders = orderRepository.fetchAllOrders();
        List<Item> externalItems = orderRepository.fetchAllItems();

        Map<String, Item> itemsById = externalItems.stream()
                .collect(Collectors.toMap(
                        Item::getItemId,
                        Function.identity(),
                        (existing, replacement) -> existing
                ));

        List<OrderSearchResponseDTO> aggregatedOrders = externalOrders.stream().map(order -> {
            List<ItemSearchDTO> matchedItems = order.getItems().stream()
                    .map(item -> findItemDetails(item.getItemId(), itemsById))
                    .collect(Collectors.toList());

            return OrderSearchResponseDTO.builder()
                    .orderRef(order.getOrderRef())
                    .orderStatus(order.getOrderStatus())
                    .storeName(order.getStoreName())
                    .items(matchedItems)
                    .build();
        }).collect(Collectors.toList());

        if (keyword == null || keyword.trim().isEmpty()) {
            return aggregatedOrders;
        }

        String normalizedKeyword = normalizeText(keyword);

        return aggregatedOrders.stream()
                .filter(order -> matchesFuzzySearch(order, normalizedKeyword))
                .collect(Collectors.toList());
    }

    private ItemSearchDTO findItemDetails(String itemId, Map<String, Item> itemsById) {
        Item item = itemsById.get(itemId);
        if (item != null) {
            return ItemSearchDTO.builder()
                    .itemId(item.getItemId())
                    .displayName(item.getDisplayName())
                    .quantity(item.getQuantity())
                    .build();
        }
        return ItemSearchDTO.builder()
                .itemId(itemId)
                .displayName("Unknown Item")
                .quantity(0)
                .build();
    }

    private boolean matchesFuzzySearch(OrderSearchResponseDTO order, String query) {
        if (isFuzzyMatch(order.getOrderRef(), query)) return true;
        if (isFuzzyMatch(order.getOrderStatus(), query)) return true;
        if (isFuzzyMatch(order.getStoreName(), query)) return true;

        for (ItemSearchDTO item : order.getItems()) {
            if (isFuzzyMatch(item.getDisplayName(), query)) {
                return true;
            }
        }
        return false;
    }

    private boolean isFuzzyMatch(String target, String query) {
        if (target == null) return false;

        String normalizedTarget = normalizeText(target);

        if (normalizedTarget.contains(query)) {
            return true;
        }

        LevenshteinDistance distance = LevenshteinDistance.getDefaultInstance();
        String[] targetWords = normalizedTarget.split("\\s+");
        String[] queryWords = query.split("\\s+");

        for (String qWord : queryWords) {
            if (qWord.length() < 3) continue;
            for (String tWord : targetWords) {
                if (distance.apply(qWord, tWord) <= MAX_TYPO_TOLERANCE) {
                    return true;
                }
            }
        }
        return false;
    }

    private String normalizeText(String input) {
        if (input == null) return "";
        String withoutAccents = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return withoutAccents.replaceAll("[,\\.]", "").toLowerCase().trim();
    }
}