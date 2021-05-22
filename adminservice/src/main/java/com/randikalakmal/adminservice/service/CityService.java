package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.exception.CityException;
import com.randikalakmal.adminservice.model.City;
import com.randikalakmal.adminservice.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<City> findAllCities(){
        return cityRepository.findAll();
    }

    public City findCityById(Integer id){
        return cityRepository.getCityByIdcity(id)
                .orElseThrow(()->new CityException("City by Id "+id+" was not found"));
    }

    public List<City> findCityByNameLike(String city){
       return cityRepository.findByCityIgnoreCaseStartsWith(city);
    }

    public City addCity(City city){
        cityValidation(city);
        City newCity = setCityNameToLowerCase(city);
        return cityRepository.save(newCity);
    }

    public City updateCity(City city){
        cityValidation(city);
        City newCity = setCityNameToLowerCase(city);
        return cityRepository.save(newCity);
    }

    public void deleteCity(Integer id){
       cityRepository.deleteCityByIdcity(id);
    }

    public int getCountByCity(String city){
        return cityRepository.countByCity(city);
    }

    public City findCityByCityName(String city){
        return cityRepository.findCityByCity(city)
                .orElseThrow(()->new CityException("City by name "+city+" was not found"));

    }

    private void cityValidation(City city){
        String cityName = city.getCity().toLowerCase();
        if (getCountByCity(cityName)>0){
            throw new CityException("City Name "+cityName+" already exits ! ");
        }else if (cityName.isEmpty() || cityName.isBlank()){
            throw new CityException("City Name can not be blank or Empty !");
        }
    }

    private City setCityNameToLowerCase(City city){
        city.setCity(city.getCity().toLowerCase());
        return city;

    }

}
