package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.dto.UserRequest;
import com.randikalakmal.adminservice.exception.UserException;
import com.randikalakmal.adminservice.model.*;
import com.randikalakmal.adminservice.repository.ImageDataRepository;
import com.randikalakmal.adminservice.repository.UserRepository;
import com.randikalakmal.adminservice.repository.UserTokenRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ImageDataRepository imageDataRepository;
    private final UserTokenRepository userTokenRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final CityService cityService;
    private final StatusService statusService;
    private final UserTypeService userTypeService;

    public User addUser(UserRequest userRequest){
        userAddValidation(userRequest);
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        City city = cityService.findCityByCityName(userRequest.getCity().toLowerCase());
        Status status = statusService.findStatusByStatus(userRequest.getStatus().toLowerCase());
        UserType userType = userTypeService.getUserTypeByUserType(userRequest.getUserType().toLowerCase());

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setAddressNo(userRequest.getAddressNo());
        user.setAddress_street(userRequest.getAddressStreet1());
        user.setAddress_street2(userRequest.getAddressStreet2());
        user.setContactNumber1(userRequest.getContactNumber1());
        user.setContactNumber2(userRequest.getContactNumber2());
        user.setPassword(userRequest.getPassword());
        user.setEnabled(false);
        user.setCity(city);
        user.setStatus(status);
        user.setUserType(userType);


        return userRepository.save(user);
    }

    public User updateUser(UserRequest userRequest){
        userUpdateValidation(userRequest);
        String email = userRequest.getEmail();
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        City city = cityService.findCityByCityName(userRequest.getCity().toLowerCase());
        Status status = statusService.findStatusByStatus(userRequest.getStatus().toLowerCase());
        UserType userType = userTypeService.getUserTypeByUserType(userRequest.getUserType().toLowerCase());

        User user = getUserByEmail(email);
        user.setEmail(userRequest.getEmail());
        user.setAddressNo(userRequest.getAddressNo());
        user.setAddress_street(userRequest.getAddressStreet1());
        user.setAddress_street2(userRequest.getAddressStreet2());
        user.setContactNumber1(userRequest.getContactNumber1());
        user.setContactNumber2(userRequest.getContactNumber2());
        user.setPassword(userRequest.getPassword());
        user.setCity(city);
        user.setStatus(status);
        user.setUserType(userType);

        return userRepository.save(user);
    }

    public User findUserByEmailAndUserType(String email, UserType userType){
       return userRepository.findByEmailAndUserType(email,userType)
                .orElseThrow(()-> new UserException("User Not Found"));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public boolean checkUserExistByEmail(String email){
        return userRepository.existsById(email);
    }

    public User getUserByEmail(String email){
        return userRepository.findById(email)
                .orElseThrow(()-> new UserException("User Not found with email "+email));
    }

    public void updateUserImage(String email, @NotNull MultipartFile file){
        User user = getUserByEmail(email);
        // Upload Image start

        String getCurrentDateAndTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // Using because same image name can upload
        System.out.println(getCurrentDateAndTime);
        String fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileNameFront = fileName.split("\\.")[0];
        String fileExtension=fileName.split("\\.")[1];
        fileNameFront +=getCurrentDateAndTime;
        fileName = fileNameFront+"."+fileExtension;
        System.out.println("File Name"+fileName);
        String uploadDir = "./user-images/";
        Path uploadPath = Paths.get(uploadDir);

        try{
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
        }catch(IOException exception){
            System.out.println("cannot create the user image folder");
            exception.printStackTrace();
        }

        Path filePath = uploadPath.resolve(fileName);
        System.out.println("FILE PATH :" + filePath.toString());

        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageData imageData = new ImageData();
        imageData.setPath(filePath.toString());

        ImageData uploadedImage =imageDataRepository.save(imageData);

        user.setImageData(uploadedImage);
        userRepository.save(user);
    }

    public void sendUserActivationEmail(String email){
        User user = getUserByEmail(email);
        String userToken = generateCustomerVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your account", user.getEmail(),
                "Thank you for signing up ! Please click on the below url to activate your account : " +
                        "http://localhost:8301/api/admin/user/accountverification/" + userToken));
    }

    private String generateCustomerVerificationToken(User user) {

        String customerVerificationToken = UUID.randomUUID().toString();

        UserToken userToken = new UserToken();
        userToken.setToken(customerVerificationToken);
        userToken.setUser(user);

        userTokenRepository.save(userToken);
        return customerVerificationToken;
    }

    public void activateUser(String token){
        Optional<UserToken> userToken = userTokenRepository.findByToken(token);
        userToken.orElseThrow(()->new UserException("Invalid Token"));

        String userEmail = userToken.get().getUser().getEmail();
        User user = getUserByEmail(userEmail);
        user.setEnabled(true);
        userRepository.save(user);
        deleteUserToken(token,user);
    }

    private void deleteUserToken(String token,User user){
        userTokenRepository.deleteUserTokenByTokenAndUser(token,user);
    }

    private void userUpdateValidation(UserRequest userRequest){

        String customerEmail = userRequest.getEmail();
        String password = userRequest.getPassword();

        if (customerEmail.isEmpty() || customerEmail.isBlank())
            throw new UserException("Email id cannot be empty or blank");
        if (password.isEmpty() || password.isBlank()) {
            throw new UserException("Password cannot be empty or blank");
        }
    }

    private void userAddValidation(UserRequest userRequest) {

        String customerEmail = userRequest.getEmail();
        String password = userRequest.getPassword();

        if (customerEmail.isEmpty() || customerEmail.isBlank())
            throw new UserException("Email id cannot be empty or blank");
        if (checkUserExistByEmail(customerEmail)) {
            throw new UserException("Email id " + userRequest.getEmail() + " already is use.");
        }
        if (password.isEmpty() || password.isBlank()) {
            throw new UserException("Password cannot be empty or blank");
        }

    }




}
