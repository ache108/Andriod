package com.example.assessment_3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.assessment_3.databinding.ActivityReviewBinding;
import com.example.assessment_3.entity.Customer;
import com.example.assessment_3.viewmodel.CustomerViewModel;

import java.util.List;

import java.util.concurrent.CompletableFuture;

public class ReviewActivity extends AppCompatActivity {
    private ActivityReviewBinding binding;
    private CustomerViewModel customerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        binding = ActivityReviewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //menu view binding
        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewActivity.this, com.example.assessment_3.HomeActivity.class);
                startActivity(intent);

            }
        });


        binding.idTextField.setPlaceholderText("This is only used for Edit");
        //we make sure that AndroidViewModelFactory creates the view model so it can accept the Application as the parameter
        customerViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CustomerViewModel.class);
        customerViewModel.getAllCustomers().observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(@Nullable final List<Customer> customers) {
                String allCustomers = "";
                for (Customer temp : customers) {
                    String customerDetails = ("Id: " + temp.uid + " Name: " + temp.name + " Review: " + temp.review + " Rating: " + temp.rating);
                    allCustomers = allCustomers + System.getProperty("line.separator") + customerDetails;
                }
                binding.textViewRead.setText("Reviews: " + allCustomers);
            }
        });
        //add review
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name= binding.nameTextField.getEditText().getText().toString();
                String review=binding.reviewTextField.getEditText().getText().toString();
                String strrating=binding.ratingTextField.getEditText().getText().toString();
                double rating = Double.parseDouble(strrating);
                if ((!name.isEmpty() && name!= null) && (!review.isEmpty() && strrating!=null) && (!strrating.isEmpty() && review!=null)) {
                    //double rating = Double.parseDouble(strrating);
                    if (rating >=0 && rating <= 5) {
                        Customer customer = new Customer(name, review, rating);
                        customerViewModel.insert(customer);
                        binding.textViewAdd.setText("Added: " + name + " " + review + " " + rating);
                    }
                }
                if (name.isEmpty())
                {
                    String msg = "Name is empty";
                    toastMsg(msg);
                }
                if (review.isEmpty())
                {
                    String msg = "Review is empty";
                    toastMsg(msg);
                }
                if (strrating.isEmpty())
                {
                    String msg = "Rating is empty";
                    toastMsg(msg);
                }
                if (rating < 0 || rating > 5)
                {
                    String msg = "Rating must be between 0 and 5";
                    toastMsg(msg);
                }
            }});
        //delete review
        /*binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                customerViewModel.deleteAll();
                binding.textViewDelete.setText("All data was deleted");
            }
        });*/
        //clear review
        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.nameTextField.getEditText().setText("");
                binding.reviewTextField.getEditText().setText("");
                binding.ratingTextField.getEditText().setText("");
            }
        });
        //update review
        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strId =binding.idTextField.getEditText().getText().toString();
                int id=0;
                if (!strId.isEmpty() && strId!= null)
                    id=Integer.parseInt(strId);
                String name= binding.nameTextField.getEditText().getText().toString();
                String review=binding.reviewTextField.getEditText().getText().toString();
                String strrating=binding.ratingTextField.getEditText().getText().toString();
                double rating = Double.parseDouble(strrating);
                if ((!name.isEmpty() && name!= null)) {
                    //double rating = Double.parseDouble(strrating);
                    if (rating >=0 && rating <= 5) {
                        //this deals with versioning issues
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            CompletableFuture<Customer> customerCompletableFuture = customerViewModel.findByIDFuture(id);
                            customerCompletableFuture.thenApply(customer -> {
                                if (customer != null) {
                                    customer.name = name;
                                    customer.review = review;
                                    customer.rating = rating;
                                    customerViewModel.update(customer);
                                    binding.textViewUpdate.setText("Update was successful for ID: " + customer.uid);
                                } else {
                                    binding.textViewUpdate.setText("Id does not exist");
                                }
                                return customer;
                            });
                        }
                    }
                }
                if (name.isEmpty())
                {
                    String msg = "Name is empty";
                    toastMsg(msg);
                }
                if (review.isEmpty())
                {
                    String msg = "Review is empty";
                    toastMsg(msg);
                }
                if (strrating.isEmpty())
                {
                    String msg = "Rating is empty";
                    toastMsg(msg);
                }
                if (rating < 0 || rating > 5)
                {
                    String msg = "Rating must be between 0 and 5";
                    toastMsg(msg);
                }
            }
        });
    }
    public void toastMsg(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}