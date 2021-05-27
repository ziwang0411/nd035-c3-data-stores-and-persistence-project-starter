package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getPhoneNumber(), customerDTO.getNotes());
        List<Long> petIds = customerDTO.getPetIds();

        CustomerDTO convertedCustomer;
        try {
            convertedCustomer = convertCustomerToCustomerDTO(customerService.saveCustomer(customer, petIds));
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer could not be saved", exception);
        }
        return convertedCustomer;    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> results = new ArrayList<>();
        for (Customer customer: customers) {
            results.add(convertCustomerToCustomerDTO(customer));
        }
        return results;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        CustomerDTO convertedCustomer;
        try {
            convertedCustomer = convertCustomerToCustomerDTO(customerService.getOwnerByPet(petId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer for pet with petId= "+petId+" could not be found", e);
        }
        return convertedCustomer;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO.getId(), employeeDTO.getName(), employeeDTO.getSkills(), employeeDTO.getDaysAvailable());
        EmployeeDTO convertedEmployee;
        try {
            convertedEmployee = convertEmployeeToEmployeeDTO(employeeService.saveEmployee(employee));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee could not be saved", e);
        }
        return convertedEmployee;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        EmployeeDTO employeeDTO;
        try {
            employeeDTO= convertEmployeeToEmployeeDTO(employeeService.getEmployee(employeeId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee with id: " + employeeId + " not found", e);
        }
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        try {
            employeeService.setAvailability(daysAvailable, employeeId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee with id: " + employeeId + " not found", e);
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> qualifiedEmployees = employeeService.findEmployeesForService(employeeDTO.getDate(), employeeDTO.getSkills());
        return qualifiedEmployees.stream().map(this::convertEmployeeToEmployeeDTO).collect(Collectors.toList());
    }
    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getSkills(), employee.getDaysAvailable());
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getPhoneNumber(), customer.getNotes(), petIds);
    }
}
