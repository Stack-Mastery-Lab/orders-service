package com.relatandopapel.facade.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "title", "author", "isbn", "publicationDate", "category", "rating", "visible" })
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
}
