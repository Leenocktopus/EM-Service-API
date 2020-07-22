package com.leandoer.service.implementation;

import com.leandoer.entity.Manufacturer;
import com.leandoer.entity.model.ManufacturerModel;
import com.leandoer.exception.EntityConflictException;
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
        manufacturerRepository.save(manufacturer.toManufacturer());
        return new ManufacturerModel(manufacturerRepository.findByName(manufacturer.getName())
                .orElseThrow(RuntimeException::new));
    }

    @Override
    public ManufacturerModel getOneManufacturer(long id) {
        return new ManufacturerModel(manufacturerRepository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

    @Override
    public ManufacturerModel modifyManufacturer(long id, ManufacturerModel manufacturer) {
        checkForDuplicate(manufacturer);
        Manufacturer selected = manufacturerRepository.findById(id).orElse(new Manufacturer());
        selected.setName(manufacturer.getName());
        manufacturerRepository.save(selected);
        return new ManufacturerModel(selected);
    }

    @Override
    public ManufacturerModel deleteManufacturer(long id) {
        Manufacturer selected = manufacturerRepository.findById(id).orElseThrow(RuntimeException::new);
        manufacturerRepository.delete(selected);
        return new ManufacturerModel(selected);
    }

    private void checkForDuplicate(ManufacturerModel manufacturer) {
        String name = manufacturer.getName();
        if (manufacturerRepository.existsByName(name)) {
            throw new EntityConflictException("Manufacturer with name \" " + name + "\" already exists");
        }
    }
}
