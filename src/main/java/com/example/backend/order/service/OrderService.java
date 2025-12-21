package com.example.backend.order.service;

import com.example.backend.cart.entity.Cart;
import com.example.backend.cart.entity.CartItem;
import com.example.backend.cart.repository.CartItemRepository;
import com.example.backend.cart.repository.CartRepository;
import com.example.backend.order.dto.OrderDetailResponse;
import com.example.backend.order.dto.OrderItemResponse;
import com.example.backend.order.entity.Order;
import com.example.backend.order.entity.OrderItem;
import com.example.backend.order.repository.OrderItemRepository;
import com.example.backend.order.repository.OrderRepository;
import com.example.backend.product.entity.Product;
import com.example.backend.product.entity.ProductVariant;
import com.example.backend.product.repository.ProductRepository;
import com.example.backend.product.repository.ProductVariantRepository;

import java.math.BigDecimal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

        private final OrderRepository orderRepository;
        private final OrderItemRepository orderItemRepository;
        private final CartRepository cartRepository;
        private final CartItemRepository cartItemRepository;
        private final ProductRepository productRepository;
        private final ProductVariantRepository productVariantRepository;

        @Transactional
        public Long placeOrder(Long userId, String shippingAddress) {

                // 1️⃣ 找購物車
                Cart cart = cartRepository.findByUserId(userId)
                                .orElseThrow(() -> new IllegalStateException("購物車不存在"));

                List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

                if (cartItems.isEmpty()) {
                        throw new IllegalStateException("購物車是空的");
                }

                BigDecimal totalAmount = BigDecimal.ZERO;

                // 2️⃣ 檢查庫存 + 計算金額
                for (CartItem item : cartItems) {
                        ProductVariant variant = productVariantRepository.findById(
                                        item.getProductVariantId())
                                        .orElseThrow(() -> new IllegalStateException("商品規格不存在"));

                        if (variant.getStockQuantity() < item.getQuantity()) {
                                throw new IllegalStateException("庫存不足，SKU：" + variant.getSku());
                        }

                        totalAmount = totalAmount.add(
                                        variant.getPrice()
                                                        .multiply(BigDecimal.valueOf(item.getQuantity())));
                }

                // 3️⃣ 建立訂單
                Order order = Order.create(
                                generateOrderNumber(),
                                userId,
                                totalAmount,
                                shippingAddress);
                orderRepository.save(order);

                // 4️⃣ 建立 OrderItem + 扣庫存
                for (CartItem item : cartItems) {
                        ProductVariant variant = productVariantRepository.findById(
                                        item.getProductVariantId()).orElseThrow();

                        Product product = productRepository.findById(
                                        variant.getProductId()).orElseThrow();

                        OrderItem orderItem = OrderItem.create(
                                        order.getId(),
                                        product.getName(), // ✅ 從 Product 來
                                        variant.getVariantName(),
                                        variant.getSku(),
                                        variant.getPrice(),
                                        item.getQuantity());

                        orderItemRepository.save(orderItem);
                        variant.decreaseStock(item.getQuantity());
                }

                // 5️⃣ 清空購物車
                cartItemRepository.deleteByCartId(cart.getId());

                return order.getId();
        }

        private String generateOrderNumber() {
                return "ORD-" + System.currentTimeMillis();
        }

        @Transactional(readOnly = true)
        public OrderDetailResponse getOrderDetail(Long orderId) {

                Order order = orderRepository.findById(orderId)
                                .orElseThrow(() -> new IllegalStateException("訂單不存在"));

                List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

                List<OrderItemResponse> itemResponses = orderItems.stream()
                                .map(item -> new OrderItemResponse(
                                                item.getProductName(),
                                                item.getVariantName(),
                                                item.getSku(),
                                                item.getUnitPrice(),
                                                item.getQuantity(),
                                                item.getSubtotal()))
                                .toList();

                return new OrderDetailResponse(
                                order.getId(),
                                order.getOrderNumber(),
                                order.getStatus(),
                                order.getTotalAmount(),
                                order.getShippingAddress(),
                                itemResponses);
        }

        @Transactional(readOnly = true)
        public List<OrderDetailResponse> getOrdersByUser(Long userId) {

                List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);

                return orders.stream()
                                .map(order -> {
                                        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

                                        List<OrderItemResponse> itemResponses = items.stream()
                                                        .map(item -> new OrderItemResponse(
                                                                        item.getProductName(),
                                                                        item.getVariantName(),
                                                                        item.getSku(),
                                                                        item.getUnitPrice(),
                                                                        item.getQuantity(),
                                                                        item.getSubtotal()))
                                                        .toList();

                                        return new OrderDetailResponse(
                                                        order.getId(),
                                                        order.getOrderNumber(),
                                                        order.getStatus(),
                                                        order.getTotalAmount(),
                                                        order.getShippingAddress(),
                                                        itemResponses);
                                })
                                .toList();
        }

}
