package com.msa.domain;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PAYMENT_WAITING,
    PAYMENT_COMPLETED,
    CANCELED
}
