package com.randikalakmal.adminservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "image_data")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idimage_data")
    private int idImageData;
    private String path;

}
