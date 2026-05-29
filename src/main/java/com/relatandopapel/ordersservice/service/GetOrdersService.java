package com.relatandopapel.ordersservice.service;

import com.relatandopapel.ordersservice.controller.model.GetOrdersResponseDto;
import com.relatandopapel.ordersservice.controller.model.PurchasedBook;
import com.relatandopapel.ordersservice.controller.model.RecentOrder;
import com.relatandopapel.ordersservice.facade.CatalogueFacade;
import com.relatandopapel.ordersservice.facade.model.BookDto;
import com.relatandopapel.ordersservice.repository.OrderJpaRepository;
import com.relatandopapel.ordersservice.repository.model.Order;
import com.relatandopapel.ordersservice.repository.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetOrdersService {

    private final OrderJpaRepository orderJpaRepository;
    private final CatalogueFacade catalogueFacade;


    @Transactional(readOnly = true)
    public GetOrdersResponseDto getRecentOrders(Integer ownerId) {

        List<Order> orders = orderJpaRepository.findByOwnerIdOrderByOrderDateDesc(ownerId);

        List<RecentOrder> recentOrders = orders.stream()
                .map(this::toRecentOrder)
                .toList();

        return GetOrdersResponseDto.builder()
                .orders(recentOrders)
                .build();
    }


    private RecentOrder toRecentOrder(Order order) {

        List<PurchasedBook> books = order.getOrderItems().stream()
                .map(this::toPurchasedBook)
                .toList();

        return RecentOrder.builder()
                .name(order.getName())
                .status(order.getStatus().name())
                .total(order.getTotal().doubleValue())
                .orderDate(order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .comment(order.getComment())
                .books(books)
                .build();
    }


    private PurchasedBook toPurchasedBook(OrderItem item) {

        String title = "Libro #" + item.getProductId();  // fallback por defecto
        String author = "Desconocido";

        try {
            BookDto book = catalogueFacade.getBook(item.getProductId());
            if (book != null) {
                title = book.getTitle();
                author = book.getAuthor();
            }
        } catch (Exception e) {
        }

        return PurchasedBook.builder()
                .title(title)
                .author(author)
                .quantity(item.getQuantity())
                .subTotal(item.getSubTotal().doubleValue())
                .build();
    }
}