package com.relatandopapel.ordersservice.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({ "name", "status", "total", "order_date", "comment", "books" })
public class RecentOrder {
    private static final long serialVersionUID = 1L;
    @JsonProperty("name")
    private String name;

    @JsonProperty("status")
    private String status;

    @JsonProperty("total")
    private Double total;

    @JsonProperty("order_date")
    private String orderDate;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("books")
    private List<PurchasedBook> books;
}
