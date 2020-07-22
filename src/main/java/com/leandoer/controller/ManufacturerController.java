package com.leandoer.controller;

import com.leandoer.assembler.ManufacturerAssembler;
import com.leandoer.entity.model.ManufacturerModel;
import com.leandoer.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/manufacturers")
public class ManufacturerController {

    ManufacturerService manufacturerService;
    ManufacturerAssembler assembler;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService, ManufacturerAssembler assembler) {
        this.manufacturerService = manufacturerService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<RepresentationModel<ManufacturerModel>> getAllManufacturers() {
        return assembler.toCollectionModel(manufacturerService.getAllManufacturers());
    }

    @PostMapping
    public RepresentationModel<ManufacturerModel> addManufacturer(@RequestBody ManufacturerModel manufacturer) {
        return assembler.toModel(manufacturerService.addManufacturer(manufacturer));
    }

    @GetMapping("/{id}")
    public RepresentationModel<ManufacturerModel> getOneManufacturer(@PathVariable("id") long id) {
        return assembler.toModel(manufacturerService.getOneManufacturer(id));
    }

    @PutMapping("/{id}")
    public RepresentationModel<ManufacturerModel> modifyManufacturer(@PathVariable("id") long id, @RequestBody ManufacturerModel manufacturer) {
        return assembler.toModel(manufacturerService.modifyManufacturer(id, manufacturer));
    }

    @DeleteMapping("/{id}")
    public RepresentationModel<ManufacturerModel> deleteManufacturer(@PathVariable("id") long id) {
        return assembler.toModel(manufacturerService.deleteManufacturer(id));
    }
}
