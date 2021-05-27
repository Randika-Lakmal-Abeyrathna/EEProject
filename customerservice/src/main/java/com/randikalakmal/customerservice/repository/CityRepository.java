package com.randikalakmal.customerservice.repository;

import com.randikalakmal.customerservice.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City,Integer> {

    Optional<City> getCityByIdcity(Integer id);

    List<City> findByCityIgnoreCaseContaining(String city);

    List<City> findByCityIgnoreCaseStartsWith(String city);

    Optional<City> findCityByCity(String city);

    Integer countByCity(String city);

    void deleteCityByIdcity(Integer id);
}
