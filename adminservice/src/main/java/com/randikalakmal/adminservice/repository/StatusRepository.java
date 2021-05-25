package com.randikalakmal.adminservice.repository;

import com.randikalakmal.adminservice.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status,Integer> {


    Optional<Status> getSalutationByIdstatus(Integer id);

    void deleteSalutationByIdstatus(Integer id);

    Integer countByStatus(String status);

    Optional<Status> findStatusByStatus(String status);

}
