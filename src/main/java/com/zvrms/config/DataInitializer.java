package com.zvrms.config;

import com.zvrms.entity.District;
import com.zvrms.entity.Shehia;
import com.zvrms.entity.User;
import com.zvrms.enums.Role;
import com.zvrms.repository.DistrictRepository;
import com.zvrms.repository.ShehiaRepository;
import com.zvrms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DistrictRepository districtRepository;
    private final ShehiaRepository shehiaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        createDistrict("Mjini Magharibi");
        createDistrict("Magharibi A");
        createDistrict("Magharibi B");
        createDistrict("Kaskazini A");
        createDistrict("Kaskazini B");
        createDistrict("Kati");
        createDistrict("Kusini");

        loadKatiShehia();

        loadMagharibiBShehia();

        createDirector();

        createSystemOfficer();

    }

    private void createDistrict(String name){

        if(districtRepository.findByName(name).isEmpty()){

            District district = new District();

            district.setName(name);

            districtRepository.save(district);

        }

    }

    private void createDirector(){

        if(!userRepository.existsByUsername("director")){

            User user = new User();

            user.setFullName("System Director");
            user.setUsername("director");
            user.setPassword(passwordEncoder.encode("director123"));
            user.setRole(Role.DIRECTOR);
            user.setActive(true);

            userRepository.save(user);

        }

    }

    private void createSystemOfficer(){

        if(!userRepository.existsByUsername("system")){

            User user = new User();

            user.setFullName("System Officer");
            user.setUsername("system");
            user.setPassword(passwordEncoder.encode("system123"));
            user.setRole(Role.SYSTEM_OFFICER);
            user.setActive(true);

            userRepository.save(user);

        }

    }

    private void loadKatiShehia() {

    District district = districtRepository.findByName("Kati")
            .orElseThrow();

    addShehia("Mchangani", district);
    addShehia("Mchangani/Dongongwe", district);
    addShehia("Tunduni", district);
    addShehia("Kijibwemtu", district);
    addShehia("Mitakawani", district);
    addShehia("Uzini", district);
    addShehia("Mgeni Haji", district);
    addShehia("Koani", district);
    addShehia("Kidimni", district);
    addShehia("Machui", district);
    addShehia("Kiboje Mkwajuni", district);
    addShehia("Kiboje M/Shauri", district);
    addShehia("Ghana", district);
    addShehia("Miwani", district);
    addShehia("Miwani/Manzese", district);
    addShehia("Umbuji", district);
    addShehia("Pagali", district);
    addShehia("Mpapa", district);
    addShehia("Bambi", district);

    }

    private void loadMagharibiBShehia() {

    District district = districtRepository.findByName("Magharibi B")
            .orElseThrow();

    addShehia("Fumba", district);
    addShehia("Bweleo", district);
    addShehia("Dimani", district);
    addShehia("Nyamanzi", district);
    addShehia("Kombeni", district);
    addShehia("Maungani", district);
    addShehia("Uwandani", district);
    addShehia("Kibondeni", district);
    addShehia("Fuoni Kipungani", district);
    addShehia("Fuoni Migombani", district);
    addShehia("Mambosasa", district);
    addShehia("Chunga", district);
    addShehia("Kiembesamaki", district);
    addShehia("Mbweni", district);
    addShehia("Mombasa", district);
    addShehia("Kwamchina", district);
    addShehia("Michungwani", district);
    addShehia("Chukwani", district);
    addShehia("Shakani", district);
    addShehia("Kisauni", district);
    addShehia("Magogoni", district);
    addShehia("Jitimai", district);
    addShehia("Sokoni", district);
    addShehia("Mwanakwerekwe", district);
    addShehia("Mikarafuuni", district);
    addShehia("Melinne", district);
    addShehia("Taveta", district);
    addShehia("Muembe Majogoo", district);
    addShehia("Pangawe", district);
    addShehia("Kinuni", district);
    addShehia("Mnarani", district);
    addShehia("Kijitoupele", district);
    addShehia("Uzi", district);
    addShehia("Tomondo", district);

    }

    private void addShehia(String name,District district){

        boolean exists = shehiaRepository.findByDistrict(district)

                .stream()

                .anyMatch(s->s.getName().equalsIgnoreCase(name));

        if(!exists){

            Shehia shehia = new Shehia();

            shehia.setName(name);

            shehia.setDistrict(district);

            shehiaRepository.save(shehia);

        }

    }

}