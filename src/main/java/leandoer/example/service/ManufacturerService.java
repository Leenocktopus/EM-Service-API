package leandoer.example.service;

import leandoer.example.entity.Manufacturer;

import java.util.List;

public interface ManufacturerService {

    List<Manufacturer> getAll();

    List<Manufacturer> addManufacturer(Manufacturer manufacturer);
}
