package com.leandoer.controller;

import com.leandoer.entity.dto.ManufacturerDto;
import com.leandoer.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/manufacturers")
public class ManufacturerController {

    ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public List<ManufacturerDto> getAllManufacturers() {
        return manufacturerService.getAllManufacturers();
    }

    @PostMapping
    public ManufacturerDto addManufacturer(@RequestBody ManufacturerDto manufacturer) {
        return manufacturerService.addManufacturer(manufacturer);
    }

    @GetMapping("/{id}")
    public ManufacturerDto getOneManufacturer(@PathVariable("id") long id) {
        return manufacturerService.getOneManufacturer(id);
    }

    @PutMapping("/{id}")
    public ManufacturerDto modifyManufacturer(@PathVariable("id") long id, @RequestBody ManufacturerDto manufacturer) {
        return manufacturerService.modifyManufacturer(id, manufacturer);
    }

    @DeleteMapping("/{id}")
    public ManufacturerDto deleteManufacturer(@PathVariable("id") long id) {
        return manufacturerService.deleteManufacturer(id);
    }


}
