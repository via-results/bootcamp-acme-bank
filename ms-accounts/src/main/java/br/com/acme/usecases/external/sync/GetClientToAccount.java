package br.com.acme.usecases.external.sync;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "callingExternalClientAccount", url = "${client.url}")
public interface GetClientToAccount {

    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    ClientResponse clientToAccount(@PathVariable("id") Long id);
}
