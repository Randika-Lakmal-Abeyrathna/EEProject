package com.randikalakmal.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private String email;
    private String addressNo;
    private String address_street;
    private String address_street2;
    private Integer contactNumber1;
    private Integer contactNumber2;
    private String password;
    private boolean enabled;
    private Date createddate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_idcity",referencedColumnName = "idcity")
    private City city;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_idstatus",referencedColumnName = "idstatus")
    private Status status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_type_iduser_type",referencedColumnName = "iduser_type")
    private UserType userType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_data_idimage_data" ,referencedColumnName = "idimage_data")
    private ImageData imageData;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_email"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

}
