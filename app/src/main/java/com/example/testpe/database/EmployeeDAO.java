package com.example.testpe.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.testpe.Employee;

import java.util.List;

@Dao
public interface EmployeeDAO {

    @Insert
    void insertEmployee(Employee employee);

    @Query("SELECT * FROM employee")
    List<Employee> getListEmployee();

    @Query("SELECT * FROM employee where name= :name")
    List<Employee> checkEmployee(String name);
}
