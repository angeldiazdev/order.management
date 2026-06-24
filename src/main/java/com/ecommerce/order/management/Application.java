package com.ecommerce.order.management;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.ecommerce.order.management.domain.model.Address;
import com.ecommerce.order.management.domain.model.Client;
import com.ecommerce.order.management.domain.repository.ClientRepository;

@SpringBootApplication
@EnableFeignClients
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(ClientRepository clientRepository) {
        return args -> {
            System.out.println("Executing database seeding...");

            List<Client> mockClients = List.of(
                Client.builder().userId("75c97531-abf5-4524-8107-90aa48d08efc").firstName("Barry").paternalLastName("Allen").maternalLastName("Perez").email("john.doe@example.com").address(Address.builder().shippingAddress("CDMX").build()).orders(List.of()).build(),
                Client.builder().userId("aa2ae8bf-6b32-45dc-bb69-14e899bd8fed").firstName("Bruce").paternalLastName("Wayne").maternalLastName("Dominguez").email("bruce.wayne@example.com").address(Address.builder().shippingAddress("Toluca").build()).orders(List.of()).build(),
                Client.builder().userId("b446eb39-84fc-46e2-923b-91771d806c38").firstName("Peter").paternalLastName("Parker").maternalLastName("Hernandez").email("peter.parker@example.com").address(Address.builder().shippingAddress("Puebla").build()).orders(List.of()).build(),
                Client.builder().userId("93a5c099-3bbe-4718-8a0a-2e1bae0e08c8").firstName("Tony").paternalLastName("Stark").maternalLastName("Diaz").email("tony.stark@example.com").address(Address.builder().shippingAddress("Puebla").build()).orders(List.of()).build(),
                Client.builder().userId("f6ec8262-7017-4532-a382-70c86e7844fc").firstName("Clark").paternalLastName("Kent").maternalLastName("Espinoza").email("clark.kent@example.com").address(Address.builder().shippingAddress("Puebla").build()).orders(List.of()).build(),
                Client.builder().userId("12665a1b2c3d4e5f6a7b8c9d0e").firstName("Diana").paternalLastName("Prince").maternalLastName("Garcia").email("diana.prince@example.com").address(Address.builder().shippingAddress("Monterrey").build()).orders(List.of()).build()
            );

            mockClients.forEach(client -> {
                if (clientRepository.findById(client.getUserId()).isEmpty()) {
                    clientRepository.save(client);
                    System.out.println("Client seeded successfully: " + client.getUserId());
                }
            });

            System.out.println("Database initialization completed.");
        };
    }
}