package com.example.gateway.controller;

import com.example.gateway.rest.CurrentRequest;
import com.example.gateway.rest.HistoryRequest;
import com.example.gateway.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "/json_api")
public class GatewayController {

    @Autowired
    private GatewayService service;

    @PostMapping("/current")
    ResponseEntity<BigDecimal> current(@RequestBody CurrentRequest request) {
        BigDecimal response;
        try {
            response = service.current(request);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/history")
    ResponseEntity<List<BigDecimal>> history(@RequestBody HistoryRequest request) {
        List<BigDecimal> response;
        try {
            response = service.history(request);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }
}