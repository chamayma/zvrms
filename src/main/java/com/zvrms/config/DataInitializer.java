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

        // Rename Mjini Magharibi to Mjini if it exists
        districtRepository.findByName("Mjini Magharibi").ifPresent(d -> {
            d.setName("Mjini");
            districtRepository.save(d);
        });

        createDistrict("Mjini");
        createDistrict("Magharibi A");
        createDistrict("Magharibi B");
        createDistrict("Kaskazini A");
        createDistrict("Kaskazini B");
        createDistrict("Kati");
        createDistrict("Kusini");
        createDistrict("Micheweni");
        createDistrict("Mkoani");
        createDistrict("Chake-Chake");
        createDistrict("Wete");

        loadKatiShehia();

        loadMagharibiBShehia();

        loadMjiniShehia();

        loadKaskaziniAShehia();

        loadMagharibiAShehia();

        loadKaskaziniBShehia();

        loadKusiniShehia();

        loadWeteShehia();

        loadChakeChakeShehia();

        loadMicheweniShehia();

        loadMkoaniShehia();

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
    addShehia("Charawe", district);
    addShehia("Ukongoroni", district);
    addShehia("Pongwe", district);
    addShehia("Uroa", district);
    addShehia("Chwaka", district);
    addShehia("Marumbi", district);
    addShehia("Jendele", district);
    addShehia("Ubago", district);
    addShehia("Tunguu", district);
    addShehia("Uzi", district);
    addShehia("Bungi", district);
    addShehia("Cheju", district);
    addShehia("Ndijani Mseweni", district);
    addShehia("Ndijani Mwembepunda", district);

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

    private void loadMjiniShehia() {
        District district = districtRepository.findByName("Mjini")
                .orElseThrow();

        addShehia("Kwamtumwajeni", district);
        addShehia("Nyerere", district);
        addShehia("Magomeni", district);
        addShehia("Meya", district);
        addShehia("Mpendae", district);
        addShehia("Kwabintihamrani", district);
        addShehia("Kilimani", district);
        addShehia("Migombani", district);
        addShehia("Kilimahewa Bondeni", district);
        addShehia("Kilimahewa Juu", district);
        addShehia("Amani", district);
        addShehia("Kwawazee", district);
        addShehia("Banko", district);
        addShehia("Chumbuni", district);
        addShehia("Karakana", district);
        addShehia("Mwembemakumbi", district);
        addShehia("Maruhubi", district);
        addShehia("Masumbani", district);
        addShehia("Kwamtipura", district);
        addShehia("Mapinduzi", district);
        addShehia("Mboriborini", district);
        addShehia("Mkele", district);
        addShehia("Saateni", district);
        addShehia("Shaurimoyo", district);
        addShehia("Kidongochekundu", district);
        addShehia("Matarumbeta", district);
        addShehia("Kwaalinatu", district);
        addShehia("Jang'ombe", district);
        addShehia("Urusi", district);
        addShehia("Kwaalamsha", district);
        addShehia("Kwahani", district);
        addShehia("Mikunguni", district);
        addShehia("Muungano", district);
        addShehia("Sebleni", district);
        addShehia("Kikwajuni Bondeni", district);
        addShehia("Kikwajuni Juu", district);
        addShehia("Kisimamajongoo", district);
        addShehia("Kisiwandui", district);
        addShehia("Mnazimmoja", district);
        addShehia("Miembeni", district);
        addShehia("Mwembeladu", district);
        addShehia("Mwembemadema", district);
        addShehia("Mwembeshauri", district);
        addShehia("Rahaleo", district);
        addShehia("Kiponda", district);
        addShehia("Malindi", district);
        addShehia("Mchangani Mjini", district);
        addShehia("Mkunazini", district);
        addShehia("Shangani", district);
        addShehia("Vikokotoni", district);
        addShehia("Makadara", district);
        addShehia("Gulioni", district);
        addShehia("Mitiulaya", district);
        addShehia("Mlandege", district);
        addShehia("Mwembetanga", district);
    }

    private void loadKaskaziniAShehia() {
        District district = districtRepository.findByName("Kaskazini A")
                .orElseThrow();

        addShehia("Chaani Masingini", district);
        addShehia("Mchenza Shauri", district);
        addShehia("Chaani Kubwa", district);
        addShehia("Donge Vijibweni", district);
        addShehia("Bandamaji", district);
        addShehia("Kikobweni", district);
        addShehia("Kinyasini", district);
        addShehia("Kandwi", district);
        addShehia("Pwani Mchangani", district);
        addShehia("Potoa", district);
        addShehia("Kijini", district);
        addShehia("Matetema", district);
        addShehia("Kinduni", district);
        addShehia("Kigomani", district);
        addShehia("Kwagube", district);
        addShehia("Juga Kuu", district);
        addShehia("Kivunge", district);
        addShehia("Muwange", district);
        addShehia("Pitanazako", district);
        addShehia("Kilombero", district);
        addShehia("Kidombo", district);
        addShehia("Kibeni", district);
        addShehia("Mkwajuni", district);
        addShehia("Pangeni", district);
        addShehia("Chutama", district);
        addShehia("Kiwengwa", district);
        addShehia("Moga", district);
        addShehia("Kiongwe Kidogo", district);
        addShehia("Mafufuni", district);
        addShehia("Gamba", district);
        addShehia("Matemwe Kaskazini", district);
        addShehia("Matemwe Kusini", district);
        addShehia("Makoba", district);
        addShehia("Misufini", district);
        addShehia("Mtakuja", district);
        addShehia("Gomani", district);
        addShehia("Fujoni", district);
        addShehia("Uvivini", district);
        addShehia("Jongowe", district);
        addShehia("Zingwezingwe", district);
        addShehia("Mto wa Pwani", district);
        addShehia("Kiombamvua", district);
        addShehia("Mkadini", district);
        addShehia("Pale", district);
    }

    private void loadMagharibiAShehia() {
        District district = districtRepository.findByName("Magharibi A")
                .orElseThrow();

        addShehia("Kibweni", district);
        addShehia("Kwa Goa", district);
        addShehia("Mwanyanya", district);
        addShehia("Mtoni", district);
        addShehia("Sharifu Msa", district);
        addShehia("Kijichi", district);
        addShehia("Bububu", district);
        addShehia("Chemchem", district);
        addShehia("Mbuzini", district);
        addShehia("Dole", district);
        addShehia("Kizimbani", district);
        addShehia("Chuini", district);
        addShehia("Kihinani", district);
        addShehia("Kikaangoni", district);
        addShehia("Kama", district);
        addShehia("Mfenesini", district);
        addShehia("Mwakaje", district);
        addShehia("Bumbwisudi", district);
        addShehia("Mto Pepo", district);
        addShehia("Munduli", district);
        addShehia("Mtoni Kidatu", district);
        addShehia("Mtoni Chemchem", district);
        addShehia("Kianga", district);
        addShehia("Masingini", district);
        addShehia("Mwera", district);
        addShehia("Muembemchomeke", district);
        addShehia("Mtofaani", district);
        addShehia("Michikichini", district);
        addShehia("Hawaii", district);
        addShehia("Welezo", district);
        addShehia("Uholanzi", district);
    }

    private void loadKaskaziniBShehia() {
        District district = districtRepository.findByName("Kaskazini B")
                .orElseThrow();

        addShehia("Donge Mtambile", district);
        addShehia("Njia ya Mtoni", district);
        addShehia("Donge Vijibweni", district);
        addShehia("Majenzi", district);
        addShehia("Donge Karange", district);
        addShehia("Donge Pwani", district);
        addShehia("Donge Mbiji", district);
        addShehia("Mnyimbi", district);
        addShehia("Mkataleni", district);
        addShehia("Mahonda", district);
        addShehia("Kitope", district);
        addShehia("Mbaleni", district);
        addShehia("Mgambo", district);
        addShehia("Kisongoni", district);
        addShehia("Upenja", district);
        addShehia("Kiwengwa", district);
        addShehia("Kilombero", district);
        addShehia("Pangeni", district);
        addShehia("Misufini", district);
        addShehia("Kiombamvua", district);
        addShehia("Mkadini", district);
        addShehia("Zingwezingwe", district);
        addShehia("Fujoni", district);
        addShehia("Mangapwani", district);
        addShehia("Kidanzini", district);
        addShehia("Makoba", district);
        addShehia("Mafufuni", district);
        addShehia("Kiongwe Kidogo", district);
        addShehia("Kinduni", district);
        addShehia("Matetema", district);
        addShehia("Kwagube", district);
    }

    private void loadKusiniShehia() {
        District district = districtRepository.findByName("Kusini")
                .orElseThrow();

        addShehia("Dongwe", district);
        addShehia("Jambiani", district);
        addShehia("Jambiani Kikadini", district);
        addShehia("Muyuni 'A'", district);
        addShehia("Muyuni 'B'", district);
        addShehia("Muyuni 'C'", district);
        addShehia("Kizimkazi Dimbani", district);
        addShehia("Kizimkazi Mkunguni", district);
        addShehia("Makunduchi", district);
        addShehia("Mtende", district);
        addShehia("Kajengwa", district);
        addShehia("Mzuri", district);
        addShehia("Ghana", district);
    }

    private void loadWeteShehia() {
        District district = districtRepository.findByName("Wete")
                .orElseThrow();

        addShehia("Gando", district);
        addShehia("Junguni", district);
        addShehia("Ukunjwi", district);
        addShehia("Fundo", district);
        addShehia("Utaani", district);
        addShehia("Bopwe", district);
        addShehia("Chwale", district);
        addShehia("Mapambani", district);
        addShehia("Kojani", district);
        addShehia("Kinyikani", district);
        addShehia("Mchanga Mdogo", district);
        addShehia("Kangagani", district);
        addShehia("Kiuyu Kigongoni", district);
        addShehia("Kiuyu Minungwini", district);
        addShehia("Kambini", district);
        addShehia("Kisiwani", district);
        addShehia("Mtambwe Kusini", district);
        addShehia("Mtambwe Kaskazini", district);
        addShehia("Piki", district);
        addShehia("Limbani", district);
        addShehia("Pandani", district);
        addShehia("Maziwani", district);
        addShehia("Mzambarau ni Takao", district);
        addShehia("Mgogoni", district);
        addShehia("Finya", district);
        addShehia("Kinyasini", district);
        addShehia("Kizimbani", district);
        addShehia("Milindo", district);
        addShehia("Shengelejuu", district);
        addShehia("Mjananza", district);
        addShehia("Kiongoni", district);
        addShehia("Pembeni", district);
        addShehia("Selem", district);
        addShehia("Jadida", district);
        addShehia("Mtemani", district);
        addShehia("Kipangani", district);
    }

    private void loadChakeChakeShehia() {
        District district = districtRepository.findByName("Chake-Chake")
                .orElseThrow();

        addShehia("Madungu", district);
        addShehia("Chanjaani", district);
        addShehia("Shungi", district);
        addShehia("Tibirinzi", district);
        addShehia("Kichungwani", district);
        addShehia("Chachani", district);
        addShehia("Msingini", district);
        addShehia("Kilindi", district);
        addShehia("Mgelema", district);
        addShehia("Chonga", district);
        addShehia("Matale", district);
        addShehia("Mfikiwa", district);
        addShehia("Pujini", district);
        addShehia("Ng'ambwa", district);
        addShehia("Uwandani", district);
        addShehia("Vitongoji", district);
        addShehia("Mchanga Mrima", district);
        addShehia("Mjini Ole", district);
        addShehia("Ole", district);
        addShehia("Mgogoni", district);
        addShehia("Mvumoni", district);
        addShehia("Kibokoni", district);
        addShehia("Gombani", district);
        addShehia("Mkoroshoni", district);
        addShehia("Wara", district);
        addShehia("Wawi", district);
        addShehia("Ziwani", district);
        addShehia("Kwale", district);
        addShehia("Mbuzini", district);
        addShehia("Ndagoni", district);
        addShehia("Wesha", district);
        addShehia("Michungwani", district);
    }

    private void loadMicheweniShehia() {
        District district = districtRepository.findByName("Micheweni")
                .orElseThrow();

        addShehia("Makangale", district);
        addShehia("Tondooni", district);
        addShehia("Msuka Magharibi", district);
        addShehia("Msuka Mashariki", district);
        addShehia("Kipange", district);
        addShehia("Konde", district);
        addShehia("Kifundi", district);
        addShehia("Shumba Mjini", district);
        addShehia("Majenzi", district);
        addShehia("Micheweni", district);
        addShehia("Chamboni", district);
        addShehia("Shanake", district);
        addShehia("Kiuyu Mbuyuni", district);
        addShehia("Maziwa Ng'ombe", district);
        addShehia("Tumbe Magharibi", district);
        addShehia("Tumbe Mashariki", district);
        addShehia("Mihogoni", district);
        addShehia("Kinowe", district);
        addShehia("Chimba", district);
        addShehia("Shumba Viamboni", district);
        addShehia("Sizini", district);
        addShehia("Mjini Wingwi", district);
        addShehia("Wingwi Mapofu", district);
        addShehia("Wingwi Njuguni", district);
        addShehia("Mtemani", district);
    }

    private void loadMkoaniShehia() {
        District district = districtRepository.findByName("Mkoani")
                .orElseThrow();

        addShehia("Dodo", district);
        addShehia("Chambani", district);
        addShehia("Ukutini", district);
        addShehia("Wambaa", district);
        addShehia("Chumbageni", district);
        addShehia("Mgagadu", district);
        addShehia("Ngwachani", district);
        addShehia("Kendwa", district);
        addShehia("Mtangani", district);
        addShehia("Kiwani", district);
        addShehia("Mchakwe", district);
        addShehia("Shamiani", district);
        addShehia("Mwambe", district);
        addShehia("Jombwe", district);
        addShehia("Chole", district);
        addShehia("Muambe", district);
        addShehia("Makombeni", district);
        addShehia("Ng'ombeni", district);
        addShehia("Uweleni", district);
        addShehia("Mbuguani", district);
        addShehia("Changaweni", district);
        addShehia("Makoongwe", district);
        addShehia("Mbuyuni", district);
        addShehia("Shidi", district);
        addShehia("Michenzani", district);
        addShehia("Stahabu", district);
        addShehia("Mkanyageni", district);
        addShehia("Chokocho", district);
        addShehia("Mizingani", district);
        addShehia("Mtambile", district);
        addShehia("Mjimbini", district);
        addShehia("Minazini", district);
        addShehia("Kisiwapanza", district);
        addShehia("Kangani", district);
        addShehia("Kuukuu", district);
        addShehia("Mkungu", district);
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