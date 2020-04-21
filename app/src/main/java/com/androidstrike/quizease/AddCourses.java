package com.androidstrike.quizease;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidstrike.quizease.Database.Database;
import com.androidstrike.quizease.Interface.ItemClickListener;
import com.androidstrike.quizease.Model.Courses;
import com.androidstrike.quizease.Model.RegisteredCourses;
import com.androidstrike.quizease.ViewHolder.AddCoursesViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCourses extends Fragment {

    FirebaseDatabase database;
    DatabaseReference newCourseList;

    RecyclerView recycler_courses;
    RecyclerView.LayoutManager layoutManager;

    String courseId = "";
    RegisteredCourses currentCourse;
//    FloatingActionButton btnAdd;

    Button btnAdd;

    FirebaseRecyclerAdapter<Courses, AddCoursesViewHolder> coursesAdapter;

//    Search Functionality
    FirebaseRecyclerAdapter<Courses, AddCoursesViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    AddCoursesViewHolder addCoursesViewHolder;

    public AddCourses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_courses, container, false);

//        Firebase init
        database = FirebaseDatabase.getInstance();
        newCourseList = database.getReference("Courses");

//        Load Course list
        recycler_courses = v.findViewById(R.id.recycler_new_courses);
        recycler_courses.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recycler_courses.setLayoutManager(layoutManager);

//        btnAdd = v.findViewById(R.id.button_add_course);

//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

      loadCourseList();



//        Search
        materialSearchBar = v.findViewById(R.id.searchBar);
        materialSearchBar.setHint("Enter Food");

        loadSuggest(); //function to load suggestions from firebase

        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<String>();
                for (String search:suggestList){
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
//                when search bar is closed restore original adapter
                if (!enabled)
                    recycler_courses.setAdapter(coursesAdapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

//                when search is finished show results of search adapter
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


        return v;
    }

    private void startSearch(CharSequence text){
        searchAdapter = new FirebaseRecyclerAdapter<Courses, AddCoursesViewHolder>(
                Courses.class,
                R.layout.course_reg_item,
                AddCoursesViewHolder.class,
                newCourseList.orderByChild("Title").equalTo(text.toString()) // compares to names in database
        ) {
            @Override
            protected void populateViewHolder(AddCoursesViewHolder addCoursesViewHolder, final Courses courses, int i) {
                addCoursesViewHolder.txtCourseCode.setText(courses.getId());
                addCoursesViewHolder.txtCourseTitile.setText(courses.getTitle());
                addCoursesViewHolder.txtCourseCredit.setText(courses.getCredit());
                Picasso.get().load(courses.getImage())
                        .into(addCoursesViewHolder.ivCourseImage);


                final Courses local = courses;
                addCoursesViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        set what happens when user clicks a course...fetch from cart fab button
                        Toast.makeText(getActivity(), courses.getTitle().toString() + " added", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
        recycler_courses.setAdapter(searchAdapter);
    }

    private void loadSuggest(){
        newCourseList.orderByChild("courseId").equalTo(courseId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                            Courses item = postSnapshot.getValue(Courses.class);
                            suggestList.add(item.getTitle()); //Add title of course to suggest list
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadCourseList(){
        coursesAdapter = new FirebaseRecyclerAdapter<Courses, AddCoursesViewHolder>(
                Courses.class, R.layout.course_reg_item, AddCoursesViewHolder.class,newCourseList
        ) {
            @Override
            protected void populateViewHolder(AddCoursesViewHolder addCoursesViewHolder, final Courses courses, int i) {
                addCoursesViewHolder.txtCourseCode.setText(courses.getId());
                Picasso.get().load(courses.getImage())
                        .into(addCoursesViewHolder.ivCourseImage);
                addCoursesViewHolder.txtCourseTitile.setText(courses.getTitle());
                addCoursesViewHolder.txtCourseCredit.setText(courses.getCredit());


                final Courses local = courses;
                addCoursesViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        new Database(getContext()).addToCourses(new RegisteredCourses(
                                courseId,
//                                currentCourse.getCourseTitle(),
//                                currentCourse.getCourseCode(),
//                                currentCourse.getCourseCredit()
                                courses.getTitle(),
                                courses.getCourseCode(),
                                courses.getLecturer(),
                                courses.getCredit()
                        ));

                        Toast.makeText(getActivity(), "Course Added!!", Toast.LENGTH_SHORT).show();
//                    }
                    }
                });

//                addCoursesViewHolder.addButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        getCourseDetail();
//
//
//                });

            }
        };

        Log.d("TAG", ""+coursesAdapter.getItemCount());
        recycler_courses.setAdapter(coursesAdapter);

    }

    private void getCourseDetail(){

                newCourseList.child(courseId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        currentCourse = dataSnapshot.getValue(RegisteredCourses.class);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

}
