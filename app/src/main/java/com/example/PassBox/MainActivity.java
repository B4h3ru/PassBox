package com.example.PassBox;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.PassBox.AddFragment.AddFragment;
import com.example.PassBox.HomeFragment.HomeFragment;
import com.example.PassBox.SettingFragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                    if(item.getItemId()== R.id.navigation_home) {
//                        Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                        loadFragment( new HomeFragment());
                        return true;
                    }
                    else  if(item.getItemId()== R.id.navigation_add) {
//                        Toast.makeText(MainActivity.this, "Add clicked", Toast.LENGTH_SHORT).show();
                        loadFragment( new AddFragment());
                        return true;
                    }
                    else  if(item.getItemId()== R.id.navigation_setting) {
//                        Toast.makeText(MainActivity.this, "Settings clicked", Toast.LENGTH_SHORT).show();
                        loadFragment( new SettingFragment());
                        return true;
                    }
                return false;
            }
        });

    }
    // Inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handle menu item click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void loadFragment(Fragment fragment) {  //fragment loader
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit();
    }
}