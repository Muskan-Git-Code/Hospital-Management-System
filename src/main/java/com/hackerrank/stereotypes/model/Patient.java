package com.hackerrank.stereotypes.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer age;

    private String email;

    private String phone;

    private String room;

    private String doctorName;

    private String doctorEmail;

    @CreatedDate
    private String admitDate;

    private Integer expenses;

    private String status = "Admitted";

    // Constructors, getters, setters, etc.
    public Patient() {
    }

    public Patient(String name, Integer age, String email, String phone, String room, String doctorName, String doctorEmail, Integer expenses, String status) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.room = room;
        this.doctorName = doctorName;
        this.doctorEmail = doctorEmail;
        this.expenses = expenses;
        this.status = status;
    }

    public Patient(String name, Integer age, String email, String phone, String room, String doctorName, String doctorEmail, String admitDate, Integer expenses, String status) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.room = room;
        this.doctorName = doctorName;
        this.doctorEmail = doctorEmail;
        this.admitDate = admitDate;
        this.expenses = expenses;
        this.status = status;
    }

    public Patient(Integer id, String name, Integer age, String email, String phone, String room, String doctorName, String doctorEmail, String admitDate, Integer expenses, String status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.room = room;
        this.doctorName = doctorName;
        this.doctorEmail = doctorEmail;
        this.admitDate = admitDate;
        this.expenses = expenses;
        this.status = status;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getAdmitDate() {
        return admitDate;
    }

    public void setAdmitDate(String admitDate) {
        this.admitDate = admitDate;
    }

    public Integer getExpenses() {
        return expenses;
    }

    public void setExpenses(Integer expenses) {
        this.expenses = expenses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
