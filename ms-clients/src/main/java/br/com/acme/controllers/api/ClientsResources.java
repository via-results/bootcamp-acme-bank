package br.com.acme.controllers.api;

import br.com.acme.controllers.requests.ClientRequest;
import br.com.acme.controllers.response.ClientResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/clients")
public interface ClientsResources {

    @PostMapping
     ClientResponse create(@RequestBody ClientRequest request);

    @GetMapping
    List<ClientResponse> list();

    @GetMapping("/{id}")
    ClientResponse getClient(@PathVariable("id") Long id);

    @GetMapping("/email/{email}")
    ClientResponse getClientEmail(@PathVariable("email") String email);

    @GetMapping("/document/{document}")
    ClientResponse getClientDocument(@PathVariable("document") String document);

    @DeleteMapping("/{id}")
    String deleteClient(@PathVariable("id") Long id);
}
