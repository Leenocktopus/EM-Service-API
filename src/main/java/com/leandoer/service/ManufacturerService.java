package com.leandoer.service;

import com.leandoer.entity.model.ManufacturerModel;

import java.util.List;

public interface ManufacturerService {

    List<ManufacturerModel> getAllManufacturers();

    ManufacturerModel addManufacturer(ManufacturerModel manufacturer);

    ManufacturerModel getOneManufacturer(long id);

    ManufacturerModel modifyManufacturer(long id, ManufacturerModel manufacturer);

    ManufacturerModel deleteManufacturer(long id);
}
