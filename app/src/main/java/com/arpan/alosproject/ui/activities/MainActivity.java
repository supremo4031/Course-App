package com.arpan.alosproject.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arpan.alosproject.R;
import com.arpan.alosproject.ui.fragments.Contact;
import com.arpan.alosproject.ui.fragments.Courses;
import com.arpan.alosproject.ui.fragments.Invite;
import com.arpan.alosproject.util.Others;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    private ActionBarDrawerToggle toggle;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFireStore;

    // Navigation Header Text
    private TextView name;

    // Navigation Footer Text
    private TextView signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI();
        setUpFirebase();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, new Courses()).commit();
            navigationView.setCheckedItem(R.id.courses);
        }

        signOut.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), LogInActivity.class));
            finish();
        });

        if (mUser != null) {
            mFireStore.collection("users")
                    .document(mUser.getUid())
                    .addSnapshotListener((value, error) -> {
                        if (value != null) {
                            Others.NAME = value.getString("Name");
                            Others.EMAIL = value.getString("Email");
                            name.setText(Others.NAME);
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mUser == null) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setUpFirebase() {

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFireStore = FirebaseFirestore.getInstance();
    }

    private void setUI() {

        // setting the toolbar
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // setting the drawer layout
        drawerLayout = findViewById(R.id.main_drawer_layout);

        // setting the navigationView
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View view = navigationView.getHeaderView(0);
        name = view.findViewById(R.id.header_name);

        signOut = findViewById(R.id.sign_out);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.courses: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, new Courses()).commit();
                item.setChecked(true);
                break;
            }
            case R.id.invite: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, new Invite()).commit();
                item.setChecked(true);
                break;
            }
            case R.id.contact: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, new Contact()).commit();
                item.setChecked(true);
                break;
            }
            default:
                return false;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}