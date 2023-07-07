package com.jbtg.exam.entity;

import jakarta.persistence.*;

@Entity
@Table( name = "employee" )
public class employee {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column ( name = "id" )
    private int id;
    @Column ( name = "first_name" )
    private String firstName;
    @Column ( name = "last_name" )
    private String lastName;
    @Column ( name = "nickname" )
    private String nickName;
    @Column ( name = "salary" )
    private int salary;
    @Column ( name = "status" )
    private String status;
    @Column ( name = "address" )
    private String address;
    @Column ( name = "position" )
    private String position;

    public employee(String firstName, String lastName, String nickName, int salary, String status, String address, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.salary = salary;
        this.status = status;
        this.address = address;
        this.position = position;
    }

    public employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
