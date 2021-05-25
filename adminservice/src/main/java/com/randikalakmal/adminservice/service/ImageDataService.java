package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.model.ImageData;
import com.randikalakmal.adminservice.repository.ImageDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ImageDataService {

    private final ImageDataRepository imageDataRepository;

    public ImageData addImageData(ImageData imageData){
        return imageDataRepository.save(imageData);
    }


}
