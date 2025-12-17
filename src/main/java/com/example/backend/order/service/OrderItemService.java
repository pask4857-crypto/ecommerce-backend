package com.example.backend.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.order.dto.OrderItemResponseDTO;
import com.example.backend.order.entity.OrderItem;
import com.example.backend.order.repository.OrderItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public List<OrderItemResponseDTO> getItemsByOrderId(Long orderId) {
        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);
        return items.stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getOrderItemId(),
                        item.getOrderId(),
                        item.getProductId(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getSubtotal()))
                .toList();
    }
}
