package com.example.tuckbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditUser extends AppCompatActivity implements View.OnClickListener {

    private TextView registerUser, banner;
    private EditText textFullName, textAge, textEmail, textPassword;
    private ProgressBar progressBar;
    private Button update;
    private FirebaseAuth mAuth;
    private String userID;
    private FirebaseUser user;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        update = (Button) findViewById((R.id.updateUser));
        update.setOnClickListener(this);

        final EditText textFullName = (EditText) findViewById(R.id.fullName);
        final EditText textAge = (EditText) findViewById(R.id.age);
        final EditText textEmail = (EditText) findViewById(R.id.email);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    textFullName.setText(userProfile.fullName);
                    textAge.setText(userProfile.age);
                    textEmail.setText(userProfile.email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditUser.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateUser:
                update();
                break;
        }
    }

    private void update () {
        String fullName = textFullName.getText().toString().trim();
        String age = textAge.getText().toString().trim();
        String email = textEmail.getText().toString().trim();

        if (fullName.isEmpty()) {
            textFullName.setError("Full name is required!");
            textFullName.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            textAge.setError("Age is required!");
            textAge.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            textEmail.setError("Email is required!");
            textEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textEmail.setError("Please provide a valid email!");
            textEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.updateCurrentUser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditUser.this,"Update Complete",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(EditUser.this,Home.class));
                }else{
                    Toast.makeText(EditUser.this,"Try Again!Something went wrong!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
