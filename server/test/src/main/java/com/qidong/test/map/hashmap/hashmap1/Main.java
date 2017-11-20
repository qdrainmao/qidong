package com.qidong.test.map.hashmap.hashmap1;

import java.util.HashMap;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Country india = new Country("india", 1000);
        Country japan = new Country("japan", 10000);
        Country france = new Country("france", 2000);
        Country russia = new Country("russia", 20000);

        HashMap<Country, String> countryCapitalMap = new HashMap<Country, String>(10);
        countryCapitalMap.put(india, "Delhi");
        countryCapitalMap.put(japan, "Tokyo");
        countryCapitalMap.put(france, "Paris");
        countryCapitalMap.put(russia, "Moscow");

        Iterator<Country> countryIterator = countryCapitalMap.keySet().iterator();
        while (countryIterator.hasNext()){
            Country country = countryIterator.next();
            String capital = countryCapitalMap.get(country);
            System.out.println(country.getName()+"--------------"+capital);
        }
    }
}




