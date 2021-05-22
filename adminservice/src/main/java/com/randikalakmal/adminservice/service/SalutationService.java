package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.exception.SalutationException;
import com.randikalakmal.adminservice.model.Salutation;
import com.randikalakmal.adminservice.repository.SalutationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class SalutationService {

    private final SalutationRepository salutationRepository;


    public List<Salutation> findAllSalutations(){
        return salutationRepository.findAll();
    }

    public Salutation findById(Integer id){
        return salutationRepository.getSalutationByIdsalutation(id)
                .orElseThrow(()-> new SalutationException("Salutation by Id "+id+" was not found"));
    }

    public Salutation addSalutation(Salutation salutation){
        salutationValidation(salutation);
        Salutation newSalutation = setSalutationNameToLowerCase(salutation);
        return salutationRepository.save(newSalutation);
    }

    public Salutation updateSalutation(Salutation salutation){
        salutationValidation(salutation);
        Salutation newSalutation = setSalutationNameToLowerCase(salutation);
        return salutationRepository.save(newSalutation);
    }

    public void deleteSalutationById(Integer id){
        salutationRepository.deleteSalutationByIdsalutation(id);
    }

    public Integer getCountBySalutation(String salutation){
        return salutationRepository.countBySalutation(salutation);
    }

    public Salutation findSalutationBySalutation(String salutation){
        return salutationRepository.findSalutationBySalutation(salutation)
                .orElseThrow(()-> new SalutationException("Salutation by name "+salutation+" was not found"));
    }

    private void salutationValidation(Salutation salutation){
        String salutationName = salutation.getSalutation().toLowerCase();

        if (getCountBySalutation(salutationName)>0){
            throw new SalutationException("Salutation "+salutationName+" is already in use");
        }else if(salutationName.isEmpty()|| salutationName.isBlank()){
            throw new SalutationException("Salutation cannot be empty or blank");
        }

    }

    private Salutation setSalutationNameToLowerCase(Salutation salutation){
        salutation.setSalutation(salutation.getSalutation().toLowerCase());
        return salutation;
    }

}
