package com.example.soap;

import de.eiswind.training.spring.soap.web_service.Country;
import de.eiswind.training.spring.soap.web_service.Currency;
import org.springframework.stereotype.Component;

@Component
public class CountryRepository {

    public Country findCountry(String name) {
        Country c = new Country();
        c.setName(name);
        c.setPopulation(9000);
        c.setCapital("Random Town");
        c.setCurrency(Currency.EUR);
        return c;
    }
}
