package com.desafio.dio.api.service.impl;

import com.desafio.dio.api.model.Address;
import com.desafio.dio.api.model.AddressRepository;
import com.desafio.dio.api.model.Client;
import com.desafio.dio.api.model.ClientRepository;
import com.desafio.dio.api.service.ClientService;
import com.desafio.dio.api.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.get();
    }

    @Override
    public void insert(Client client) {
        saveClientWithCep(client);

    }

    @Override
    public void update(Long id, Client client) {
        Optional<Client> clientDatabase = clientRepository.findById(id);
        if(clientDatabase.isPresent()) {
            saveClientWithCep(client);
        }

    }

    private void saveClientWithCep(Client client) {
        String cep = client.getAddress().getCep();
        Address address = addressRepository.findById(cep).orElseGet(() -> {
            Address newAddress = viaCepService.consultaCep(cep);
            addressRepository.save(newAddress);
            return newAddress;
        });
        client.setAddress(address);
        clientRepository.save(client);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}
