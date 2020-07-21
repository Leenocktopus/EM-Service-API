package com.leandoer.service.implementation;

import com.leandoer.entity.Manufacturer;
import com.leandoer.entity.dto.ManufacturerDto;
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
    public List<ManufacturerDto> getAllManufacturers() {
        return manufacturerRepository.findAll().stream().map(ManufacturerDto::new).collect(Collectors.toList());
    }

    @Override
    public ManufacturerDto addManufacturer(ManufacturerDto manufacturer) {
        checkForDuplicate(manufacturer);
        manufacturerRepository.save(manufacturer.toManufacturer());
        return new ManufacturerDto(manufacturerRepository.findByName(manufacturer.getName())
                .orElseThrow(RuntimeException::new));
    }

    @Override
    public ManufacturerDto getOneManufacturer(long id) {
        return new ManufacturerDto(manufacturerRepository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

    @Override
    public ManufacturerDto modifyManufacturer(long id, ManufacturerDto manufacturer) {
        checkForDuplicate(manufacturer);
        Manufacturer selected = manufacturerRepository.findById(id).orElse(new Manufacturer());
        selected.setName(manufacturer.getName());
        manufacturerRepository.save(selected);
        return new ManufacturerDto(selected);
    }

    @Override
    public ManufacturerDto deleteManufacturer(long id) {
        Manufacturer selected = manufacturerRepository.findById(id).orElseThrow(RuntimeException::new);
        manufacturerRepository.delete(selected);
        return new ManufacturerDto(selected);
    }

    private void checkForDuplicate(ManufacturerDto manufacturer) {
        String name = manufacturer.getName();
        if (manufacturerRepository.existsByName(name)) {
            throw new EntityConflictException("Manufacturer with name \" " + name + "\" already exists");
        }
    }
}
