package com.leandoer.service;

import com.leandoer.entity.Category;
import com.leandoer.entity.Manufacturer;
import com.leandoer.entity.dto.CategoryDto;
import com.leandoer.entity.dto.ManufacturerDto;

import java.util.List;

public interface ManufacturerService {

    List<ManufacturerDto> getAllManufacturers();

    ManufacturerDto addManufacturer(ManufacturerDto manufacturer);

    ManufacturerDto getOneManufacturer(long id);

    ManufacturerDto modifyManufacturer(long id, ManufacturerDto manufacturer);

    ManufacturerDto deleteManufacturer(long id);
}
