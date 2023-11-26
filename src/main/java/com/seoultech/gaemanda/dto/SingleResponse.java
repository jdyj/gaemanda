package com.seoultech.gaemanda.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SingleResponse<T> {

    private T data;

    public static <T> SingleResponse<T> from(T t) {
        return new SingleResponse<>(t);
    }

}
