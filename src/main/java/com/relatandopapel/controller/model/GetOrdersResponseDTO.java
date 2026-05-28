package com.relatandopapel.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serial;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({ "orders" })
public class GetOrdersResponseDTO {

    @Serial
    private final static long serialVersionUID = -8949888676747079614L;

    @JsonProperty("orders")
    private List<RecentOrder> orders;
}
