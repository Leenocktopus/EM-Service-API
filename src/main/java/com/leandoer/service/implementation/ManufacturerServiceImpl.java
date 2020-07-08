package com.leandoer.service.implementation;

import com.leandoer.service.ManufacturerService;
import com.leandoer.entity.Manufacturer;
import com.leandoer.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> getAll() {
        return manufacturerRepository.findAll();
    }

    @Override
    public List<Manufacturer> addManufacturer(Manufacturer manufacturer) {
        if (manufacturerRepository.existsById(manufacturer)) {
            throw new RuntimeException("manufacturer");
        }
        manufacturerRepository.save(manufacturer);
        return manufacturerRepository.findAll();
    }
}
