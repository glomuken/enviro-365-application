package com.enviro.assessment.grad001.KamwanyaMukendi.services;


import com.enviro.assessment.grad001.KamwanyaMukendi.model.Client;
import com.enviro.assessment.grad001.KamwanyaMukendi.model.Profile;
import com.enviro.assessment.grad001.KamwanyaMukendi.UserRegistrationDto;
import com.enviro.assessment.grad001.KamwanyaMukendi.repositories.ClientRepository;
import com.enviro.assessment.grad001.KamwanyaMukendi.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validateLogin(String email, String enteredPassword) {
        Client client = clientRepository.findByEmail(email);
        if (client != null) {
            return passwordEncoder.matches(enteredPassword, client.getPassword());
        }
        return false;
    }



    public void registerUser(UserRegistrationDto userRegistrationDto) {
        Client client = new Client();
        client.setEmail(userRegistrationDto.getEmail());
        client.setPassword(userRegistrationDto.getPassword());

        Profile profile = new Profile();
        profile.setName(userRegistrationDto.getName());
        profile.setAge(userRegistrationDto.getAge());
        profile.setNumber(userRegistrationDto.getNumber());
        profile.setAddress(userRegistrationDto.getAddress());
        profile.setEmail(userRegistrationDto.getEmail());

        client.setProfile(profile);
        profile.setClient(client);

        clientRepository.save(client);
        profileRepository.save(profile);
    }
    public void updateUser(UserRegistrationDto updatedUserDto) {
        Client existingClient = clientRepository.findByEmail(updatedUserDto.getEmail());

        if (existingClient != null) {
            existingClient.getProfile().setNumber(updatedUserDto.getNumber());
            existingClient.getProfile().setAddress(updatedUserDto.getAddress());

            if (updatedUserDto.getPassword() != null && !updatedUserDto.getPassword().isEmpty()) {
                existingClient.setPassword(updatedUserDto.getPassword());
            }

            clientRepository.save(existingClient);
        }
    }

}

