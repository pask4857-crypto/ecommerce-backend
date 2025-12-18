package com.example.backend.order.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.cart.entity.Cart;
import com.example.backend.cart.entity.CartItem;
import com.example.backend.cart.repository.CartItemRepository;
import com.example.backend.cart.repository.CartRepository;
import com.example.backend.common.exception.ResourceNotFoundException;
import com.example.backend.order.dto.OrderResponseDTO;
import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.OrderItem;
import com.example.backend.order.repository.OrderItemRepository;
import com.example.backend.order.repository.OrderRepository;
import com.example.backend.product.entity.Product;
import com.example.backend.product.repository.ProductRepository;
import com.example.backend.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

        private final OrderRepository orderRepository;
        private final OrderItemRepository orderItemRepository;

        private final CartRepository cartRepository;
        private final CartItemRepository cartItemRepository;

        private final UserService userService;

        private final ProductRepository productRepository;

        /*
         * ======================
         * 查詢訂單
         * ======================
         */
        public List<OrderResponseDTO> getAllOrders() {
                return orderRepository.findAll().stream()
                                .map(order -> new OrderResponseDTO(
                                                order.getOrderId(),
                                                order.getUserId(),
                                                order.getTotalAmount(),
                                                order.getDiscountAmount(),
                                                order.getFinalAmount(),
                                                order.getStatus(),
                                                order.getPaymentMethod(),
                                                order.getCreatedAt(),
                                                order.getUpdatedAt()))
                                .toList();
        }

        public OrderResponseDTO getOrderById(Long userId, Long orderId) {

                userService.validateUserIsActive(userId);

                Order order = orderRepository.findById(orderId)
                                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));

                if (!order.getUserId().equals(userId)) {
                        throw new IllegalArgumentException("Order does not belong to user: " + userId);
                }

                return new OrderResponseDTO(
                                order.getOrderId(),
                                order.getUserId(),
                                order.getTotalAmount(),
                                order.getDiscountAmount(),
                                order.getFinalAmount(),
                                order.getStatus(),
                                order.getPaymentMethod(),
                                order.getCreatedAt(),
                                order.getUpdatedAt());
        }

        /*
         * ======================
         * 正式下單流程
         * ======================
         */
        @Transactional
        public OrderResponseDTO createOrderFromCart(Long userId, String paymentMethod) {

                userService.validateUserIsActive(userId);

                if (paymentMethod == null || paymentMethod.isBlank()) {
                        throw new IllegalArgumentException("Payment method is required.");
                }

                Cart cart = cartRepository.findByUserId(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

                List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());
                if (cartItems.isEmpty()) {
                        throw new IllegalStateException("Cannot create order: cart is empty");
                }

                // 💰 計算總金額（BigDecimal）
                BigDecimal totalAmount = cartItems.stream()
                                .map(CartItem::getTotalPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal discountAmount = BigDecimal.ZERO;

                // 建立訂單
                Order order = Order.createFromCart(
                                userId,
                                totalAmount,
                                discountAmount,
                                paymentMethod);
                orderRepository.save(order);

                // 建立 OrderItem 快照
                for (CartItem cartItem : cartItems) {

                        Product product = productRepository.findById(cartItem.getProductId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Product not found: " + cartItem.getProductId()));

                        OrderItem orderItem = OrderItem.fromCartItem(
                                        order.getOrderId(),
                                        cartItem,
                                        product.getName());

                        orderItemRepository.save(orderItem);
                }

                // 清空購物車
                cartItemRepository.deleteAll(cartItems);

                return getOrderById(userId, order.getOrderId());
        }
}
