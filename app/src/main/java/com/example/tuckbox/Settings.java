package com.example.tuckbox;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private Button logout,update;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        user = FirebaseAuth.getInstance().getCurrentUser();

        //identifying buttons
        logout = (Button) findViewById(R.id.signOut);
        logout.setOnClickListener(this);

        update = (Button) findViewById(R.id.updateUser);
        update.setOnClickListener(this);

        //Nav bar initialization
        BottomNavigationView botnav = findViewById(R.id.bottom_navigation);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.updateUser:
                startActivity(new Intent(this,EditUser.class));
        }
    }
}