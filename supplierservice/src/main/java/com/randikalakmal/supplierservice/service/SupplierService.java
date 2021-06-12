package com.randikalakmal.supplierservice.service;

import com.randikalakmal.supplierservice.dto.SupplierInfoRequest;
import com.randikalakmal.supplierservice.exception.SupplierException;
import com.randikalakmal.supplierservice.model.SupplierInfo;
import com.randikalakmal.supplierservice.model.User;
import com.randikalakmal.supplierservice.repository.SupplierInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class SupplierService {

    private final SupplierInfoRepository supplierInfoRepository;
    @Autowired
    private final RestTemplate restTemplate;

    private final String ADMIN_SERVICE_URL = "http://admin-service/api/admin";

    public List<SupplierInfo> getAllSupplier(){
        return supplierInfoRepository.findAll();
    }

    public SupplierInfo getSupplierById(Integer id){
        return supplierInfoRepository.findById(id)
                .orElseThrow(()->new SupplierException("Supplier id "+id+" not found"));
    }

    public SupplierInfo getSupplierByUserEmail(String email){

        ResponseEntity<User> userResponseEntity = restTemplate.exchange(
                ADMIN_SERVICE_URL +"/user/find/" + email,
                HttpMethod.GET, null, User.class);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK){
            User user = userResponseEntity.getBody();
            return supplierInfoRepository.findByUser(user)
                    .orElseThrow(()-> new SupplierException("Customer "+user.getEmail()+" Not Fount"));
        }else{
            throw new SupplierException("User "+email+" Not found");
        }
    }

    public SupplierInfo addSupplier(SupplierInfoRequest supplierInfoRequest){
        addSupplierValidation(supplierInfoRequest);
        HttpEntity<SupplierInfoRequest> requestEntity = new HttpEntity<>(supplierInfoRequest);

        ResponseEntity<User> responseEntity = restTemplate.exchange(
                ADMIN_SERVICE_URL+"/user/add",
                HttpMethod.POST,
                requestEntity,
                User.class
        );
        if (responseEntity.getStatusCode() == HttpStatus.CREATED){
            User user = responseEntity.getBody();

            SupplierInfo supplierInfo = new SupplierInfo();
            supplierInfo.setSupplierName(supplierInfoRequest.getSupplierName().toLowerCase());
            supplierInfo.setCompanyName(supplierInfoRequest.getCompanyName().toLowerCase());
            supplierInfo.setCompanyRegNumber(supplierInfoRequest.getCompanyRegNumber());
            supplierInfo.setUser(user);

            SupplierInfo savedSupplierInfo = supplierInfoRepository.save(supplierInfo);

            restTemplate.exchange(
                    ADMIN_SERVICE_URL+"/user/sendActivate/"+user.getEmail(),
                    HttpMethod.GET,
                    null,
                    Object.class
            );

            return savedSupplierInfo;

        }else{
            throw new SupplierException("User Not Created");
        }
    }

    public SupplierInfo updateSupplierInfo(SupplierInfoRequest supplierInfoRequest){
        updateSupplierValidation(supplierInfoRequest);
        HttpEntity<SupplierInfoRequest> requestHttpEntity = new HttpEntity<>(supplierInfoRequest);

        ResponseEntity<User> userResponseEntity = restTemplate.exchange(
                ADMIN_SERVICE_URL + "/user/update",
                HttpMethod.PUT,
                requestHttpEntity,
                User.class
        );

        if (userResponseEntity.getStatusCode() ==HttpStatus.OK){
            User user = userResponseEntity.getBody();

            SupplierInfo supplierInfo = supplierInfoRepository.findByUser(user)
                    .orElseThrow(()->new SupplierException("Supplier "+user.getEmail()+" was not found"));

            supplierInfo.setSupplierName(supplierInfoRequest.getSupplierName().toLowerCase());
            supplierInfo.setCompanyName(supplierInfoRequest.getCompanyName().toLowerCase());
            supplierInfo.setCompanyRegNumber(supplierInfoRequest.getCompanyRegNumber());
            supplierInfo.setUser(user);

            return supplierInfoRepository.save(supplierInfo);
        }else{
            throw new SupplierException("User Not Updated");
        }
    }

    public void updateSupplierImage(String email, MultipartFile image) throws IOException {
        String filename = image.getOriginalFilename();
        byte[] fileContent = image.getBytes();

        HttpHeaders parts = new HttpHeaders();
        parts.setContentType(MediaType.IMAGE_GIF);
        parts.setContentType(MediaType.IMAGE_JPEG);
        parts.setContentType(MediaType.IMAGE_PNG);

        final ByteArrayResource byteArrayResource = new ByteArrayResource(fileContent){
            @Override
            public String getFilename() {
                return filename;
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
                ADMIN_SERVICE_URL+"/user/update/image",
                HttpMethod.POST,
                new HttpEntity<>(requestMap,headers),
                ResponseEntity.class
        );


    }

    private void addSupplierValidation(SupplierInfoRequest supplierInfoRequest){
        String email = supplierInfoRequest.getEmail();
        String password = supplierInfoRequest.getPassword();
        String supplierName = supplierInfoRequest.getSupplierName();
        String companyName = supplierInfoRequest.getCompanyName();
        String companyRegNumber = supplierInfoRequest.getCompanyRegNumber();

        if (email.isEmpty() || email.isBlank())
            throw new SupplierException("Email id cannot be empty or blank");

        if (supplierName.isBlank() || supplierName.isEmpty()) {
            throw new SupplierException("Supplier Name cannot be empty or blank");
        }
        if (companyName.isBlank() || companyName.isEmpty()) {
            throw new SupplierException("Company Name cannot be empty or blank");
        }
        if (companyRegNumber.isBlank() || companyRegNumber.isEmpty()) {
            throw new SupplierException("Company Registration Number cannot be empty or blank");
        }
        if (password.isEmpty() || password.isBlank()) {
            throw new SupplierException("Password cannot be empty or blank");
        }

        ResponseEntity<Boolean> userResponseEntity = restTemplate.exchange(
                ADMIN_SERVICE_URL+"/user/existence/" +email,
                HttpMethod.GET, null, Boolean.class);

        if (userResponseEntity.getStatusCode() == HttpStatus.OK){
            Boolean flag = userResponseEntity.getBody();
            if (flag){
                throw new SupplierException("Supplier email "+email+" already in use");
            }
        }


    }

    private void updateSupplierValidation(SupplierInfoRequest supplierInfoRequest){
        String email = supplierInfoRequest.getEmail();
        String password = supplierInfoRequest.getPassword();
        String supplierName = supplierInfoRequest.getSupplierName();
        String companyName = supplierInfoRequest.getCompanyName();
        String companyRegNumber = supplierInfoRequest.getCompanyRegNumber();

        if (email.isEmpty() || email.isBlank())
            throw new SupplierException("Email id cannot be empty or blank");

        if (supplierName.isBlank() || supplierName.isEmpty()) {
            throw new SupplierException("Supplier Name cannot be empty or blank");
        }
        if (companyName.isBlank() || companyName.isEmpty()) {
            throw new SupplierException("Company Name cannot be empty or blank");
        }
        if (companyRegNumber.isBlank() || companyRegNumber.isEmpty()) {
            throw new SupplierException("Company Registration Number cannot be empty or blank");
        }
        if (password.isEmpty() || password.isBlank()) {
            throw new SupplierException("Password cannot be empty or blank");
        }

    }
}
