package com.example.backend.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.order.dto.OrderItemResponseDTO;
import com.example.backend.order.mapper.OrderItemMapper;
import com.example.backend.order.repository.OrderItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public List<OrderItemResponseDTO> getItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }
}
