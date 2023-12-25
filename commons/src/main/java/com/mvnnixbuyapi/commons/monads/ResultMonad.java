package com.mvnnixbuyapi.commons.monads;

import java.util.Optional;

public class ResultMonad<T> {
    private Optional<T> value;
    private Optional<String> error;

    private ResultMonad(T value, String error) {
        this.value = Optional.ofNullable(value);
        this.error = Optional.ofNullable(error);
    }

    public static <U> ResultMonad<U> ok(U value) {
        return new ResultMonad<>(value, null);
    }

    public static <U> ResultMonad<U> error(String error) {
        return new ResultMonad<>(null, error);
    }

    public boolean isError() {
        return error.isPresent();
    }

    public T getValue() {
        return value.get();
    }

    public String getError() {
        return error.get();
    }
}