package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class ScheduleService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Schedule createSchedule(List<Long> petIds, List<Long> employeeIds, Set<EmployeeSkill> activities, LocalDate date) {
        List<Pet> pets = petRepository.findAllById(petIds);
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        Schedule schedule = new Schedule(date, activities);
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getEmployeeSchedule(long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        List<Schedule> schedules = scheduleRepository.findByEmployees(employee);
        return schedules;
    }

    public List<Schedule> getPetSchedule(long petId) {
        Pet pet = petRepository.getOne(petId);
        List<Schedule> schedules = scheduleRepository.findByPets(pet);
        return schedules;
    }

    public List<Schedule> getCustomerSchedule(long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        List<Schedule> schedules = scheduleRepository.findByPetsIn(customer.getPets());
        return schedules;
    }
}
