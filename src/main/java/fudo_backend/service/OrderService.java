package fudo_backend.service;

import fudo_backend.model.Order;
import fudo_backend.model.OrderItem;
import fudo_backend.model.Product;
import fudo_backend.model.OrderStatus; // මේක අලුතෙන් Import කළා
import fudo_backend.repository.OrderItemRepository;
import fudo_backend.repository.OrderRepository;
import fudo_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    public Order placeOrder(Order order, List<OrderItem> orderItems) {
        double totalAmount = 0.0;

        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : orderItems) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found!"));

            item.setPrice(product.getPrice());
            item.setOrder(savedOrder);

            totalAmount += (product.getPrice() * item.getQuantity());

            orderItemRepository.save(item);
        }

        savedOrder.setTotalPrice(totalAmount);
        return orderRepository.save(savedOrder);
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 🚀 අලුතෙන් එකතු කළ Order Status Update Method එක
    public Order updateOrderStatus(Long orderId, String newStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            // String එකක් විදිහට එන status එක Enum එකකට හරවලා සේව් කරනවා
            order.setStatus(OrderStatus.valueOf(newStatus.toUpperCase()));
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }
}