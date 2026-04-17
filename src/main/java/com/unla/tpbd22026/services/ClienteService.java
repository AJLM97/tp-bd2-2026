package com.unla.tpbd22026.services;

import com.unla.tpbd22026.models.Cliente;
import com.unla.tpbd22026.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente crearNuevoCliente(Cliente cliente) {
        return repository.save(cliente);
    }

    public Cliente buscarPorId(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Cliente actualizarCliente(String id, Cliente clienteActualizado) {
        if (repository.existsById(id)) {
            clienteActualizado.setId(id);
            return repository.save(clienteActualizado);
        }
        return null;
    }

    public void eliminarCliente(String id) {
        repository.deleteById(id);
    }

}
