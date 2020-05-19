package leandoer.example.controller;


import leandoer.example.entity.CustomerRequest;
import leandoer.example.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RequestController {

    RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/requests")
    List<CustomerRequest> getAll() {
        return requestService.getAll();
    }

    @PostMapping("/requests")
    void addRequest(@RequestBody CustomerRequest customerRequest) {
        requestService.addRequest(customerRequest);
    }
}
