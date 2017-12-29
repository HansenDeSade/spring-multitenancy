package de.evelopment.multitenancy.web;

import de.evelopment.multitenancy.model.ExternalSystem;
import de.evelopment.multitenancy.repositories.ExternalSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/externalsystem")
public class ExternalSystemController {

    @Autowired
    private ExternalSystemRepository repo;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<?> getAll() {
        Iterable<ExternalSystem> allSystems = repo.findAll();
        String result = StreamSupport.stream(allSystems.spliterator(), false).map(es -> es.getExternalSystemId() + "-" + es.getName()).collect(Collectors.joining(", "));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
