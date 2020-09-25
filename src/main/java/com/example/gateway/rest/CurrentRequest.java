package com.example.gateway.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentRequest {
    private String requestId;
    private Long timestamp;
    private String client;
    private String currency;
}
