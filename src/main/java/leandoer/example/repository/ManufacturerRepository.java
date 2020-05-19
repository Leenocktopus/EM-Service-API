package leandoer.example.repository;

import leandoer.example.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    boolean existsById(Manufacturer manufacturer);
}
