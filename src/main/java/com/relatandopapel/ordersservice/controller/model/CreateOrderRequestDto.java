package com.relatandopapel.ordersservice.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({ "owner_id", "comment", "books" })

public class CreateOrderRequestDto {

    @Serial
    private final static long serialVersionUID = 7686450847709803303L;

    @JsonProperty("owner_id")
    private Integer ownerId;

    @JsonProperty("comment")
    private String comment;

    @Valid
    @NotEmpty(message = "La orden debe contener al menos un libro")
    @JsonProperty("books")
    private List<RequestedBook> books;
}
