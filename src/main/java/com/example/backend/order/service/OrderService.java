package com.example.backend.order.service;

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

        public OrderResponseDTO getOrderById(Long orderId) {
                Order order = orderRepository.findById(orderId)
                                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
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

                // 1️⃣ 取得購物車
                Cart cart = cartRepository.findByUserId(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

                List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());
                if (cartItems.isEmpty()) {
                        throw new IllegalStateException("Cannot create order: cart is empty");
                }

                // 2️⃣ 計算總金額
                int totalAmount = cartItems.stream().mapToInt(CartItem::getTotalPrice).sum();
                int discountAmount = 0;

                // 3️⃣ 建立 Order
                Order order = Order.createFromCart(userId, totalAmount, discountAmount, paymentMethod);
                orderRepository.save(order);

                // 4️⃣ 將 CartItem 轉成 OrderItem
                for (CartItem cartItem : cartItems) {
                        OrderItem orderItem = OrderItem.fromCartItem(order.getOrderId(), cartItem, "ProductName"); // 需取得實際商品名稱
                        orderItemRepository.save(orderItem);
                }

                // 5️⃣ 清空購物車
                cartItemRepository.deleteAll(cartItems);

                // 6️⃣ 回傳結果
                return getOrderById(order.getOrderId());
        }
}
