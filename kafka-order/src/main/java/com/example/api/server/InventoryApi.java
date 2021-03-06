package com.example.api.server;

import com.example.api.request.InventoryRequest;
import com.example.command.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryApi {

    @Autowired
    private InventoryService service;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> add(@RequestBody InventoryRequest request) {
        service.addInventory(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/remove", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@RequestBody InventoryRequest request) {
        service.removeInventory(request);

        return ResponseEntity.ok().build();
    }
}
