package com.example.backend.order.service;

import com.example.backend.cart.entity.Cart;
import com.example.backend.cart.entity.CartItem;
import com.example.backend.cart.repository.CartItemRepository;
import com.example.backend.cart.repository.CartRepository;
import com.example.backend.common.exception.ResourceNotFoundException;
import com.example.backend.order.dto.OrderRequestDTO;
import com.example.backend.order.dto.OrderResponseDTO;
import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.OrderItem;
import com.example.backend.order.mapper.OrderMapper;
import com.example.backend.order.repository.OrderItemRepository;
import com.example.backend.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    /*
     * ======================
     * 基本查詢
     * ======================
     */

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
        return orderMapper.toDto(order);
    }

    /**
     * 管理 / 測試用途（非正式結帳）
     */
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        Order order = orderMapper.toEntity(dto);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return orderMapper.toDto(orderRepository.save(order));
    }

    /*
     * ======================
     * 正式下單流程
     * ======================
     */

    @Transactional
    public OrderResponseDTO createOrderFromCart(Long userId) {

        // 1️⃣ 取得購物車
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cannot create order: cart is empty");
        }

        // 2️⃣ 計算訂單金額（CartItem 為快照）
        int totalAmount = cartItems.stream()
                .mapToInt(CartItem::getTotalPrice)
                .sum();

        int discountAmount = 0;

        // 3️⃣ 建立訂單
        Order savedOrder = orderRepository.save(
                Order.createFromCart(
                        userId,
                        totalAmount,
                        discountAmount,
                        "NOT_SELECTED" // paymentMethod
                ));

        // 4️⃣ 建立訂單項目
        for (CartItem cartItem : cartItems) {
            orderItemRepository.save(
                    OrderItem.fromCartItem(
                            savedOrder.getOrderId(),
                            cartItem));
        }

        // 5️⃣ 清空購物車
        cartItemRepository.deleteAll(cartItems);

        // 6️⃣ 回傳結果
        return orderMapper.toDto(savedOrder);
    }
}
