package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {
    @Id
    @GeneratedValue
    private Long id;

    private PetType petType;

    @Nationalized
    private String name;
    @ManyToOne(fetch= FetchType.LAZY, targetEntity = Customer.class)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private LocalDate birthDate;

    private String notes;

    public Pet() {
    }

    public Pet(Long id, PetType petType, String name, Customer customer, LocalDate birthDate, String notes) {
        this.id = id;
        this.petType = petType;
        this.name = name;
        this.customer = customer;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public Pet(PetType type, String name, LocalDate birthDate, String notes) {
        this.petType = petType;
        this.name = name;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
