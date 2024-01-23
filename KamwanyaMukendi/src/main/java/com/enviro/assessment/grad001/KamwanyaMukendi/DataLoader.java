package com.enviro.assessment.grad001.KamwanyaMukendi;

import com.enviro.assessment.grad001.KamwanyaMukendi.model.Client;
import com.enviro.assessment.grad001.KamwanyaMukendi.model.Product;
import com.enviro.assessment.grad001.KamwanyaMukendi.model.Profile;
import com.enviro.assessment.grad001.KamwanyaMukendi.repositories.ClientRepository;
import com.enviro.assessment.grad001.KamwanyaMukendi.repositories.ProductRepository;
import com.enviro.assessment.grad001.KamwanyaMukendi.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Pre-save test clients with profiles and products during application startup

        // Client 1
        Client client1 = new Client();
        client1.setEmail("client1@example.com");

        // Set a dummy password for testing
        client1.setPassword(passwordEncoder.encode("password"));

        Profile profile1 = new Profile();
        profile1.setName("John Doe");
        profile1.setAge(30);
        profile1.setNumber("06165753429");
        profile1.setAddress("123 Main St");
        profile1.setEmail("client1@example.com");

        Product product1 = new Product();
        product1.setType("Retirement");
        product1.setName("Product1");
        product1.setCurrentBalance(100000.00);

        Product product2 = new Product();
        product2.setType("Saving");
        product2.setName("Product2");
        product2.setCurrentBalance(200000.00);

        profile1.getProducts().add(product1);
        profile1.getProducts().add(product2);
        product1.setProfile(profile1);
        product2.setProfile(profile1);

        client1.setProfile(profile1);
        profile1.setClient(client1);

        clientRepository.save(client1);

        // Client 2
        Client client2 = new Client();
        client2.setEmail("client2@example.com");

        // Set a dummy password for testing
        client2.setPassword(passwordEncoder.encode("password"));

        Profile profile2 = new Profile();
        profile2.setName("Jane Doe");
        profile2.setAge(65);
        profile2.setNumber("06165753429");
        profile2.setAddress("123 Main St");
        profile2.setEmail("client2@example.com");

        Product product3 = new Product();
        product3.setType("Retirement");
        product3.setName("Product3");
        product3.setCurrentBalance(100000.00);

        Product product4 = new Product();
        product4.setType("Saving");
        product4.setName("Product4");
        product4.setCurrentBalance(200000.00);

        profile2.getProducts().add(product3);
        profile2.getProducts().add(product4);
        product3.setProfile(profile2);
        product4.setProfile(profile2);

        client2.setProfile(profile2);
        profile2.setClient(client2);

        clientRepository.save(client2);
    }
}
