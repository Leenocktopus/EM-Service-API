package leandoer.example.service;

import leandoer.example.entity.CustomerRequest;

import java.util.List;

public interface RequestService {

    List<CustomerRequest> getAll();

    void addRequest(CustomerRequest customerRequest);
}
