package com.example.roammate.util;

/**
 * A generic class that contains data and status about loading this data.
 * Used as a wrapper for data that is loaded from the network and/or database.
 */
public class Resource<T> {
    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    public final Status status;
    public final T data;
    public final String message;

    private Resource(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(T data) {
        return new Resource<>(Status.LOADING, data, null);
    }
}
