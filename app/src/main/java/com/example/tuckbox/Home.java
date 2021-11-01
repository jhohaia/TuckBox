package com.example.tuckbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    private Button order, update, history;
    private BottomNavigationView botnav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Nav bar initialization
        botnav = findViewById(R.id.bottom_navigation);
        botnav.setOnClickListener(this);
        botnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuHome:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menuOrders:
                        startActivity(new Intent(getApplicationContext(), Order.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menuSettings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        //Identifying Buttons

        order = (Button) findViewById(R.id.placeOrder);
        order.setOnClickListener(this);

        update = (Button) findViewById(R.id.updateUser);
        update.setOnClickListener(this);

        history = (Button) findViewById(R.id.orderHistory);
        history.setOnClickListener(this);

        //Getting Username from Firebase Database
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();
        final TextView userText = (TextView) findViewById(R.id.userText);
        final TextView welcomeMessage = (TextView) findViewById(R.id.message);
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String name = userProfile.fullName;
                    userText.setText("Hey, " + name);
                    welcomeMessage.setText("whats on the menu today?");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Button page routing
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.signOut:
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(Home.this, MainActivity.class));
//                break;
            case R.id.placeOrder:
                startActivity(new Intent(this, Order.class));
                break;
            case R.id.updateUser:
                startActivity(new Intent(this,EditUser.class));
        }
    }
}
