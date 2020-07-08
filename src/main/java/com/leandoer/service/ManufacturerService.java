package com.leandoer.service;

import com.leandoer.entity.Manufacturer;

import java.util.List;

public interface ManufacturerService {

    List<Manufacturer> getAll();

    List<Manufacturer> addManufacturer(Manufacturer manufacturer);
}
