package com.unla.tpbd22026.util;

import com.unla.tpbd22026.models.*;
import com.unla.tpbd22026.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class GeneradorDataSeed implements CommandLineRunner {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public void run(String... args) throws Exception {
        ventaRepository.deleteAll();

        System.out.println("=== Iniciando carga automática de datos desnormalizados ===");

        // Obras Sociales Reales
        ObraSocial osecac = new ObraSocial("OSECAC", 412586);
        ObraSocial osde = new ObraSocial("OSDE", 112233);
        ObraSocial ioma = new ObraSocial("IOMA", 856941);

        // 10 Clientes con datos y direcciones reales combinadas
        List<Cliente> listaClientes = new ArrayList<>();

        listaClientes.add(crearCliente("Pérez", "Juan Carlos", 30541285L, "Av. Meeks", 412, "Lomas de Zamora", osde));
        listaClientes.add(crearCliente("García", "María Elena", 28654125L, "Boedo", 155, "Lomas de Zamora", osecac));
        listaClientes.add(crearCliente("Rodríguez", "Lucas", 35985412L, "Frias", 1120, "Temperley", ioma));
        listaClientes.add(crearCliente("Fernández", "Laura Inés", 32145874L, "Av. Hipólito Yrigoyen", 9250, "Lanús", osde));
        listaClientes.add(crearCliente("López", "Jorge Omar", 25412589L, "Anatole France", 1840, "Lanús", osecac));
        // Clientes Privados (Sin Obra Social / null)
        listaClientes.add(crearCliente("Martínez", "Sofía", 41258963L, "Calle 25 de Mayo", 341, "Lanús", null));
        listaClientes.add(crearCliente("Gómez", "Diego Armando", 33654128L, "Colón", 124, "Temperley", null));
        listaClientes.add(crearCliente("Díaz", "Camila", 39854125L, "Garibaldi", 455, "Lomas de Zamora", null));
        listaClientes.add(crearCliente("Álvarez", "Norberto", 18451258L, "Brandsen", 202, "Remedios de Escalada", null));
        listaClientes.add(crearCliente("Romero", "Beatriz", 22654128L, "Beltrán", 89, "Remedios de Escalada", null));

        // 10 Productos Reales (7 Medicamentos, 3 Perfumería) con precios reales de mercado estimado
        List<Producto> listaProductos = new ArrayList<>();
        listaProductos.add(new Producto("MEDICAMENTO", "Ibuprofeno 600mg x 20 comp", "Bayer", 1001, 2450.0f));
        listaProductos.add(new Producto("MEDICAMENTO", "Amoxicilina 500mg x 16 comp", "Roemmers", 1002, 3800.0f));
        listaProductos.add(new Producto("MEDICAMENTO", "Paracetamol 500mg x 10 comp", "Raffo", 1003, 1200.0f));
        listaProductos.add(new Producto("MEDICAMENTO", "Losartan 500mg x 30 comp", "Bagó", 1004, 4600.0f));
        listaProductos.add(new Producto("MEDICAMENTO", "Aspirineta x 28 comp", "Bayer", 1005, 1550.0f));
        listaProductos.add(new Producto("MEDICAMENTO", "Sertal Compuesto x 10 comp", "Roemmers", 1006, 2100.0f));
        listaProductos.add(new Producto("MEDICAMENTO", "Alikal x 4 sobres", "Glaxo", 1007, 950.0f));
        listaProductos.add(new Producto("PERFUMERIA", "Champú Dove Reconstrucción 400ml", "Unilever", 2001, 3200.0f));
        listaProductos.add(new Producto("PERFUMERIA", "Jabón de Tocador Rexona Original", "Unilever", 2002, 850.0f));
        listaProductos.add(new Producto("PERFUMERIA", "Desodorante Nivea Dry Comfort", "Beiersdorf", 2003, 1900.0f));

        int totalVentas = 0;

        // Estructuración de las 3 Sucursales con distintas direcciones y staff realista
        for (int s = 1; s <= 3; s++) {
            Sucursal sucursalEsquema;
            Empleado encargado = new Empleado();
            Empleado vendedor1 = new Empleado();
            Empleado vendedor2 = new Empleado();

            if (s == 1) {
                Domicilio domSuc1 = new Domicilio("Av. Alsina", 152, "Lomas de Zamora", "Buenos Aires");
                configurarEmpleado(encargado, "Sánchez", "Ricardo", 24158654L, 20241586545L, "Las Heras", 220, "Lomas");
                configurarEmpleado(vendedor1, "Mendoza", "Claudio", 33214589L, 20332145896L, "Loria", 415, "Lomas");
                configurarEmpleado(vendedor2, "Castro", "Viviana", 36541258L, 27365412583L, "Sarmiento", 810, "Temperley");
                sucursalEsquema = new Sucursal(1, domSuc1, encargado, List.of(encargado, vendedor1, vendedor2));
            } else if (s == 2) {
                Domicilio domSuc2 = new Domicilio("25 de Mayo", 182, "Lanús Oeste", "Buenos Aires");
                configurarEmpleado(encargado, "Benítez", "Andrés Marcelo", 22654125L, 20226541256L, "Melo", 1420, "Lanús");
                configurarEmpleado(vendedor1, "Acosta", "Natalia", 34586941L, 27345869412L, "Tejedor", 310, "Lanús");
                configurarEmpleado(vendedor2, "Silva", "Facundo", 38954125L, 20389541253L, "Urquiza", 640, "Remedios de Escalada");
                sucursalEsquema = new Sucursal(2, domSuc2, encargado, List.of(encargado, vendedor1, vendedor2));
            } else {
                Domicilio domSuc3 = new Domicilio("Av. 9 de Julio", 1105, "Lanús Este", "Buenos Aires");
                configurarEmpleado(encargado, "Herrera", "Marta Graciela", 20365412L, 27203654124L, "Basavilbaso", 412, "Lanús");
                configurarEmpleado(vendedor1, "Suárez", "Esteban", 31254128L, 20312541286L, "Margarita Weild", 1320, "Lanús");
                configurarEmpleado(vendedor2, "Vázquez", "Gabriela", 35412586L, 27354125863L, "Oncativo", 950, "Lanús");
                sucursalEsquema = new Sucursal(3, domSuc3, encargado, List.of(encargado, vendedor1, vendedor2));
            }

            // 30 Ventas por Sucursal
            for (int v = 1; v <= 30; v++) {
                Venta venta = new Venta();
                venta.setFecha(LocalDate.now().minusDays(v));
                venta.setNumeroDeTicket(String.format("000%d-%08d", s, v));

                if (v % 3 == 0) venta.setFormaDePago("EFECTIVO");
                else if (v % 3 == 1) venta.setFormaDePago("TARJETA");
                else venta.setFormaDePago("DEBITO");

                venta.setSucursal(sucursalEsquema);
                venta.setCliente(listaClientes.get(v % 10));

                // Rotación de vendedores atendiendo
                venta.setEmpleadoAtendio(v % 2 == 0 ? vendedor1 : vendedor2);
                venta.setEmpleadoCobrador(encargado);

                List<VentaProducto> itemsTicket = new ArrayList<>();

                // Ítem 1 (Dinámico rotativo)
                Producto p1 = listaProductos.get(v % 10);
                itemsTicket.add(new VentaProducto(p1, 1, p1.getPrecio(), p1.getPrecio()));
                float subtotalAcumulado = p1.getPrecio();

                // Ítem 2 (Garantiza el promedio de 1.5 productos por ticket)
                if (v % 2 == 0) {
                    Producto p2 = listaProductos.get((v + 1) % 10);
                    // Agregamos cantidad dinámica entre 1 y 2 unidades
                    int cant = (v % 4 == 0) ? 2 : 1;
                    float sub = p2.getPrecio() * cant;
                    itemsTicket.add(new VentaProducto(p2, cant, p2.getPrecio(), sub));
                    subtotalAcumulado += sub;
                }

                venta.setItems(itemsTicket);
                venta.setTotal(subtotalAcumulado);

                ventaRepository.save(venta);
                totalVentas++;
            }
        }

        System.out.println("=== ¡Carga Completa exitosa! Se insertaron " + totalVentas + " ventas en MongoDB ===");
    }

    // Funciones auxiliares limpias para simplificar la lectura del código
    private Cliente crearCliente(String ap, String nom, long dni, String calle, int num, String loc, ObraSocial os) {
        Cliente c = new Cliente();
        c.setApellido(ap);
        c.setNombre(nom);
        c.setDni(dni);
        c.setDomicilio(new Domicilio(calle, num, loc, "Buenos Aires"));
        c.setObraSocial(os);
        return c;
    }

    private void configurarEmpleado(Empleado emp, String ap, String nom, long dni, long cuit, String calle, int num, String loc) {
        emp.setApellido(ap);
        emp.setNombre(nom);
        emp.setDni(dni);
        emp.setCuit(cuit);
        emp.setDomicilio(new Domicilio(calle, num, loc, "Buenos Aires"));
    }
}
