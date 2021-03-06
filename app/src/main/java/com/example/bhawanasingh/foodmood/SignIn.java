package com.example.bhawanasingh.foodmood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bhawanasingh.foodmood.Common.Common;
import com.example.bhawanasingh.foodmood.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText  edtPassword,edtPhone;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtPassword = findViewById(R.id.edtPassword);
        edtPhone = findViewById(R.id.edtPhone);
        btnSignIn = findViewById(R.id.btnSignIn);
        //init firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
       final DatabaseReference table_user = database.getReference("User");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                 mDialog.setMessage("please wait ...");
                 mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check if user not exist in database
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {

                            //get user information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            assert user != null;
                            user.setPhone(edtPhone.getText().toString()); //set phone no.
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                {
                                    Intent homeIntent = new Intent(SignIn.this,Home.class);
                                    Common.setCurrentUser(user);
                                    startActivity(homeIntent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(SignIn.this, "Wrong Password !!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    else

                    {
                        mDialog.dismiss();
                        Toast.makeText(SignIn.this, "User not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
