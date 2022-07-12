package com.example.testpe;

import android.app.Activity;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testpe.database.EmployeeDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtId;
    private EditText edtName;
    private EditText edtAge;
    private Button btnAddEmployee;
    private RecyclerView rcvEmployee;

    private EmployeeAdapter employeeAdapter;
    private List<Employee> mListEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

        employeeAdapter = new EmployeeAdapter(new EmployeeAdapter.IClickItemEmployee() {
            @Override
            public void updateEmployee(Employee employee) {
                clickUpdateEmployee(employee);
            }
        });
        mListEmployee = new ArrayList<>();
        employeeAdapter.setData(mListEmployee);

        LinearLayoutManager LinearLayoutManager = new LinearLayoutManager(this);
        rcvEmployee.setLayoutManager(LinearLayoutManager);

        rcvEmployee.setAdapter(employeeAdapter);

        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEmployee();
            }
        });
        loadData();
    }

    private void clickUpdateEmployee(Employee employee) {

    }

    private void AddEmployee() {
        String strId = edtId.getText().toString().trim();
        String strName = edtName.getText().toString().trim();
        String strAge = edtAge.getText().toString().trim();

        if(TextUtils.isEmpty(strId) || TextUtils.isEmpty(strName) || TextUtils.isEmpty(strAge)){
            return;
        }

        Employee employee = new Employee(strId, strName, strAge);

        if(isEmployeeExist(employee)){
            Toast.makeText(this, "Employee exist!", Toast.LENGTH_SHORT).show();
        }

        EmployeeDatabase.getInstance(this).employeeDAO().insertEmployee(employee);
        Toast.makeText(this, "Add Employee Successfully", Toast.LENGTH_SHORT).show();

        edtId.setText("");
        edtName.setText("");
        edtAge.setText("");

        hideSoftKeyboard();


    }

    public void hideSoftKeyboard(){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        } catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }

    private void initUi() {
        edtId = findViewById(R.id.edt_id);
        edtName = findViewById(R.id.edt_name);
        edtAge = findViewById(R.id.edt_age);
        btnAddEmployee = findViewById(R.id.btn_add_employee);
        rcvEmployee = findViewById(R.id.rcv_employee);
    }

    private void loadData(){
        mListEmployee = EmployeeDatabase.getInstance(this).employeeDAO().getListEmployee();
        employeeAdapter.setData(mListEmployee);
    }
    private boolean isEmployeeExist(Employee employee){
        List<Employee> list = EmployeeDatabase.getInstance(this).employeeDAO().checkEmployee(employee.getName());
        return list!= null && !list.isEmpty();
    }
}