package com.msa.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommonResponse<T> {
    private T data;
    private LocalDateTime timeStamp;

    public CommonResponse(T data) {
        this.data = data;
        this.timeStamp = LocalDateTime.now();

    }
}
