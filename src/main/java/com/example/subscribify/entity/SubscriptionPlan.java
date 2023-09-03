package com.example.subscribify.entity;

import com.example.subscribify.dto.CreateSubscribeDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscriptionPlan {

    @Id
    @GeneratedValue
    private Long id;
    private String subscribeName;
    private Integer duration;
    // TODO Enum 으로 변경
    private String durationUnit; // Month, Year
    private Long price;
    private Double discount;
    // TODO Enum 으로 변경
    private String discountType; // Percent, Fixed, None
    private Long discountedPrice;

    public void update(CreateSubscribeDto createSubscribeDto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
