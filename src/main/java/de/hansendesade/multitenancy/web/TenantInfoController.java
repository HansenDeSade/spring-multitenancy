package de.hansendesade.multitenancy.web;

import de.hansendesade.multitenancy.model.TenantInfo;
import de.hansendesade.multitenancy.repositories.TenantInfoRepository;
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
@RequestMapping("/tenantinfo")
public class TenantInfoController {

    @Autowired
    private TenantInfoRepository repo;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<?> getAll() {
        Iterable<TenantInfo> allSystems = repo.findAll();
        String result = StreamSupport.stream(allSystems.spliterator(), false).map(es -> es.getId() + "-" + es.getValue()).collect(Collectors.joining(", "));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
