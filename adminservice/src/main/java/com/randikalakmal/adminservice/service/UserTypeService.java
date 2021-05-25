package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.exception.StatusException;
import com.randikalakmal.adminservice.model.UserType;
import com.randikalakmal.adminservice.repository.UserTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    public UserType getUserTypeByUserType(String UserType){
        return userTypeRepository.findUserTypeByUserType(UserType)
                .orElseThrow(() -> new StatusException("Status by id " + UserType + " was not found"));
    }

}
