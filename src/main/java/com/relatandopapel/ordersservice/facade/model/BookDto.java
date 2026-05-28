package com.relatandopapel.ordersservice.facade.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "title", "author", "isbn", "publication_date",
        "category", "rating", "visible", "price", "book_type", "stock" })
public class BookDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("publication_date")
    private String publicationDate;

    @JsonProperty("category")
    private String category;

    @JsonProperty("rating")
    private Double rating;

    @JsonProperty("visible")
    private Boolean visible;

    @JsonProperty("price")
    private BigDecimal price;

    // "DIGITAL" o "PHYSICAL" — lo devuelve catalogue-service
    @JsonProperty("book_type")
    private String bookType;

    // Solo relevante si bookType = "PHYSICAL"
    @JsonProperty("stock")
    private Integer stock;

    public boolean isPhysical() {
        return "PHYSICAL".equalsIgnoreCase(bookType);
    }
}
