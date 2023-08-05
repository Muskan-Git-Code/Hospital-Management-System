package com.hackerrank.stereotypes.service;

import com.hackerrank.stereotypes.model.Patient;
import com.hackerrank.stereotypes.model.Staff;
import com.hackerrank.stereotypes.repository.PatientRepository;
import com.hackerrank.stereotypes.repository.StaffRepository;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
  
    @Autowired
    StaffRepository staffRepository;

    @Autowired
    PatientRepository patientRepository;

    public Staff save(Staff person){
        try {
            Optional<Staff> user = staffRepository.findByEmail(person.getEmail());

            if(person.equals(null)){
                throw new NullPointerException("Staff cannot be null");
            } else if (user.isPresent()) {
                throw new DuplicateMemberException("This staff member is already present");
            }

            return staffRepository.save(person);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public Patient save(Patient person){
        try {
            Optional<Staff> appointedTo = staffRepository.findByEmail(person.getDoctorEmail());
            Optional<Patient> user = patientRepository.findByEmailAndStatus(person.getEmail(), "Admitted");

            if(person.equals(null)){
                throw new NullPointerException("Staff cannot be null");
            }
            else if(!appointedTo.isPresent()){
                throw new ValidationException("Doctor appointed to doesn't exist!");
            }


            if(person.getStatus().equals("Admitted")){
                Optional<Patient> room = patientRepository.findByRoomAndStatus(person.getRoom(), "Admitted");


                //Error if trying to allocate already allocated room for a new patient who comes to get Admitted
                if(room.isPresent()){
                    throw new ValidationException("Room is already in use by another Patient, Please select a different room.");
                }

                //Error if user is already present and is admitted
                if(user.isPresent() ){
                    throw new DuplicateMemberException("This patient is already admitted!");
                }

                //If user is aready present, and got discharged before, then only update the database
                user = patientRepository.findByEmailAndStatus(person.getEmail(), "Discharged");
                if(user.isPresent()){
                    user.get().setStatus("Admitted");
                    return patientRepository.save(user.get());
                }

                return patientRepository.save(person);
            }


            if(person.getStatus().equals("Discharged")){

                //Error if trying to Discharge a patient who is not already Admitted
                if(!user.isPresent()){
                    throw new ValidationException("This patient is not admitted, so can't Discharge!");
                }

                //If user present,
                if (user.isPresent())
                {
                    user.get().setStatus("Discharged");
                    System.out.println(user.get().getStatus());

                    return patientRepository.save(user.get());
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public Staff retrieveStaff(String email){

        try {

            Optional<Staff> user = staffRepository.findByEmail(email);
            if(user.isPresent()){
                return user.get();
            }
            else {
                throw new NullPointerException("Staff with this email ID, won't exist");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public List<Patient> retrievePatient(String status){
        return patientRepository.findAllByStatus(status);
    }

    public List<Patient> retrieveAllPatient(){
        return patientRepository.findAll();
    }


}
