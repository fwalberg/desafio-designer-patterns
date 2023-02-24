package com.desafio.dio.api.service;

import com.desafio.dio.api.model.Client;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
@Repository
public interface ClientService {
    Iterable<Client> findAll();

    Client findById(Long id);

    void insert(Client client);

    void update(Long id, Client client);

    void delete(Long id);
}
