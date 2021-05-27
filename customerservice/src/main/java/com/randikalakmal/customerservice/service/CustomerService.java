package com.randikalakmal.customerservice.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.randikalakmal.customerservice.dto.CustomerImageUpdateRequest;
import com.randikalakmal.customerservice.dto.CustomerRequest;
import com.randikalakmal.customerservice.exception.CustomerException;
import com.randikalakmal.customerservice.model.*;
import com.randikalakmal.customerservice.repository.CustomerInfoRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerInfoRepository customerInfoRepository;
    @Autowired
    RestTemplate restTemplate;

    public List<CustomerInfo> getAllCustomers(){
          return customerInfoRepository.findAll();
    }

    public CustomerInfo findCustomerByUserEmail(String email){
        ResponseEntity<User> userResponseEntity = restTemplate.exchange(
                "http://localhost:8301/api/admin/user/find/" + email,
                HttpMethod.GET, null, User.class);

        if (userResponseEntity.getStatusCode() ==HttpStatus.OK){
            User user = userResponseEntity.getBody();
            return customerInfoRepository.findByUser(user)
                    .orElseThrow(()-> new CustomerException("Customer "+user.getEmail()+" Not Fount"));
        }else{
            throw new CustomerException("User "+email+" Not found");
        }

    }

    public CustomerInfo addCustomer(CustomerRequest customerRequest){
        customerAddValidation(customerRequest);
        HttpEntity<CustomerRequest> requestEntity = new HttpEntity<>(customerRequest);

        ResponseEntity<User> responseEntity = restTemplate.exchange(
                "http://localhost:8301/api/admin/user/add",
                HttpMethod.POST,
                requestEntity,
                User.class
        );
        if(responseEntity.getStatusCode() == HttpStatus.CREATED){
            User user = responseEntity.getBody();
            Gender gender =null;
            Salutation salutation =null;

            ResponseEntity<Gender> genderResponseEntity = restTemplate.exchange(
                    "http://localhost:8301/api/admin/gender/find/name/" + customerRequest.getGender().toLowerCase(),
                    HttpMethod.GET, null, Gender.class);
            if (genderResponseEntity.getStatusCode() == HttpStatus.OK)
                gender = genderResponseEntity.getBody();

            ResponseEntity<Salutation> salutationResponseEntity = restTemplate.exchange(
                    "http://localhost:8301/api/admin/salutation/find/name/" + customerRequest.getSalutation().toLowerCase(),
                    HttpMethod.GET, null, Salutation.class);

            if (salutationResponseEntity.getStatusCode() == HttpStatus.OK)
                salutation = salutationResponseEntity.getBody();

            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setFirstName(customerRequest.getFirstName().toLowerCase());
            customerInfo.setMiddleName(customerRequest.getMiddleName().toLowerCase());
            customerInfo.setLastName(customerRequest.getLastName().toLowerCase());
            customerInfo.setNicNumber(customerRequest.getNicNumber().toLowerCase());
            customerInfo.setDateOfBirth(customerRequest.getDateOfBirth());
            customerInfo.setUser(user);
            customerInfo.setGender(gender);
            customerInfo.setSalutation(salutation);

            CustomerInfo savedCustomer = customerInfoRepository.save(customerInfo);
            restTemplate.exchange(
                    "http://localhost:8301/api/admin/user/sendActivate/"+user.getEmail(),
                    HttpMethod.GET,
                     null,
                    Object.class
            );

            return savedCustomer;
        }else{
            throw new CustomerException("User Not Created");
        }
    }

    public CustomerInfo updateCustomer(CustomerRequest customerRequest){
        customerUpdateValidation(customerRequest);
        HttpEntity<CustomerRequest> requestEntity = new HttpEntity<>(customerRequest);

        ResponseEntity<User> responseEntity = restTemplate.exchange(
                "http://localhost:8301/api/admin/user/update",
                HttpMethod.PUT,
                requestEntity,
                User.class
        );
        if (responseEntity.getStatusCode()==HttpStatus.OK){
            User user = responseEntity.getBody();

            //Get Customer By User
            CustomerInfo customerInfo = customerInfoRepository.findByUser(user)
                    .orElseThrow(()-> new CustomerException("Customer "+user.getEmail()+" Not Fount"));

            Gender gender =null;
            Salutation salutation =null;

            ResponseEntity<Gender> genderResponseEntity = restTemplate.exchange(
                    "http://localhost:8301/api/admin/gender/find/name/" + customerRequest.getGender().toLowerCase(),
                    HttpMethod.GET, null, Gender.class);
            if (genderResponseEntity.getStatusCode() == HttpStatus.OK)
                gender = genderResponseEntity.getBody();

            ResponseEntity<Salutation> salutationResponseEntity = restTemplate.exchange(
                    "http://localhost:8301/api/admin/salutation/find/name/" + customerRequest.getSalutation().toLowerCase(),
                    HttpMethod.GET, null, Salutation.class);

            if (salutationResponseEntity.getStatusCode() == HttpStatus.OK)
                salutation = salutationResponseEntity.getBody();

            customerInfo.setFirstName(customerRequest.getFirstName().toLowerCase());
            customerInfo.setMiddleName(customerRequest.getMiddleName().toLowerCase());
            customerInfo.setLastName(customerRequest.getLastName().toLowerCase());
            customerInfo.setNicNumber(customerRequest.getNicNumber().toLowerCase());
            customerInfo.setDateOfBirth(customerRequest.getDateOfBirth());
            customerInfo.setUser(user);
            customerInfo.setGender(gender);
            customerInfo.setSalutation(salutation);

            return customerInfoRepository.save(customerInfo);
        }else{
            throw new CustomerException("User Not Updated");
        }
    }

    public void updateCustomerImage(String email, MultipartFile image) throws IOException {
        String fileName = image.getOriginalFilename();
        byte[] fileContent = image.getBytes();
        HttpHeaders parts = new HttpHeaders();
        parts.setContentType(MediaType.IMAGE_JPEG);
        parts.setContentType(MediaType.IMAGE_GIF);
        parts.setContentType(MediaType.IMAGE_PNG);

        final ByteArrayResource byteArrayResource = new ByteArrayResource(fileContent){
            @Override
            public String getFilename() {
                return fileName;
            }
        };
        final HttpEntity<ByteArrayResource> partsEntity = new HttpEntity<>(byteArrayResource,parts);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String,Object> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("image",partsEntity);
        requestMap.add("email",email);

        ResponseEntity<?> responseEntity = restTemplate.exchange(
                "http://localhost:8301/api/admin/user/update/image",HttpMethod.POST, new HttpEntity<>(requestMap,headers),
                ResponseEntity.class
        );

    }

    private void customerUpdateValidation(CustomerRequest customerRequest){
        String email = customerRequest.getEmail();
        Date customerBirthDay = customerRequest.getDateOfBirth();
        String customerFirstName = customerRequest.getFirstName();
        String password = customerRequest.getPassword();

        if (email.isEmpty() || email.isBlank())
            throw new CustomerException("Email id cannot be empty or blank");
        if (customerBirthDay.after(new Date())) {
            throw new CustomerException("Invalid Date of birth");
        }
        if (customerFirstName.isBlank() || customerFirstName.isEmpty()) {
            throw new CustomerException("Customer First Name cannot be empty or blank");
        }
        if (password.isEmpty() || password.isBlank()) {
            throw new CustomerException("Password cannot be empty or blank");
        }
    }

    private void customerAddValidation(CustomerRequest customerRequest){
        String email = customerRequest.getEmail();
        Date customerBirthDay = customerRequest.getDateOfBirth();
        String customerFirstName = customerRequest.getFirstName();
        String password = customerRequest.getPassword();

        if (email.isEmpty() || email.isBlank())
            throw new CustomerException("Email id cannot be empty or blank");
        if (customerBirthDay.after(new Date())) {
            throw new CustomerException("Invalid Date of birth");
        }
        if (customerFirstName.isBlank() || customerFirstName.isEmpty()) {
            throw new CustomerException("Customer First Name cannot be empty or blank");
        }
        if (password.isEmpty() || password.isBlank()) {
            throw new CustomerException("Password cannot be empty or blank");
        }

        ResponseEntity<Boolean> userResponseEntity = restTemplate.exchange(
                "http://localhost:8301/api/admin/user/existence/" +email,
                HttpMethod.GET, null, Boolean.class);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK){
            Boolean flag = userResponseEntity.getBody();
            if (flag){
                throw new CustomerException("Customer "+email+" already in use");
            }
        }


    }

}
