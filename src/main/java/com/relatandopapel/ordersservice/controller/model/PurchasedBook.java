package com.relatandopapel.ordersservice.controller.model;

import java.io.Serial;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({ "title", "author", "quantity", "sub_total" })

public class PurchasedBook {

    @Serial
    private static final long serialVersionUID = 4761762119375139021L;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("sub_total")
    private Double subTotal;
}