package com.relatandopapel.ordersservice.facade;

import com.relatandopapel.ordersservice.exception.BadOrderException;
import com.relatandopapel.ordersservice.exception.BookNotFoundException;
import com.relatandopapel.ordersservice.exception.InternalErrorException;
import com.relatandopapel.ordersservice.facade.model.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

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
            throw new InternalErrorException(
                    "Error interno al consultar el catálogo para el libro con ID " + id, e);
        }
    }

    public void updateBookStock(Integer bookId, Integer newStock) {
        try {
            webClientBuilder.build()
                    .patch()
                    .uri(catalogueUrl + "/books/{id}", bookId)
                    .bodyValue(Map.of("stock", newStock))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new BookNotFoundException("Libro con ID " + bookId + " no encontrado al actualizar stock");
        } catch (WebClientResponseException.BadRequest e) {
            throw new BadOrderException("Stock inválido para el libro con ID " + bookId);
        } catch (WebClientResponseException.InternalServerError e) {
            throw new InternalErrorException(
                    "Error interno al actualizar el stock del libro con ID " + bookId, e);
        }
    }
}