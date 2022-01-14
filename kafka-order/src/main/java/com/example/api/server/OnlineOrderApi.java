package com.example.api.server;

import com.example.api.request.InventoryRequest;
import com.example.api.request.OnlineOrderRequest;
import com.example.api.request.OnlinePaymentRequest;
import com.example.command.service.InventoryService;
import com.example.command.service.OnlineOrderService;
import com.example.command.service.OnlinePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/online")
public class OnlineOrderApi {

    @Autowired
    private OnlineOrderService orderService;

    @Autowired
    private OnlinePaymentService paymentService;

    @PostMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> add(@RequestBody OnlineOrderRequest request) {
        orderService.publish(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/payment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@RequestBody OnlinePaymentRequest request) {
        paymentService.publish(request);

        return ResponseEntity.ok().build();
    }
}
