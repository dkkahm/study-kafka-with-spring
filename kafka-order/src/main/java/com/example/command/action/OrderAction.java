package com.example.command.action;

import com.example.api.request.OrderItemRequest;
import com.example.api.request.OrderRequest;
import com.example.broker.message.OrderMessage;
import com.example.broker.producer.OrderProducer;
import com.example.entity.Order;
import com.example.entity.OrderItem;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderAction {
    @Autowired
    private OrderProducer producer;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order convertToOrder(OrderRequest request) {
        var result = new Order();

        result.setCreditCardNumber(request.getCreditCardNumber());
        result.setOrderLocation(request.getOrderLocation());
        result.setOrderDateTime(LocalDateTime.now());
        result.setOrderNumber(RandomStringUtils.randomAlphanumeric(8).toUpperCase());

        List<OrderItem> items =request.getItems().stream()
                .map(this::convertToOrderItem)
                .collect(Collectors.toList());
        items.forEach(item -> item.setOrder(result));
        result.setItems(items);

        return result;
    }

    private OrderItem convertToOrderItem(OrderItemRequest request) {
        var result = new OrderItem();

        result.setItemName(request.getItemName());
        result.setPrice(request.getPrice());
        result.setQuantity(request.getQuantity());

        return result;
    }

    public void saveToDatabase(Order order) {
        orderRepository.save(order);
        order.getItems().forEach(orderItemRepository::save);
    }

    public void publishToKafka(OrderItem item) {
        var orderMessage = new OrderMessage();

        orderMessage.setItemName(item.getItemName());
        orderMessage.setPrice(item.getPrice());
        orderMessage.setQuantity(item.getQuantity());

        orderMessage.setOrderDateTime(item.getOrder().getOrderDateTime());
        orderMessage.setOrderLocation(item.getOrder().getOrderLocation());
        orderMessage.setOrderNumber(item.getOrder().getOrderNumber());
        orderMessage.setCreditCardNumber(item.getOrder().getCreditCardNumber());

        producer.publish(orderMessage);
    }
}
