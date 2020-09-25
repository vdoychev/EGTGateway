package com.example.gateway.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "fixer")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FixerModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "base")
    private String base;

    @Column(name = "currency")
    private String currency;

    @Column(name = "rate")
    private BigDecimal rate;
}
