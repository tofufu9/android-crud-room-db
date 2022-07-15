package com.example.testpe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testpe.database.EmployeeDatabase;

public class UpdateActivity extends AppCompatActivity {

    private EditText edtId;
    private EditText edtName;
    private EditText edtAge;
    private Button btnUpdateEmployee;

    private Employee mEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edtId = findViewById(R.id.edt_id);
        edtName = findViewById(R.id.edt_name);
        edtAge = findViewById(R.id.edt_age);
        btnUpdateEmployee = findViewById(R.id.btn_update_employee);

        mEmployee = (Employee) getIntent().getExtras().get("object_employee");
        if (mEmployee != null){
            edtId.setText(mEmployee.getEmpid());
            edtName.setText(mEmployee.getName());
            edtAge.setText(mEmployee.getAge());
        }

        btnUpdateEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee();
            }
        });
    }

    private void updateEmployee() {
        String strId = edtId.getText().toString().trim();
        String strName = edtName.getText().toString().trim();
        String strAge = edtAge.getText().toString().trim();

        if(TextUtils.isEmpty(strId) || TextUtils.isEmpty(strName) || TextUtils.isEmpty(strAge)){
            return;
        }

        //Update employee
        mEmployee.setEmpid(strId);
        mEmployee.setName(strName);
        mEmployee.setAge(strAge);

        EmployeeDatabase.getInstance(this).employeeDAO().updateEmployee(mEmployee);
        Toast.makeText(this, "Update user successfully", Toast.LENGTH_SHORT).show();

        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK, intentResult);
        finish();
    }
}