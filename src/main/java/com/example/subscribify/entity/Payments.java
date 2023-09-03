package com.example.subscribify.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payments extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String productId;

    private Long amount;

    private PaymentStatus status;

}
