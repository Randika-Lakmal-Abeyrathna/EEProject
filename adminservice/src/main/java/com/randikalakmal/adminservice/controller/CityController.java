package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.exception.CityException;
import com.randikalakmal.adminservice.model.City;
import com.randikalakmal.adminservice.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/city")
@AllArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('city:read')")
    public ResponseEntity<List<City>> getAllCities(){

        List<City> cityList = cityService.findAllCities();
        return new ResponseEntity<>(cityList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAuthority('city:read')")
    public ResponseEntity<City> getCityById(@PathVariable("id") Integer id){
        City city= cityService.findCityById(id);
        return new ResponseEntity<>(city,HttpStatus.OK);
    }

    @GetMapping("/find/name/{name}")
    @PreAuthorize("hasAuthority('city:read')")
    public  ResponseEntity<City> getCityByName(@PathVariable("name") String name){
       City cityList = cityService.findCityByCityName(name);
        return new ResponseEntity<>(cityList,HttpStatus.OK);
    }

    @GetMapping("/find/like/{name}")
    @PreAuthorize("hasAuthority('city:read')")
    public  ResponseEntity<List<City>> getCityByNameLike(@PathVariable("name") String name){
        List<City> cityList = cityService.findCityByNameLike(name);
        return new ResponseEntity<>(cityList,HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('city:write')")
    public ResponseEntity<City> addCity(@RequestBody City city){
        City newCity = cityService.addCity(city);
        return new ResponseEntity<>(newCity,HttpStatus.CREATED);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('city:edit')")
    public ResponseEntity<City> updateCity(@RequestBody City city){
        City newCity = cityService.updateCity(city);
        return new ResponseEntity<>(newCity,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('city:delete')")
    public ResponseEntity<?> deleteCity(@PathVariable("id") Integer id){
        try{
        cityService.deleteCity(id);
        }catch(DataIntegrityViolationException e){
            throw new CityException("The City you trying to delete already in Use");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
