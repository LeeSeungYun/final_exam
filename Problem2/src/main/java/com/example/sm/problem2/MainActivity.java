package com.example.sm.problem2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyBaseAdapter adapter;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Employee> data = new ArrayList<>();

        adapter = new MyBaseAdapter(this, data);
        listview = (ListView) findViewById(R.id.listView1) ;
        listview.setAdapter(adapter);
        listview.setOnItemClickListener((AdapterView.OnItemClickListener)adapter);
    }
    @Override
    public void onClick(View v){
        EditText edit_name = (EditText) findViewById(R.id.edit_name);
        EditText edit_age = (EditText) findViewById(R.id.edit_age);
        EditText edit_salary = (EditText) findViewById(R.id.edit_salary);
        Employee employee = null;
        if(adapter.mData.size() != 0){
            employee = (Employee) adapter.mData.get(adapter.selected_position);
        }

        switch (v.getId()){
            case R.id.btn_inc:
                employee.increase();
                break;

            case R.id.btn_dec:
                employee.decrease();
                break;

            case R.id.btn_store:
                employee = new Employee(edit_name.getText().toString(),Integer.valueOf(edit_age.getText().toString()),
                        Integer.valueOf(edit_salary.getText().toString()));
                adapter.add(employee);
                break;

            case R.id.btn_modify:
                employee.setName(edit_name.getText().toString());
                employee.setAge(Integer.valueOf(edit_age.getText().toString()));
                employee.setSalary(Integer.valueOf(edit_salary.getText().toString()));
                break;

            case R.id.btn_delete:
                adapter.delete(adapter.selected_position);
                break;
        }
    }
}

interface Payment {
    void increase();
    void decrease();
}
