package com.relatandopapel.ordersservice.service;

import com.relatandopapel.ordersservice.exception.BadOrderException;
import com.relatandopapel.ordersservice.exception.BookNotFoundException;
import com.relatandopapel.ordersservice.repository.model.Order;
import com.relatandopapel.ordersservice.repository.model.OrderItem;
import com.relatandopapel.ordersservice.repository.model.OrderStatus;
import com.relatandopapel.ordersservice.repository.OrderJpaRepository;
import com.relatandopapel.ordersservice.controller.model.CreateOrderRequestDto;
import com.relatandopapel.ordersservice.controller.model.CreateOrderResponseDto;
import com.relatandopapel.ordersservice.controller.model.RequestedBook;
import com.relatandopapel.ordersservice.facade.CatalogueFacade;
import com.relatandopapel.ordersservice.facade.model.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CreateOrdersService {

    private final CatalogueFacade catalogueFacade;
    private final OrderJpaRepository orderJpaRepository;

    @Transactional
    public CreateOrderResponseDto createOrder(CreateOrderRequestDto request) {

        if (request.getBooks() == null || request.getBooks().isEmpty()) {
            throw new BadOrderException("La orden debe contener al menos un libro");
        }

        Map<BookDto, OrderItem> bookOrderItemMap = new HashMap<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (RequestedBook requestedBook : request.getBooks()) {
            BookDto bookData = getBookData(requestedBook);
            OrderItem orderItem = OrderItem.builder()
                    .productId(requestedBook.getBookId())
                    .quantity(requestedBook.getQuantity())
                    .subTotal(getSubTotal(requestedBook, bookData))
                    .build();
            totalAmount = totalAmount.add(orderItem.getSubTotal());
            bookOrderItemMap.put(bookData, orderItem);
        }

        Order order = Order.builder()
                .name(generateOrderName())
                .orderDate(LocalDateTime.now())
                .total(totalAmount)
                .comment(request.getComment())
                .status(OrderStatus.EN_PROCESO)
                .ownerId(request.getOwnerId())
                .orderItems(new ArrayList<>(bookOrderItemMap.values()))
                .build();

        bookOrderItemMap.values().forEach(item -> item.setOrder(order));
        Order savedOrder = orderJpaRepository.save(order);

        for (Map.Entry<BookDto, OrderItem> entry : bookOrderItemMap.entrySet()) {
            BookDto book = entry.getKey();
            OrderItem item = entry.getValue();
            if (book.isPhysical()) {
                int newStock = book.getStock() - item.getQuantity();
                catalogueFacade.updateBookStock(book.getId(), newStock);
            }
        }

        return CreateOrderResponseDto.builder()
                .name(savedOrder.getName())
                .status(savedOrder.getStatus().name())
                .total(savedOrder.getTotal().doubleValue())
                .orderDate(savedOrder.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    private BookDto getBookData(RequestedBook requestedBook) {
        if (requestedBook.getQuantity() == null || requestedBook.getQuantity() <= 0) {
            throw new BadOrderException(
                    "La cantidad debe ser mayor a 0 para el libro con ID: " + requestedBook.getBookId());
        }
        return catalogueFacade.getBook(requestedBook.getBookId());
    }

    private BigDecimal getSubTotal(RequestedBook requestedBook, BookDto book) {
        if (book == null) {
            throw new BookNotFoundException(
                    "Libro no encontrado con ID: " + requestedBook.getBookId());
        }
        if (Boolean.FALSE.equals(book.getVisible())) {
            throw new BadOrderException(
                    "El libro '" + book.getTitle() + "' (ID " + requestedBook.getBookId()
                            + ") no está disponible para compra.");
        }
        if (book.isPhysical()) {
            if (book.getStock() == null || book.getStock() < requestedBook.getQuantity()) {
                throw new BadOrderException(
                        "Stock insuficiente para '" + book.getTitle() + "'. "
                                + "Disponible: " + (book.getStock() != null ? book.getStock() : 0)
                                + ", solicitado: " + requestedBook.getQuantity());
            }
        }
        BigDecimal unitPrice = book.getPrice() != null ? book.getPrice() : BigDecimal.ZERO;
        return unitPrice.multiply(BigDecimal.valueOf(requestedBook.getQuantity()));
    }

    private String generateOrderName() {
        return "ORDER-" + System.currentTimeMillis();
    }
}