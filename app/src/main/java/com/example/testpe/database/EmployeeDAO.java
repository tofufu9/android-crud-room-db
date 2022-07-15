package com.example.testpe.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Update
    void updateEmployee(Employee employee);
    @Delete
    void deleteEmployee(Employee employee);

}
