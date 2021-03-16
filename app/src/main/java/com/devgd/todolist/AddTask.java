package com.devgd.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;


import com.devgd.todolist.databinding.ActivityAddTaskBinding;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    private ActivityAddTaskBinding binding;
    int id,year_intent,month_intent,day_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        //spinner
        String[] prioritylist = {"high", "medium", "low"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.textspinnerlayout, prioritylist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        binding.spinnerid.setAdapter(dataAdapter);


        //CHECKING INTENT
        Intent intent=getIntent();
        if(intent.getStringExtra("task")!=null){
            // update button
            binding.idadd.setText(R.string.update_button);
            id=intent.getIntExtra("id",0);





            binding.taskid.setText(intent.getStringExtra("task"));
            int spinnerpostition;
            if(intent.getStringExtra("priority").equals("high")){
                spinnerpostition=0;
            }
            else if(intent.getStringExtra("priority").equals("medium")){
                spinnerpostition=1;
            }
            else {
                spinnerpostition=2;
            }

            binding.spinnerid.setSelection(spinnerpostition);
            binding.iddate.setText(intent.getStringExtra("date"));
            binding.radioGroup.check(R.id.radioButton2);
            //checking radiobutton
            if(intent.getStringExtra("date").equals("no due date")){
                binding.radioGroup.check(R.id.radioButton1);
            }


        }
        //set calender
        year_intent=intent.getIntExtra("year",0);
        month_intent=intent.getIntExtra("month",0);
        day_intent=intent.getIntExtra("day",0);

        //add button
        binding.idadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        //radio button
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               if(checkedId==R.id.radioButton2) {
                   DialogFragment datePicker = new DatePickerFragment();
                   datePicker.show(getSupportFragmentManager(), "date picker");

               }
               else if(checkedId==R.id.radioButton1){
                   binding.iddate.setText("no due date");
               }

            }
        });





    }


    public void save(){

        Intent data = new Intent();
        data.putExtra("task",binding.taskid.getText().toString());
        data.putExtra("date",binding.iddate.getText().toString());
        data.putExtra("priority",binding.spinnerid.getSelectedItem().toString());
        data.putExtra("id",id);
        data.putExtra("year",year_intent);
        data.putExtra("month",month_intent);
        data.putExtra("day",day_intent);


        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        year_intent=year;
        month_intent=month;
        day_intent=dayOfMonth;

        binding.iddate.setText(currentDateString);

    }
}