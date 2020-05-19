package leandoer.example.service.implementation;

import leandoer.example.entity.CustomerRequest;
import leandoer.example.repository.RequestRepository;
import leandoer.example.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public List<CustomerRequest> getAll() {
        return requestRepository.findAll();
    }

    @Override
    public void addRequest(CustomerRequest customerRequest) {
        requestRepository.save(customerRequest);
    }
}
