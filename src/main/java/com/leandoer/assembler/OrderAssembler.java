package com.leandoer.assembler;

import com.leandoer.controller.MainController;
import com.leandoer.controller.OrderController;
import com.leandoer.entity.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderAssembler implements RepresentationModelAssembler<OrderModel, RepresentationModel<OrderModel>> {
    @Override
    public RepresentationModel<OrderModel> toModel(OrderModel order) {
        order.add(
                linkTo(methodOn(OrderController.class).getOneOrder(order.getId())).withSelfRel()
        );
        return order;
    }


    public CollectionModel<RepresentationModel<OrderModel>> toCollectionModel(Page<OrderModel> orders,
                                                                              PagedResourcesAssembler<OrderModel> pagedResourcesAssembler) {
        return pagedResourcesAssembler.toModel(orders, this).add(
                linkTo(methodOn(MainController.class).root()).withRel("root")
        );
    }
}
