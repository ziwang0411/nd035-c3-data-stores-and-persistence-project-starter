package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet(Pet pet, long ownerId) {
        Customer customer = customerRepository.getOne(ownerId);
        List<Pet> pets = customer.getPets();
        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        pets.add(pet);
        customer.setPets(pets);
        customerRepository.save(customer);
        return pet;
    }

    public Pet getPet(long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> getPetsByCustomerId(long ownerId) {
        return petRepository.findByCustomerId(ownerId);
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }
}
