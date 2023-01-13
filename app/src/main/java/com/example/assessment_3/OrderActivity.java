package com.example.assessment_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;


import com.example.assessment_3.databinding.ActivityOrderBinding;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private ActivityOrderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        EditText addressEditText = findViewById(R.id.address);
        //spinner for pizza
        List<String> list = new ArrayList<String>();
        list.add("Hawaiian");
        list.add("Supreme");
        list.add("BBQ Chicken");
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this ,android.R.layout.simple_spinner_item, list);
        binding.PizzaSpinner.setAdapter(spinnerAdapter);

        binding.PizzaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPizza = parent.getItemAtPosition(position).toString();
                if(selectedPizza != null){
                    Toast.makeText(parent.getContext(), "Pizza selected is " + selectedPizza,
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //spinner for drinks
        List<String> list2 = new ArrayList<String>();
        list2.add("Coke");
        list2.add("Fanta");
        list2.add("Water");
        final ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(this ,android.R.layout.simple_spinner_item, list2);
        binding.DrinkSpinner.setAdapter(spinnerAdapter2);

        binding.DrinkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDrink = parent.getItemAtPosition(position).toString();
                if(selectedDrink != null){
                    Toast.makeText(parent.getContext(), "Drink selected is " + selectedDrink,
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //menu view binding
        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, com.example.assessment_3.HomeActivity.class);
                startActivity(intent);

            }
        });
        //check for empty address
        binding.delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_Address = addressEditText.getText().toString();
                if (TextUtils.isEmpty(txt_Address)) {
                    String msg = "Empty Address";
                    toastMsg(msg);
                } else {
                    Intent intent = new Intent(OrderActivity.this, DeliveryActivity.class);
                    startActivity(intent);
                }

            }
        });

    }
    public void toastMsg(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }
}