package com.example.testpe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testpe.database.EmployeeDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 10;
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

            @Override
            public void deleteEmployee(Employee employee) {
                clickDeleteEmployee(employee);
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

    private void clickDeleteEmployee(Employee employee) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete user?")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete user
                        EmployeeDatabase.getInstance(MainActivity.this).employeeDAO().deleteEmployee(employee);
                        Toast.makeText(MainActivity.this, "Delete user success", Toast.LENGTH_SHORT).show();

                        loadData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
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

        loadData();
        mListEmployee = EmployeeDatabase.getInstance(this).employeeDAO().getListEmployee();
        employeeAdapter.setData(mListEmployee);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            loadData();
        }
    }
    private void clickUpdateEmployee(Employee employee) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_employee", employee);
        intent.putExtras(bundle);
        startActivityForResult(intent, MY_REQUEST_CODE);
    }
}