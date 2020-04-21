package com.androidstrike.quizease;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.androidstrike.quizease.Common.Common;
import com.androidstrike.quizease.Database.Database;
import com.androidstrike.quizease.Model.RegisteredCourses;
import com.androidstrike.quizease.ViewHolder.CourseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference registeredCourses;

    private AppBarConfiguration mAppBarConfiguration;

    TextView txtFullName, txtRegNo, txtRegisteredCourse, txtCourseDetailClick, txtCreditTotal;
    DrawerLayout drawer;
    private  NavigationView nv;
    private ActionBarDrawerToggle toggle;

    List<RegisteredCourses> registeredCoursesList = new ArrayList<>();

    CourseAdapter courseAdapter;

    private AddCourses addCoursesFragment;

    MediaPlayer themeSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        themeSong = MediaPlayer.create(this, R.raw.adamsfam);
        themeSong.start();

//        Firebase Init
        database = FirebaseDatabase.getInstance();
        registeredCourses = database.getReference("RegisteredCourses");

        recyclerView = findViewById(R.id.recycler_courses);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtRegisteredCourse = findViewById(R.id.txt_registered_course);
        txtCourseDetailClick = findViewById(R.id.txt_course_detail_click);
        txtCreditTotal = findViewById(R.id.txt_course_credit_total);

        loadListRegCourses();

        addCoursesFragment = new AddCourses();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Courses");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, addCoursesFragment)
                        .commit();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_courses, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();

//        set name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullName= headerView.findViewById(R.id.txtFullName);
        txtRegNo = headerView.findViewById(R.id.txtRegNo);

        txtFullName.setText(Common.currentUser.getUsername());
        txtRegNo.setText(Common.currentUser.getRegNo());
    }

    private void loadListRegCourses() {
        registeredCoursesList = new Database(this).getCourses();
        courseAdapter = new CourseAdapter(registeredCoursesList, this);
        recyclerView.setAdapter(courseAdapter);

//        calculate total credit units
        int total = 0;
        for (RegisteredCourses registeredCourses:registeredCoursesList)
            total+=(Integer.parseInt(registeredCourses.getCourseCredit()));
        txtCreditTotal.setText(String.valueOf(total));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
