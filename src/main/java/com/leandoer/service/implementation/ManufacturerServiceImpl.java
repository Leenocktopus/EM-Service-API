package com.leandoer.service.implementation;

import com.leandoer.entity.Manufacturer;
import com.leandoer.entity.model.ManufacturerModel;
import com.leandoer.exception.EntityConflictException;
import com.leandoer.exception.EntityNotFoundException;
import com.leandoer.repository.ManufacturerRepository;
import com.leandoer.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }


    @Override
    public List<ManufacturerModel> getAllManufacturers() {
        return manufacturerRepository.findAll().stream().map(ManufacturerModel::new).collect(Collectors.toList());
    }

    @Override
    public ManufacturerModel addManufacturer(ManufacturerModel manufacturer) {
        checkForDuplicate(manufacturer);
        return new ManufacturerModel(manufacturerRepository.save(manufacturer.toManufacturer()));
    }

    @Override
    public ManufacturerModel getOneManufacturer(long id) {
        return new ManufacturerModel(manufacturerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Manufacturer with id '" + id + "' has not been found")));
    }

    @Override
    public ManufacturerModel modifyManufacturer(long id, ManufacturerModel manufacturer) {
        checkForDuplicate(manufacturer);
        manufacturer.setId(id);
        return new ManufacturerModel(manufacturerRepository.save(manufacturer.toManufacturer()));
    }

    @Override
    public ManufacturerModel deleteManufacturer(long id) {
        Manufacturer selected = manufacturerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Manufacturer with id '" + id + "' has not been found"));
        manufacturerRepository.delete(selected);
        return new ManufacturerModel(selected);
    }

    private void checkForDuplicate(ManufacturerModel manufacturer) {
        String name = manufacturer.getName();
        if (manufacturerRepository.existsByName(name)) {
            throw new EntityConflictException("Manufacturer with name '" + name + "' already exists");
        }
    }
}
