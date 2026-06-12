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
        listaClientes.add(crearCliente("Martínez", "Sofía", 41258963L, "Calle 25 de Mayo", 341, "Lanús", null));
        listaClientes.add(crearCliente("Gómez", "Diego Armando", 33654128L, "Colón", 124, "Temperley", null));
        listaClientes.add(crearCliente("Díaz", "Camila", 39854125L, "Garibaldi", 455, "Lomas de Zamora", null));
        listaClientes.add(crearCliente("Álvarez", "Norberto", 18451258L, "Brandsen", 202, "Remedios de Escalada", null));
        listaClientes.add(crearCliente("Romero", "Beatriz", 22654128L, "Beltrán", 89, "Remedios de Escalada", null));

        // 10 Productos Reales (7 Medicamentos, 3 Perfumería)
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

        int totalVentasGlobales = 0;

        for (int s = 1; s <= 3; s++) {
            Sucursal sucursalEsquema;
            Empleado encargado = new Empleado();
            Empleado vendedor1 = new Empleado();
            Empleado vendedor2 = new Empleado();

            String idSucString = String.valueOf(s);

            if (s == 1) {
                Domicilio domSuc1 = new Domicilio("Av. Alsina", 152, "Lomas de Zamora", "Buenos Aires");
                configurarEmpleado(encargado, "Sánchez", "Ricardo", 24158654L, 20241586545L, "Las Heras", 220, "Lomas", idSucString, osde);
                configurarEmpleado(vendedor1, "Mendoza", "Claudio", 33214589L, 20332145896L, "Loria", 415, "Lomas", idSucString, osecac);
                configurarEmpleado(vendedor2, "Castro", "Viviana", 36541258L, 27365412583L, "Sarmiento", 810, "Temperley", idSucString, null);
                sucursalEsquema = new Sucursal(1, domSuc1, encargado, List.of(encargado, vendedor1, vendedor2));
            } else if (s == 2) {
                Domicilio domSuc2 = new Domicilio("25 de Mayo", 182, "Lanús Oeste", "Buenos Aires");
                configurarEmpleado(encargado, "Benítez", "Andrés Marcelo", 22654125L, 20226541256L, "Melo", 1420, "Lanús", idSucString, osecac);
                configurarEmpleado(vendedor1, "Acosta", "Natalia", 34586941L, 27345869412L, "Tejedor", 310, "Lanús", idSucString, ioma);
                configurarEmpleado(vendedor2, "Silva", "Facundo", 38954125L, 20339541253L, "Urquiza", 640, "Remedios de Escalada", idSucString, null);
                sucursalEsquema = new Sucursal(2, domSuc2, encargado, List.of(encargado, vendedor1, vendedor2));
            } else {
                Domicilio domSuc3 = new Domicilio("Av. 9 de Julio", 1105, "Lanús Este", "Buenos Aires");
                configurarEmpleado(encargado, "Herrera", "Marta Graciela", 20365412L, 27203654124L, "Basavilbaso", 412, "Lanús", idSucString, osde);
                configurarEmpleado(vendedor1, "Suárez", "Esteban", 31254128L, 20312541286L, "Margarita Weild", 1320, "Lanús", idSucString, ioma);
                configurarEmpleado(vendedor2, "Vázquez", "Gabriela", 35412586L, 27354125863L, "Oncativo", 950, "Lanús", idSucString, null);
                sucursalEsquema = new Sucursal(3, domSuc3, encargado, List.of(encargado, vendedor1, vendedor2));
            }

            int limiteVentasSucursal = 25 + ((s - 1) * 5);

            for (int v = 1; v <= limiteVentasSucursal; v++) {
                Venta venta = new Venta();
                venta.setFecha(LocalDate.of(2026, 5, (v % 28) + 1));
                venta.setNumeroDeTicket(String.format("000%d-%08d", s, v));

                if (v % 3 == 0) venta.setFormaDePago("EFECTIVO");
                else if (v % 3 == 1) venta.setFormaDePago("TARJETA");
                else venta.setFormaDePago("DEBITO");

                venta.setSucursal(sucursalEsquema);

                int indiceClienteAsimetrico = (v * v + s * 3) % 10;
                venta.setCliente(listaClientes.get(indiceClienteAsimetrico));

                venta.setEmpleadoAtendio(v % 2 == 0 ? vendedor1 : vendedor2);
                venta.setEmpleadoCobrador(encargado);

                List<VentaProducto> itemsTicket = new ArrayList<>();

                int indiceBaseProducto = (s == 1) ? (v % 5) : ((s == 2) ? 3 + (v % 4) : 6 + (v % 4));
                Producto p1 = listaProductos.get(indiceBaseProducto);

                int cantidadP1 = (s == 3) ? 2 + (v % 3) : 1;
                float subtotalP1 = p1.getPrecio() * cantidadP1;
                itemsTicket.add(new VentaProducto(p1, cantidadP1, p1.getPrecio(), subtotalP1));
                float subtotalAcumulado = subtotalP1;

                if (v % 5 != 0) {
                    int indiceSecundario = (indiceBaseProducto + 3) % 10;
                    Producto p2 = listaProductos.get(indiceSecundario);

                    int cantP2 = 1;
                    float subP2 = p2.getPrecio() * cantP2;
                    itemsTicket.add(new VentaProducto(p2, cantP2, p2.getPrecio(), subP2));
                    subtotalAcumulado += subP2;
                }

                venta.setItems(itemsTicket);
                venta.setTotal(subtotalAcumulado);

                ventaRepository.save(venta);
                totalVentasGlobales++;
            }
        }
        System.out.println("=== ¡Carga Completa exitosa! Se insertaron " + totalVentasGlobales + " ventas asimétricas ===");
    }

    private Cliente crearCliente(String ap, String nom, long dni, String calle, int num, String loc, ObraSocial os) {
        Cliente c = new Cliente();
        c.setApellido(ap);
        c.setNombre(nom);
        c.setDni(dni);
        c.setDomicilio(new Domicilio(calle, num, loc, "Buenos Aires"));
        c.setObraSocial(os);
        return c;
    }

    private void configurarEmpleado(Empleado emp, String ap, String nom, long dni, long cuit,
                                    String calle, int num, String loc, String idSucursal, ObraSocial os) {
        emp.setApellido(ap);
        emp.setNombre(nom);
        emp.setDni(dni);
        emp.setCuit(cuit);
        emp.setIdSucursal(idSucursal);
        emp.setObraSocial(os);
        emp.setDomicilio(new Domicilio(calle, num, loc, "Buenos Aires"));
    }
}
