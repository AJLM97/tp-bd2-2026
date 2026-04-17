package com.unla.tpbd22026.controllers;

import com.unla.tpbd22026.models.Cliente;
import com.unla.tpbd22026.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/")
    public Cliente create(@RequestBody Cliente cliente) {
        return clienteService.crearNuevoCliente(cliente);
    }

    @GetMapping("/{id}")
    public Cliente getOne(@PathVariable String id) {
        return clienteService.buscarPorId(id);
    }

    @GetMapping
    public List<Cliente> getAll() {
        return clienteService.listarTodos();
    }

    @PutMapping("/{id}")
    public Cliente update(@PathVariable String id, @RequestBody Cliente cliente) {
        return clienteService.actualizarCliente(id, cliente);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        clienteService.eliminarCliente(id);
    }

}
