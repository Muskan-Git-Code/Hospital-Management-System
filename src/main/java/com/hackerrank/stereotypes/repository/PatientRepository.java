package com.hackerrank.stereotypes.repository;


import com.hackerrank.stereotypes.model.Patient;
import com.hackerrank.stereotypes.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer> {
    List<Patient> findAllByStatus(String status);

    Optional<Patient> findByRoomAndStatus(String room, String status);

    Optional<Patient> findByEmailAndStatus(String room, String status);

    Optional<Patient> findByPhoneAndStatus(String phone, String status);
}
