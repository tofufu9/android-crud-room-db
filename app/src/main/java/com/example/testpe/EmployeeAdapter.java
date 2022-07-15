package com.example.testpe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.UserViewHolder> {

    private List<Employee> mListEmployee;
    private IClickItemEmployee iClickItemEmployee;

    public interface IClickItemEmployee {
        void updateEmployee(Employee employee);

        void deleteEmployee(Employee employee);
    }

    public EmployeeAdapter(IClickItemEmployee iClickItemEmployee) {
        this.iClickItemEmployee = iClickItemEmployee;
    }

    public void setData(List<Employee> list){
        this.mListEmployee = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final Employee employee = mListEmployee.get(position);
        if(employee == null){
            return;
        }
        holder.tvId.setText(employee.getEmpid());
        holder.tvName.setText(employee.getName());
        holder.tvAge.setText(employee.getAge());

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemEmployee.updateEmployee(employee);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            iClickItemEmployee.deleteEmployee(employee);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListEmployee != null){
            return mListEmployee.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView tvId;
        private TextView tvName;
        private TextView tvAge;
        private Button btnUpdate;
        private Button btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAge = itemView.findViewById(R.id.tv_age);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);

        }
    }
}
