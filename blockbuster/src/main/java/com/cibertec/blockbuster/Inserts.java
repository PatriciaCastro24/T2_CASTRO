package com.cibertec.blockbuster;

import com.cibertec.blockbuster.model.Clientes;
import com.cibertec.blockbuster.model.Peliculas;
import com.cibertec.blockbuster.model.Alquileres;
import com.cibertec.blockbuster.model.DetalleAlquiler;
import com.cibertec.blockbuster.repository.ClientesRepository;
import com.cibertec.blockbuster.repository.PeliculasRepository;
import com.cibertec.blockbuster.repository.AlquilerRepository;
import com.cibertec.blockbuster.repository.DetalleAlquilerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;

@Component
public class Inserts implements CommandLineRunner {

    private final ClientesRepository clientesRepo;
    private final PeliculasRepository peliculasRepo;
    private final AlquilerRepository alquileresRepo;
    private final DetalleAlquilerRepository detalleRepo;

    public Inserts(ClientesRepository clientesRepo, PeliculasRepository peliculasRepo,
                       AlquilerRepository alquileresRepo, DetalleAlquilerRepository detalleRepo) {
        this.clientesRepo = clientesRepo;
        this.peliculasRepo = peliculasRepo;
        this.alquileresRepo = alquileresRepo;
        this.detalleRepo = detalleRepo;
    }

    private void pausar(Scanner sc) {
        System.out.print("Presiona ENTER para continuar...");
        sc.nextLine();
    }

    @Override
    public void run(String... args) {
        try (Scanner sc = new Scanner(System.in)) {
            
            if (clientesRepo.count() == 0) {
                Clientes c1 = clientesRepo.save(new Clientes("María", "maria@correo.com"));
                Clientes c2 = clientesRepo.save(new Clientes("Andrés", "andres@correo.com"));
                Clientes c3 = clientesRepo.save(new Clientes("Sofía", "sofia@correo.com"));
                System.out.println("\nClientes creados:");
                System.out.println(c1);
                System.out.println(c2);
                System.out.println(c3);

                pausar(sc);

                Peliculas p1 = peliculasRepo.save(new Peliculas("Interestelar", "Ciencia Ficción", 8, 18.5));
                Peliculas p2 = peliculasRepo.save(new Peliculas("La La Land", "Musical", 4, 12.0));
                Peliculas p3 = peliculasRepo.save(new Peliculas("El Padrino", "Drama", 6, 25.0));
                System.out.println("\nPelículas creadas:");
                System.out.println(p1);
                System.out.println(p2);
                System.out.println(p3);

                pausar(sc);

                System.out.println("\nBuscando película con id " + p1.getId_pelicula());
                Peliculas peliBuscada = peliculasRepo.findById(p1.getId_pelicula()).orElse(null);
                System.out.println("Encontrada: " + peliBuscada);

                if (peliBuscada != null) {
                    peliBuscada.setStock(10);
                    peliculasRepo.save(peliBuscada);
                    System.out.println("\nStock actualizado: " + peliBuscada);
                }

                pausar(sc);

                Alquileres a1 = alquileresRepo.save(new Alquileres(LocalDate.now(), c1, 25.0, Alquileres.EstadoAlquiler.Activo));
                Alquileres a2 = alquileresRepo.save(new Alquileres(LocalDate.now(), c3, 40.0, Alquileres.EstadoAlquiler.Activo));
                Alquileres a3 = alquileresRepo.save(new Alquileres(LocalDate.now(), c1, 15.0, Alquileres.EstadoAlquiler.Retrasado));
                System.out.println("\nAlquileres creados:");
                System.out.println(a1);
                System.out.println(a2);
                System.out.println(a3);

                pausar(sc);

                DetalleAlquiler d1 = detalleRepo.save(new DetalleAlquiler(a1, p1, 2));
                DetalleAlquiler d2 = detalleRepo.save(new DetalleAlquiler(a2, p3, 1));
                DetalleAlquiler d3 = detalleRepo.save(new DetalleAlquiler(a3, p2, 1));
                System.out.println("\nDetalles de alquiler creados:");
                System.out.println(d1);
                System.out.println(d2);
                System.out.println(d3);

                pausar(sc);

                System.out.println("\n>>> DEMO FINALIZADA <<<");
            } else {
                System.out.println("Los datos ya existen. No se insertaron duplicados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}