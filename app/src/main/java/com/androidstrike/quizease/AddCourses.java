package com.androidstrike.quizease;

import android.app.FragmentManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.androidstrike.quizease.R;
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

    private DatabaseReference newCourseList;

    private RecyclerView recycler_courses;

    private String courseId = "";
    private RegisteredCourses currentCourse;
//    FloatingActionButton btnAdd;

    Button btnAdd;

    private FirebaseRecyclerAdapter<Courses, AddCoursesViewHolder> coursesAdapter;

    private List<String> suggestList = new ArrayList<>();
    private MaterialSearchBar materialSearchBar;

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        newCourseList = database.getReference("Courses");

//        Load Course list
        recycler_courses = v.findViewById(R.id.recycler_new_courses);
        recycler_courses.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recycler_courses.setLayoutManager(layoutManager);

      loadCourseList();

        return v;
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
                addCoursesViewHolder.txtCourseTitle.setText(courses.getTitle());
                addCoursesViewHolder.txtCourseCredit.setText(courses.getCredit());


                final Courses local = courses;
                addCoursesViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

//                        String columnNameshaha = currentCourse.getCourseCode();
//                        if (String.valueOf(columnNameshaha).equals(String.valueOf(courses.getCourseCode()))){
//                            Log.e("EQUA", "addToCourses: "+ columnNameshaha + "compare to: "+String.valueOf(courses.getCourseCode()) );
//
//                            Toast.makeText(getActivity(), "Course Already Registered", Toast.LENGTH_SHORT).show();
//                        }else
//                            {

                        new Database(getContext()).addToCourses(new RegisteredCourses(

//                                currentCourse.getCourseTitle(),
//                                currentCourse.getCourseCode(),
//                                currentCourse.getCourseCredit()
                                courses.getCourseCode(),
                                courses.getTitle(),
                                courses.getLecturer(),
                                courses.getCredit()
                        ));


                        SubHomeFragment subHomeFragment = new SubHomeFragment();
//                            com.androidstrike.quizease.fragments.Courses coursesFragment = new com.androidstrike.quizease.fragments.Courses();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, subHomeFragment, "findListCourses")
                                    .addToBackStack(null)
                                    .commit();

                            getActivity().getSupportFragmentManager()
                                    .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                        }
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
