package com.example.subscribify.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerSubscription extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String subscribeName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;
    private Integer price;
    private Double discountRate;
    @ManyToOne(fetch = FetchType.LAZY)
    private SubscribeUser subscribeUser;

    // paymentFrequency: 결제 주기 (월별, 분기별, 반기별, 연간) TODO 추가 예정

    // TODO 추후 구독 일시 정지 기능 추가 예정

    // 결제 기간 계산을 위한 메서드
    public void calculatePaymentPeriod() {
        // "endDate - startDate" 로 계산
    }

    // 자동 갱신 기능을 처리하는 메서드
    public void renewSubscription() {
        // "startDate + paymentFrequency + PAUSE" 로 계산
    }

    // 일시 정지 기능을 처리하는 메서드
    public void pauseSubscription() {
        // "status" 를 "PAUSE" 로 변경
    }

}
