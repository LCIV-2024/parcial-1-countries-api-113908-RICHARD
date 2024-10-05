package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryRequestDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CountryServiceTest {

    @InjectMocks
    private CountryService countryService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCountries() {

        List<Map<String, Object>> mockResponse = new ArrayList<>();

        Map<String, Object> countryData = new HashMap<>();
        countryData.put("population", 500000);
        countryData.put("area", 100000.0);
        countryData.put("region", "Asia");
        countryData.put("cca3", "ABC");
        countryData.put("borders", Arrays.asList("DEF", "GHI"));

        Map<String, String> languages = new HashMap<>();
        languages.put("eng", "English");
        countryData.put("languages", languages);

        Map<String, Object> nameData = new HashMap<>();
        nameData.put("common", "Testland");
        countryData.put("name", nameData);

        mockResponse.add(countryData);


        when(restTemplate.getForObject("https://restcountries.com/v3.1/all", List.class)).thenReturn(mockResponse);


        List<Country> countries = countryService.getAllCountries();


        assertEquals(1, countries.size());
        Country country = countries.get(0);
        assertEquals("Testland", country.getName());
        assertEquals(500000, country.getPopulation());
        assertEquals(100000.0, country.getArea());
        assertEquals("Asia", country.getRegion());
        assertEquals("ABC", country.getCode());
        assertEquals(Arrays.asList("DEF", "GHI"), country.getBorders());
        assertEquals(languages, country.getLanguages());
    }

    @Test
    void testGetAllCountriesDTO() {

        List<Map<String, Object>> mockedResponse = List.of(
                createMockCountry("France", "FR", 65273511, 551695.0, "Europe",
                        List.of("DE", "BE", "ES"), Map.of("en", "English", "fr", "French")),
                createMockCountry("India", "IN", 1380004385, 3287263.0, "Asia",
                        List.of("CN", "PK", "BD"), Map.of("en", "English", "hi", "Hindi"))
        );


        when(restTemplate.getForObject("https://restcountries.com/v3.1/all", List.class))
                .thenReturn(mockedResponse);


        List<CountryDTO> mockResponse = countryService.getAllCountriesDTO();


        assertEquals(2, mockResponse.size());
    }


    @Test
    void testGetCountriesByContinent() {

        List<Country> mockedCountries = List.of(
                new Country("France", 65273511, 551695.0, "FR", "Europe", List.of("DE", "BE", "ES"), Map.of("en", "English", "fr", "French")),
                new Country("India", 1380004385, 3287263.0, "IN", "Asia", List.of("CN", "PK", "BD"), Map.of("en", "English", "hi", "Hindi")),
                new Country("Germany", 83783942, 357022.0, "DE", "Europe", List.of("FR", "PL"), Map.of("de", "German"))
        );


        List<Map<String, Object>> mockedResponse = List.of(
                createMockCountry("France", "FR", 65273511, 551695.0, "Europe",
                        List.of("DE", "BE", "ES"), Map.of("en", "English", "fr", "French"))

        );


        when(restTemplate.getForObject("https://restcountries.com/v3.1/all", List.class))
                .thenReturn(mockedResponse);


        List<CountryDTO> europeanCountries = countryService.getCountriesByContinent("Europe");


        assertEquals(1, europeanCountries.size()); // Expecting 2 countries: France and Germany
        assertEquals("France", europeanCountries.get(0).getName());
        assertEquals("FR", europeanCountries.get(0).getCode());

    }

    @Test
    public void testGetCountriesByLanguage(){
        List<Country> mockedCountries = List.of(
                new Country("France", 65273511, 551695.0, "FR", "Europe", List.of("DE", "BE", "ES"), Map.of("en", "English", "fr", "French")),
                new Country("India", 1380004385, 3287263.0, "IN", "Asia", List.of("CN", "PK", "BD"), Map.of("en", "English", "hi", "Hindi")),
                new Country("Germany", 83783942, 357022.0, "DE", "Europe", List.of("FR", "PL"), Map.of("de", "German"))
        );


        List<Map<String, Object>> mockedResponse = List.of(
                createMockCountry("France", "FR", 65273511, 551695.0, "Europe",
                        List.of("DE", "BE", "ES"), Map.of("en", "English", "fr", "French"))

        );


        when(restTemplate.getForObject("https://restcountries.com/v3.1/all", List.class))
                .thenReturn(mockedResponse);

        List<CountryDTO> response = countryService.getCountriesByLanguage("English");
        assertEquals(1, response.size());
    }

    @Test
    public void testSaveCountries(){
        List<Country> mockedCountries = List.of(
                new Country("France", 65273511, 551695.0, "FR", "Europe", List.of("DE", "BE", "ES"), Map.of("en", "English", "fr", "French")),
                new Country("India", 1380004385, 3287263.0, "IN", "Asia", List.of("CN", "PK", "BD"), Map.of("en", "English", "hi", "Hindi")),
                new Country("Germany", 83783942, 357022.0, "DE", "Europe", List.of("FR", "PL"), Map.of("de", "German"))
        );


        List<Map<String, Object>> mockedResponse = List.of(
                createMockCountry("France", "FR", 65273511, 551695.0, "Europe",
                        List.of("DE", "BE", "ES"), Map.of("en", "English", "fr", "French"))

        );


        when(restTemplate.getForObject("https://restcountries.com/v3.1/all", List.class))
                .thenReturn(mockedResponse);


        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setName("France");
        countryEntity.setCode("FR");
        countryEntity.setArea(551695.0);
        countryEntity.setPopulation(65273511L);

        CountryRequestDTO createRequest = new CountryRequestDTO();
        createRequest.setAmountOfCountryToSave(1);
        when(countryRepository.save(any(CountryEntity.class))).thenReturn(countryEntity);

        List<CountryDTO> countryDTOS = countryService.saveCountries(createRequest);

        Assertions.assertEquals(countryDTOS.get(0).getName(), countryEntity.getName());

    }

    @Test
    void testSaveCountries_WithInvalidRequest() {

        CountryRequestDTO requestDTO = new CountryRequestDTO();
        requestDTO.setAmountOfCountryToSave(11);


        assertThrows(IllegalArgumentException.class, () -> {
            countryService.saveCountries(requestDTO);
        });
    }
    private Map<String, Object> createMockCountry(String name, String code, long population, double area, String region, List<String> borders, Map<String, String> languages) {
        Map<String, Object> country = new HashMap<>();
        Map<String, Object> nameData = new HashMap<>();
        nameData.put("common", name);
        country.put("name", nameData);
        country.put("cca3", code);
        country.put("population", population);
        country.put("area", area);
        country.put("region", region);
        country.put("borders", borders);
        country.put("languages", languages);
        return country;
    }


}
