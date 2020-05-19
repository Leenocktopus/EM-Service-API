package leandoer.example.repository;

import leandoer.example.entity.CustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<CustomerRequest, Integer> {


}
