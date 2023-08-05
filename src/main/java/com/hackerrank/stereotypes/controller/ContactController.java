package com.hackerrank.stereotypes.controller;

import com.hackerrank.stereotypes.model.Patient;

import com.hackerrank.stereotypes.model.Staff;
import com.hackerrank.stereotypes.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContactController {

  @Autowired  
  ContactService contactService;

    @PostMapping( "/staff/save")
    public ResponseEntity<Staff> save(@RequestBody Staff person){
        Staff saved = contactService.save(person);
        return new ResponseEntity(saved, HttpStatus.CREATED);
    }

    @PostMapping( "/patient/save")
    public ResponseEntity<Patient> save(@RequestBody Patient person){
        Patient saved = contactService.save(person);
        return new ResponseEntity(saved, HttpStatus.CREATED);
    }

    @GetMapping( "/patient/retrieve/")
    public ResponseEntity<Patient> retrieveAllPatient(){
        List<Patient> person = contactService.retrieveAllPatient();
        return new ResponseEntity(person, HttpStatus.OK);
    }

    @GetMapping( "/staff/retrieve/{email}")
    public ResponseEntity<Staff> retrieveStaff(@PathVariable("email") String email){
        Staff person = contactService.retrieveStaff(email);
        return new ResponseEntity(person, HttpStatus.OK);
    }

    @GetMapping( "/patient/retrieve/{status}")
    public ResponseEntity<Patient> retrievePatient(@PathVariable("status") String status){
        List<Patient> person = contactService.retrievePatient(status);
        return new ResponseEntity(person, HttpStatus.OK);
    }


}
