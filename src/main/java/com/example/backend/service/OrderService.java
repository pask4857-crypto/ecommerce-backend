package com.example.backend.service;

import com.example.backend.dto.OrderItemRequest;
import com.example.backend.dto.OrderRequest;
import com.example.backend.entity.Order;
import com.example.backend.entity.OrderItem;
import com.example.backend.entity.Product;
import com.example.backend.repository.OrderRepository;
import com.example.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order placeOrder(OrderRequest request) {
        // 建立 Order
        Order order = new Order();
        order.setName(request.getName());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());

        // 建立 OrderItem 清單
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemReq : request.getItems()) {
            // 透過 productId 查商品
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("商品不存在，ID: " + itemReq.getProductId()));

            // 建立 OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order); // 設定回到 Order

            orderItems.add(orderItem);
        }

        // 設定 Order 的 items
        order.setItems(orderItems);

        // 存入資料庫並回傳
        return orderRepository.save(order);
    }
}
