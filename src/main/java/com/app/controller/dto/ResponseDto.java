package com.app.controller.dto;

/**
 * A generic DTO class used to represent a response, containing either a data payload or an error message.
 * It allows the flexible handling of both successful data responses and error responses.
 *
 * @param <T> the type of the data object contained in the response
 */
public record ResponseDto<T>(
        /**
         * The data object that is returned in a successful response.
         * This field may be {@code null} if there is an error response.
         */
        T data,

        /**
         * The error message in case of failure.
         * This field may be {@code null} if the response contains valid data.
         */
        String error
) {

    /**
     * Constructs a {@code ResponseDto} with the provided data and {@code null} as the error message.
     *
     * @param data the data to be returned in the response
     */
    public ResponseDto(T data){
        this(data, null);
    }

    /**
     * Constructs a {@code ResponseDto} with the provided error message and {@code null} as the data.
     *
     * @param error the error message to be returned in the response
     */
    public ResponseDto(String error){
        this(null, error);
    }
}
