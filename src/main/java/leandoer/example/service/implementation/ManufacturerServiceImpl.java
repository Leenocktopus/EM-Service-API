package leandoer.example.service.implementation;

import leandoer.example.entity.Manufacturer;
import leandoer.example.exception.DuplicateSaveException;
import leandoer.example.repository.ManufacturerRepository;
import leandoer.example.service.ManufacturerService;
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
            throw new DuplicateSaveException("manufacturer");
        }
        manufacturerRepository.save(manufacturer);
        return manufacturerRepository.findAll();
    }
}
