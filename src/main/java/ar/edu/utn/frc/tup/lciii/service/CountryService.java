package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryRequestDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        @Autowired
        private final CountryRepository countryRepository;

        @Autowired
        private final RestTemplate restTemplate;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .borders((List<String>) countryData.get("borders"))
                        .code((String) countryData.get("cca3"))
                        .build();
        }


        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getName(), country.getCode());
        }

        public List<CountryDTO> getAllCountriesDTO() {
                List<Country> countries = getAllCountries();
                return countries.stream().map(this::mapToDTO).collect(Collectors.toList());
        }

        public List<CountryDTO> getCountriesByContinent(String region) {
                List<Country> countries = getAllCountries();

                return countries.stream()
                        .filter(country -> country.getRegion().equalsIgnoreCase(region))
                        .map(country -> new CountryDTO(country.getName(), country.getCode()))
                        .collect(Collectors.toList());
        }

        public List<CountryDTO> getCountriesByLanguage(String language) {
                List<Country> countries = getAllCountries();

                return countries.stream()
                        .filter(country -> country.getLanguages() != null && country.getLanguages().containsValue(language))
                        .map(country -> new CountryDTO(country.getName(), country.getCode()))
                        .collect(Collectors.toList());
        }

        public CountryDTO getCountryWithMostBorders() {
                List<Country> countries = getAllCountries();

                return countries.stream()
                        .filter(country -> country.getBorders() != null)
                        .max(Comparator.comparingInt(country -> country.getBorders().size()))
                        .map(country -> new CountryDTO(country.getName(), country.getCode()))
                        .orElse(null);
        }

        public List<CountryDTO> saveCountries(CountryRequestDTO requestDTO){

                if (requestDTO.getAmountOfCountryToSave() > 10){
                        throw new IllegalArgumentException();

                }
                List<Country> countries = getAllCountries();


                Collections.shuffle(countries);
                List<Country> selectedCountries = countries.stream()
                        .limit(requestDTO.getAmountOfCountryToSave())
                        .collect(Collectors.toList());


                List<CountryEntity> savedCountries = selectedCountries.stream()
                        .map(country -> {
                                CountryEntity entity = new CountryEntity();
                                entity.setName(country.getName());
                                entity.setCode(country.getCode());
                                entity.setPopulation(country.getPopulation());
                                entity.setArea(country.getArea());
                                return countryRepository.save(entity);
                        })
                        .collect(Collectors.toList());


                return savedCountries.stream()
                        .map(country -> new CountryDTO(country.getName(), country.getCode()))
                        .collect(Collectors.toList());
                }
        }


