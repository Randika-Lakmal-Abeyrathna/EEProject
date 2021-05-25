package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.model.Brand;
import com.randikalakmal.adminservice.model.BrandImage;
import com.randikalakmal.adminservice.repository.BrandImageRepository;
import com.randikalakmal.adminservice.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BrandImageService {

    private final BrandImageRepository brandImageRepository;
    private final BrandService brandService;
    private final BrandRepository brandRepository;

    public void updateBrandImage(String brandName, MultipartFile imageFile){
        Brand brand = brandService.getBrandByBrandName(brandName);

        // Save Brand Image
        String getCurrentDateAndTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // Using because same image name can upload
        String fileName= StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
        String fileNameFront = fileName.split("\\.")[0];
        String fileExtension=fileName.split("\\.")[1];
        fileNameFront +=getCurrentDateAndTime;
        fileName = fileNameFront+"."+fileExtension;
        String uploadDir = "./brand-images/";
        Path uploadPath = Paths.get(uploadDir);

        try{
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
        }catch(IOException exception){
            System.out.println("cannot create the Brand image folder");
            exception.printStackTrace();
        }

        Path filePath = uploadPath.resolve(fileName);

        try {
            InputStream inputStream = imageFile.getInputStream();
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BrandImage brandImage = new BrandImage();
        brandImage.setPath(filePath.toString());

        BrandImage uploadedBrandImage = brandImageRepository.save(brandImage);

        brand.setBrandImage(uploadedBrandImage);
        brandRepository.save(brand);
    }
}
