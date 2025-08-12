package br.com.acme.usecases.external.sync;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "callingExternalClientAccount", url = "http://localhost:8081/api/v1/clients")
public interface GetClientToAccount {

    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    ClientResponse clientToAccount(@PathVariable("id") Long id);
}
