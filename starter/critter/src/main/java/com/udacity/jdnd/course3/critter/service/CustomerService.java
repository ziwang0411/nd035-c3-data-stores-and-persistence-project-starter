package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;


    public Customer saveCustomer(Customer customer, List<Long> petIds) throws RuntimeException{
        List<Pet> petList = new ArrayList<>();
        if (petIds!=null && !petIds.isEmpty()) {
            for (Long id: petIds) {
                Optional<Pet> optionalPet=petRepository.findById(id);
                if (optionalPet.isPresent()) {
                    petList.add(optionalPet.get());
                } else {
                    throw new RuntimeException("one Pet not found for this customer");
                }
            }
        }
        customer.setPets(petList);
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(long petId) {
        Optional<Pet> optionalPet= petRepository.findById(petId);
        if (!optionalPet.isPresent()) {
            throw new RuntimeException("cannot find pet!");
        }
        return optionalPet.get().getCustomer();
    }

}
