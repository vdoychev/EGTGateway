package com.example.gateway.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "request")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "client_id")
    private String clientId;
}
