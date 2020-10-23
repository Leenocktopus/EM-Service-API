package com.leandoer.controller;

import com.leandoer.assembler.OrderAssembler;
import com.leandoer.entity.model.OrderModel;
import com.leandoer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    OrderService orderService;
    OrderAssembler assembler;

    @Autowired
    public OrderController(OrderService orderService, OrderAssembler assembler) {
        this.orderService = orderService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<RepresentationModel<OrderModel>> getAllOrders(@PageableDefault Pageable pageable,
                                                                         PagedResourcesAssembler<OrderModel> pagedResourcesAssembler,
                                                                         @RequestParam(value = "search", required = false) String searchString) {

        return assembler.toCollectionModel(orderService.getAllOrders(pageable, searchString), pagedResourcesAssembler);
    }

    @PostMapping
    public RepresentationModel<OrderModel> add(@RequestBody OrderModel order) {
        return assembler.toModel(orderService.addOrder(order));
    }

    @GetMapping("/{id}")
    public RepresentationModel<OrderModel> getOneOrder(@PathVariable("id") Long id) {
        return assembler.toModel(orderService.getOneOrder(id));
    }

    @PutMapping("/{id}")
    public RepresentationModel<OrderModel> modifyOrder(@PathVariable("id") Long id, @RequestBody OrderModel order) {
        return assembler.toModel(orderService.modifyOrder(id, order));
    }

    @DeleteMapping("/{id}")
    public RepresentationModel<OrderModel> deleteOrder(@PathVariable("id") Long id) {
        return assembler.toModel(orderService.deleteOrder(id));
    }
}
