package com.example.backend.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.common.exception.ResourceNotFoundException;
import com.example.backend.order.dto.OrderItemRequestDTO;
import com.example.backend.order.dto.OrderItemResponseDTO;
import com.example.backend.order.entity.OrderItem;
import com.example.backend.order.mapper.OrderItemMapper;
import com.example.backend.order.repository.OrderItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public List<OrderItemResponseDTO> getByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    public OrderItemResponseDTO create(OrderItemRequestDTO dto) {
        OrderItem orderItem = orderItemMapper.toEntity(dto);

        // 🔒 subtotal 一定由後端計算
        orderItem.setSubtotal(orderItem.getPrice() * orderItem.getQuantity());

        return orderItemMapper.toDto(orderItemRepository.save(orderItem));
    }

    public void delete(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("OrderItem not found: " + id);
        }
        orderItemRepository.deleteById(id);
    }
}
