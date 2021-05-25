package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.dto.AdminInfoRequest;
import com.randikalakmal.adminservice.dto.UserRequest;
import com.randikalakmal.adminservice.exception.AdminInfoException;
import com.randikalakmal.adminservice.model.AdminInfo;
import com.randikalakmal.adminservice.model.User;
import com.randikalakmal.adminservice.repository.AdminInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Service
public class AdminInfoService {

    private final AdminInfoRepository adminInfoRepository;
    private final UserService userService;

    public AdminInfo addAdmin(AdminInfoRequest adminInfoRequest){
        adminAddValidation(adminInfoRequest);
        User user = userService.addUser(adminInfoRequest);
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setFirstName(adminInfoRequest.getFirstName().toLowerCase());
        adminInfo.setMiddleName(adminInfoRequest.getMiddleName().toLowerCase());
        adminInfo.setLastName(adminInfoRequest.getLastName().toLowerCase());
        adminInfo.setUser(user);
        AdminInfo addedAdmin = adminInfoRepository.save(adminInfo);
        userService.sendUserActivationEmail(user.getEmail());
        return addedAdmin;
    }

    public AdminInfo updateAdmin(AdminInfoRequest adminInfoRequest){
        //update User Information
        User user = userService.updateUser(adminInfoRequest);

        AdminInfo adminInfo = getAdminInfoByEmail(user.getEmail());
        adminInfo.setUser(user);
        adminInfo.setFirstName(adminInfoRequest.getFirstName());
        adminInfo.setMiddleName(adminInfoRequest.getMiddleName());
        adminInfo.setLastName(adminInfoRequest.getLastName());

        return adminInfoRepository.save(adminInfo);
    }

    public List<AdminInfo> getAllAdminInfo(){
        return adminInfoRepository.findAll();
    }

    public AdminInfo getAdminInfoByEmail(String email){
        User user =userService.getUserByEmail(email);
        return adminInfoRepository.getByUser(user)
                .orElseThrow(()->new AdminInfoException("Admin Not found"));
    }

    public void updateImage(String email, MultipartFile image){
        userService.updateUserImage(email,image);
    }

    private int getAdminCount(String email){
        User user= userService.getUserByEmail(email);
        return adminInfoRepository.countByUser(user);
    }

    public void adminAddValidation(AdminInfoRequest adminInfoRequest){
        String email = adminInfoRequest.getEmail();

        if (getAdminCount(email)>0 ){
            throw new AdminInfoException("User email "+email+" is already an admin");
        }
    }
}
