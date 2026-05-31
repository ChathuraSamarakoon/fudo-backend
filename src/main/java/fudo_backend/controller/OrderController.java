package fudo_backend.controller;

import fudo_backend.model.Order;
import fudo_backend.model.OrderItem;
import fudo_backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    public static class OrderRequest {
        private Order order;
        private List<OrderItem> orderItems;

        public Order getOrder() { return order; }
        public void setOrder(Order order) { this.order = order; }

        public List<OrderItem> getOrderItems() { return orderItems; }
        public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
    }


    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {

        Order savedOrder = orderService.placeOrder(orderRequest.getOrder(), orderRequest.getOrderItems());
        return ResponseEntity.ok(savedOrder);
    }


    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}