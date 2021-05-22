package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.exception.GenderException;
import com.randikalakmal.adminservice.model.Gender;
import com.randikalakmal.adminservice.repository.GenderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class GenderService {

    private final GenderRepository genderRepository;

    public List<Gender> getAllGender(){
        return genderRepository.findAll();
    }

    public Gender addGender(Gender gender){
        genderValidation(gender);
        Gender newGender = setGenderNameToLowerCase(gender);
        return genderRepository.save(newGender);
    }

    public Gender findGenderById(Integer id){
        return genderRepository.getGenderByIdgender(id)
                .orElseThrow(()-> new GenderException("Gender by Id "+id+" was not found"));
    }

    public Gender updateGender(Gender gender){
        genderValidation(gender);
        Gender newGender = setGenderNameToLowerCase(gender);
        return genderRepository.save(newGender);
    }

    public Integer getCountByGender(String gender){
        return genderRepository.countByGender(gender);
    }

    public Gender findGenderByGender(String gender){
        return genderRepository.findGenderByGender(gender)
                .orElseThrow(()-> new GenderException("Gender by name "+gender+" was not found"));
    }

    public void deleteGenderById(Integer id){
        genderRepository.deleteGenderByIdgender(id);
    }

    private void genderValidation(Gender gender){
        String genderName = gender.getGender().toLowerCase();

        if(getCountByGender(genderName)>0){
             throw new GenderException("Gender "+genderName+" already in use");
        }else if(genderName.isBlank() || genderName.isEmpty()){
            throw new GenderException("Gender name can not be empty or blank");
        }

    }


    private Gender setGenderNameToLowerCase(Gender gender){
        gender.setGender(gender.getGender().toLowerCase());
        return gender;
    }

}
