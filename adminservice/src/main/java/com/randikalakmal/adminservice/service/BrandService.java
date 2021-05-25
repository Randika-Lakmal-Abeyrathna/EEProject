package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.exception.BrandException;
import com.randikalakmal.adminservice.model.Brand;
import com.randikalakmal.adminservice.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class BrandService {

    private final BrandRepository brandRepository;

    public List<Brand> getAllBrand(){
        return brandRepository.findAll();
    }

    public Brand getBrandByBrandName(String brandName){
        return brandRepository.findBrandByBrandname(brandName)
                .orElseThrow(()-> new BrandException("Brand Name "+brandName+" not found"));
    }

    public List<Brand> getBrandByBrandNameLike(String brandName){
       return brandRepository.findByBrandnameIgnoreCaseContaining(brandName);

    }

    public Brand addBrand(Brand brand){
        brandValidation(brand);
        Brand newBrand =setToLowerCase(brand);
        return brandRepository.save(newBrand);
    }

    public Brand updateBrand(Brand brand){
        brandValidation(brand);
        Brand newBrand =setToLowerCase(brand);
        return brandRepository.save(newBrand);
    }

    public int getCountByBrandName(String brandName){
        return brandRepository.countByBrandname(brandName);
    }

    // Validation
    private void brandValidation(Brand brand){
        String brandName = brand.getBrandname().toLowerCase();
        if (getCountByBrandName(brandName)>0){
            throw new BrandException("Brand Name "+brandName+" already exists !");
        }else if (brandName.isBlank() || brandName.isEmpty()){
            throw new BrandException("Brand Name cannot be empty or Blank.");
        }
    }

    private Brand setToLowerCase(Brand brand){
        brand.setBrandname(brand.getBrandname().toLowerCase());
        return brand;
    }
}
