package com.example.backend.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.cart.entity.Cart;
import com.example.backend.cart.entity.CartItem;
import com.example.backend.cart.repository.CartItemRepository;
import com.example.backend.cart.repository.CartRepository;
import com.example.backend.common.exception.ResourceNotFoundException;
import com.example.backend.order.dto.OrderItemSnapshotDTO;
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
        // public List<OrderResponseDTO> getAllOrders() {
        // return orderRepository.findAll().stream()
        // .map(order -> new OrderResponseDTO(
        // order.getOrderId(),
        // order.getUserId(),
        // order.getTotalAmount(),
        // order.getDiscountAmount(),
        // order.getFinalAmount(),
        // order.getStatus(),
        // order.getPaymentMethod(),
        // order.getCreatedAt(),
        // order.getUpdatedAt()))
        // .toList();
        // }

        public OrderResponseDTO getOrderById(Long userId, Long orderId) {

                // 1️⃣ 驗證使用者是否啟用
                userService.validateUserIsActive(userId);

                // 2️⃣ 取得訂單
                Order order = orderRepository.findById(orderId)
                                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));

                // 3️⃣ 檢查訂單是否屬於該使用者
                if (!order.getUserId().equals(userId)) {
                        throw new IllegalArgumentException("Order does not belong to user: " + userId);
                }

                // 4️⃣ 取得訂單明細
                List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

                // 5️⃣ 將 OrderItem 轉成快照 DTO
                List<OrderItemSnapshotDTO> snapshots = orderItems.stream()
                                .map(item -> new OrderItemSnapshotDTO(
                                                item.getProductId(),
                                                item.getProductName(),
                                                item.getQuantity(),
                                                item.getPrice(),
                                                item.getSubtotal()))
                                .toList();

                // 6️⃣ 回傳 OrderResponseDTO（包含明細快照）
                return new OrderResponseDTO(
                                order.getOrderId(),
                                order.getUserId(),
                                order.getTotalAmount(),
                                order.getDiscountAmount(),
                                order.getFinalAmount(),
                                order.getStatus(),
                                order.getPaymentMethod(),
                                order.getCreatedAt(),
                                order.getUpdatedAt(),
                                snapshots);
        }

        /*
         * ======================
         * 正式下單流程
         * ======================
         */
        @Transactional
        public OrderResponseDTO createOrderFromCart(Long userId, String paymentMethod) {

                // 1️⃣ 驗證使用者是否啟用
                userService.validateUserIsActive(userId);

                if (paymentMethod == null || paymentMethod.isBlank()) {
                        throw new IllegalArgumentException("Payment method is required.");
                }

                // 2️⃣ 取得購物車
                Cart cart = cartRepository.findByUserId(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

                List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());
                if (cartItems.isEmpty()) {
                        throw new IllegalStateException("Cannot create order: cart is empty");
                }

                // 3️⃣ 計算總金額
                BigDecimal totalAmount = cartItems.stream()
                                .map(CartItem::getTotalPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal discountAmount = BigDecimal.ZERO;

                // 4️⃣ 建立訂單
                Order order = Order.createFromCart(userId, totalAmount, discountAmount, paymentMethod);
                orderRepository.save(order);

                // 5️⃣ 建立 OrderItem 快照 & 儲存 OrderItem
                List<OrderItemSnapshotDTO> snapshots = new ArrayList<>();

                for (CartItem cartItem : cartItems) {

                        Product product = productRepository.findById(cartItem.getProductId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Product not found: " + cartItem.getProductId()));

                        // 建立商品快照 DTO
                        OrderItemSnapshotDTO snapshot = new OrderItemSnapshotDTO(
                                        product.getProductId(),
                                        product.getName(),
                                        cartItem.getQuantity(),
                                        cartItem.getUnitPrice(),
                                        cartItem.getTotalPrice());

                        // 使用快照建立 OrderItem
                        OrderItem orderItem = OrderItem.fromSnapshot(order.getOrderId(), snapshot);
                        orderItemRepository.save(orderItem);

                        snapshots.add(snapshot);
                }

                // 6️⃣ 清空購物車
                cartItemRepository.deleteAll(cartItems);

                // 7️⃣ 回傳 DTO（包含快照明細）
                OrderResponseDTO response = new OrderResponseDTO(
                                order.getOrderId(),
                                order.getUserId(),
                                order.getTotalAmount(),
                                order.getDiscountAmount(),
                                order.getFinalAmount(),
                                order.getStatus(),
                                order.getPaymentMethod(),
                                order.getCreatedAt(),
                                order.getUpdatedAt(),
                                snapshots);

                return response;
        }
}
