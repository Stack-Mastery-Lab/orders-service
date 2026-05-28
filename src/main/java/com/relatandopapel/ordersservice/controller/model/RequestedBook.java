package com.relatandopapel.ordersservice.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({ "book_id", "quantity" })

public class RequestedBook {
    private static final long serialVersionUID = 1L;
    @JsonProperty("book_id")
    private Integer bookId;

    @JsonProperty("quantity")
    private Integer quantity;
}
