package com.willrsousa.crudchallenge.services;

import com.willrsousa.crudchallenge.dto.ClientDTO;
import com.willrsousa.crudchallenge.entities.Client;
import com.willrsousa.crudchallenge.repositories.ClientRepository;
import com.willrsousa.crudchallenge.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return new ClientDTO(client);
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable){
        Page<Client> result = clientRepository.findAll(pageable);
        return result.map(x -> new ClientDTO(x));
    }

    @Transactional
    public ClientDTO save(ClientDTO dto){
            Client client = new Client();
            dtoToEntity(dto,client);
            client = clientRepository.save(client);
            return new ClientDTO(client);

    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto){
        try{
        Client client = clientRepository.getReferenceById(id);
        dtoToEntity(dto, client);
        client = clientRepository.save(client);
        return new ClientDTO(client);
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Cliente não encontrado!");
        }
    }

    @Transactional
    public void delete(Long id){
        if(!clientRepository.existsById(id)){
            throw new ResourceNotFoundException("Cliente não encontrado!");
        }
        clientRepository.deleteById(id);
    }

    private void dtoToEntity(ClientDTO dto, Client entity){
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }
}
