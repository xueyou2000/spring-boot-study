package com.xueyou.controller;

import com.xueyou.model.pojo.Customer;
import com.xueyou.repository.CustomerRepository;
import com.xueyou.service.CustomerService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;

/**
 * 创建 by xueyo on 2019/7/17
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    public CustomerController(CustomerRepository customerRepository,CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public Customer add(@RequestBody Customer customer) {
        return customerService.add(customer);
    }

    @GetMapping("/findByName")
    public Iterable<Customer> findByName(@RequestParam String firstName, @RequestParam String lastName) {
        return customerRepository.findByLastNameAndFirstName(lastName, firstName);
    }

    @GetMapping("/findFirstByFirstName")
    public Customer findFirstByFirstName(@RequestParam String firstName) {
        return customerRepository.findFirstByFirstName(firstName);
    }

    @PostMapping("/findAll")
    public Iterable<Customer> findAll(@RequestBody Customer customer) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("age", "createTime");
        return customerRepository.findAll(Example.of(customer, matcher));
    }

    @GetMapping("/all")
    public Iterable<Customer> all() {
        return customerRepository.findAll();
    }

}