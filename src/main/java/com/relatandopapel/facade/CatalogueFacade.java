package com.relatandopapel.facade;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.relatandopapel.Exception.BookNotFoundException;
import com.relatandopapel.facade.model.BookDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Component
@RequiredArgsConstructor
public class CatalogueFacade {

    private final WebClient.Builder webClientBuilder;

    @Value("${catalogue.url}")
    private String catalogueUrl;

    public BookDto getBook(Integer id) {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri(catalogueUrl + "/books/{id}", id)
                    .retrieve()
                    .bodyToMono(BookDto.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new BookNotFoundException("Libro con ID " + id + " no encontrado");
        } catch (WebClientResponseException.InternalServerError e) {
            throw new RuntimeException(
                    "An exception ocurred with Internal Server Error: " + e.getMessage() + "for book with ID " + id);
        }
    }

}
