package com.leandoer.service;

import com.leandoer.entity.dto.ManufacturerDto;

import java.util.List;

public interface ManufacturerService {

    List<ManufacturerDto> getAllManufacturers();

    ManufacturerDto addManufacturer(ManufacturerDto manufacturer);

    ManufacturerDto getOneManufacturer(long id);

    ManufacturerDto modifyManufacturer(long id, ManufacturerDto manufacturer);

    ManufacturerDto deleteManufacturer(long id);
}
