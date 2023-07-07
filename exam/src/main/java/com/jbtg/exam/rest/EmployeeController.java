package com.jbtg.exam.rest;

import com.jbtg.exam.entity.employee;
import com.jbtg.exam.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository;
    private EntityManager entityManager;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, EntityManager entityManager) {
        this.entityManager=entityManager;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<employee>> getAllEmployee(){
        return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getEmployeeById(@PathVariable("id") int id){
        Optional<employee> employeeById = employeeRepository.findById(id);
        if(employeeById.isEmpty()){
            return   ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "Not found employee ID: "+id);
        }
        else{
            return  ResponseEntity.status( HttpStatus.OK ).body( employeeById);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> addEmployee(@RequestBody employee employee){
        employee.setStatus("current");
        employeeRepository.save(employee);
        return  ResponseEntity.status( HttpStatus.OK ).body( employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id){
        Optional<employee> employeeById = employeeRepository.findById(id);
        if(employeeById.isEmpty()){
            return   ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "Not found employee ID: "+id);
        }
        else{
            employeeById.get().setStatus("deleted");
            return  ResponseEntity.status( HttpStatus.NO_CONTENT).body("");
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> editEmployee(@RequestBody employee employee){
        Optional<employee> employeeById = employeeRepository.findById(employee.getId());
        employeeById.get().setFirstName(employee.getFirstName());
        employeeById.get().setLastName(employee.getLastName());
        employeeById.get().setNickName(employee.getNickName());
        employeeById.get().setAddress(employee.getAddress());
        employeeRepository.save(employeeById.get());
        return  ResponseEntity.status( HttpStatus.OK).body(employeeById.get());
    }

    @PutMapping("/salary/{id}")
    public ResponseEntity<?> editEmployeeSalary(@PathVariable("id") int id,@RequestParam int percent){
        Optional<employee> employeeById = employeeRepository.findById(id);
        int plusPercent = employeeById.get().getSalary()*percent;
        employeeById.get().setSalary(employeeById.get().getSalary()+plusPercent);
        employeeRepository.save(employeeById.get());
        return  ResponseEntity.status( HttpStatus.OK).body(employeeById.get());
    }

    @PutMapping("/position/{id}")
    public ResponseEntity<?> editEmployeePosition(@PathVariable("id") int id,@RequestParam String oldPosition, @RequestParam String newPosition){
        Optional<employee> employeeById = employeeRepository.findById(id);
        if(employeeById.get().getPosition().equals(oldPosition)){
            employeeById.get().setPosition(newPosition);
            employeeRepository.save(employeeById.get());
            return  ResponseEntity.status( HttpStatus.OK).body(employeeById.get());
        }
        return  ResponseEntity.status( HttpStatus.BAD_REQUEST).body("Current position is incorrect");
    }

    @GetMapping("/name")
    public ResponseEntity<?> findEmployeeByName(@RequestParam String q){
        TypedQuery<employee> query = entityManager.createQuery("FROM employee WHERE firstName LIKE :search", employee.class);
        query.setParameter("search", q);
        return ResponseEntity.status( HttpStatus.OK).body(query.getResultList());
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deletemanyEmployee(@RequestParam int[] idList){
        System.out.println(idList);
        ArrayList<Integer> notFindList = new ArrayList<>();
        for (int j : idList) {
            Optional<employee> employeeById = employeeRepository.findById(j);
            if (employeeById.isEmpty()) {
                notFindList.add(j);
            } else {
                System.out.println("TEST");
                employeeById.get().setStatus("deleted");
                employeeRepository.save(employeeById.get());
            }
        }
        if(notFindList.size() == 0){
            return ResponseEntity.status( HttpStatus.NO_CONTENT).body("");
        }else{
            return ResponseEntity.status( HttpStatus.MULTI_STATUS).body("not_found_ids:"+notFindList);
        }

    }

}
