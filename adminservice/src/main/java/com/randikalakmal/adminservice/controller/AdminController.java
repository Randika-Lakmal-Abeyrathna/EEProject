package com.randikalakmal.adminservice.controller;

import com.randikalakmal.adminservice.dto.AdminInfoRequest;
import com.randikalakmal.adminservice.model.AdminInfo;
import com.randikalakmal.adminservice.service.AdminInfoService;
import lombok.AllArgsConstructor;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/info")
@AllArgsConstructor
public class AdminController {

    private final AdminInfoService adminInfoService;

    @PostMapping("/add")
    public ResponseEntity<AdminInfo> addAdmin(@RequestBody AdminInfoRequest adminInfoRequest){
        AdminInfo  adminInfo = adminInfoService.addAdmin(adminInfoRequest);
        return new ResponseEntity<>(adminInfo, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<AdminInfo> updateAdmin(@RequestBody AdminInfoRequest adminInfoRequest){
        AdminInfo updatedAdmin = adminInfoService.updateAdmin(adminInfoRequest);
        return new ResponseEntity<>(updatedAdmin,HttpStatus.OK);
    }

    @PostMapping("/update/image")
    public ResponseEntity<?> updateImage(@RequestParam("email") String email, MultipartFile image){
        adminInfoService.updateImage(email,image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdminInfo>> getAllAdmin(){
        List<AdminInfo>adminInfoList = adminInfoService.getAllAdminInfo();
        return new ResponseEntity<>(adminInfoList,HttpStatus.OK);
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<AdminInfo> getAdminByEmail(@PathVariable("email") String email){
        AdminInfo adminInfo = adminInfoService.getAdminInfoByEmail(email);
        return new ResponseEntity<>(adminInfo,HttpStatus.OK);
    }

}
