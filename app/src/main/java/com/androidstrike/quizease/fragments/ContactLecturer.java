package com.androidstrike.quizease.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstrike.quizease.Model.Lecturers;
import com.androidstrike.quizease.R;
import com.androidstrike.quizease.RecyclerItemTouchHelper;
import com.androidstrike.quizease.ViewHolder.LecturerContactVHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactLecturer extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private DatabaseReference lecturerContact;

    CoordinatorLayout coordinatorLayout;

    private RecyclerView recycler_lecturers;
    private Paint p = new Paint();

    private FirebaseRecyclerAdapter<Lecturers, LecturerContactVHolder> contactAdapter;

    public ContactLecturer(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_lecturer, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        lecturerContact = database.getReference("Lecturer_Contact");

        coordinatorLayout = v.findViewById(R.id.layout_contact_lecturer_coordinate);

        recycler_lecturers = v.findViewById(R.id.recycler_contacts);
        recycler_lecturers.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recycler_lecturers.setLayoutManager(layoutManager);
        recycler_lecturers.setItemAnimator(new DefaultItemAnimator());
        recycler_lecturers.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

//        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, (RecyclerItemTouchHelper.RecyclerItemTouchHelperListener) this.getContext());
//        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recycler_lecturers);


        loadContacts();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    Snackbar snackbar = Snackbar.make(
                            coordinatorLayout, "Swipe Call Implemented", Snackbar.LENGTH_LONG
                    );
                    snackbar.setActionTextColor(Color.CYAN);
                    snackbar.show();
                }else {
                    Snackbar snackbar = Snackbar.make(
                            coordinatorLayout, "Swipe Mail Implemented", Snackbar.LENGTH_LONG
                    );
                    snackbar.setActionTextColor(Color.CYAN);
                    snackbar.show();
                }

            }
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_baseline_pedal_bike_24);
                        if (icon== null){
                            Log.e("EQUA", "onChildDrawPhone: icon is null");
                            return;
                        }else {
                            RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
//                        c.drawBitmap(icon,null,p);
                            c.drawBitmap(icon,null,icon_dest,p);
                        }
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
//                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_email_white_24);
                        icon = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.ic_baseline_outdoor_grill_24);
                        if (icon== null){
                            Log.e("EQUA", "onChildDrawMail: icon is null");
                            return;
                        }else {
                            RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                            c.drawBitmap(icon,null,icon_dest,p);
                        }

//                        c.drawBitmap(icon,null,p);
//                        Toast.makeText(getActivity(), "Swiped", Toast.LENGTH_SHORT).show();
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }


        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback1);
        itemTouchHelper.attachToRecyclerView(recycler_lecturers);

        return v;
    }

    private void loadContacts() {
        contactAdapter = new FirebaseRecyclerAdapter<Lecturers, LecturerContactVHolder>(
                Lecturers.class, R.layout.contact_lecturer_item, LecturerContactVHolder.class, lecturerContact) {
            @Override
            protected void populateViewHolder(LecturerContactVHolder lecturerContactVHolder, final Lecturers lecturers, int i) {

                lecturerContactVHolder.txtLecturerName.setText(lecturers.getLecturerName());
                lecturerContactVHolder.txtLecturerCourse.setText(lecturers.getLecturerCourse());
                lecturerContactVHolder.txtLecturerMail.setText(lecturers.getLecturerEmail());
                lecturerContactVHolder.txtLecturerPhone.setText(lecturers.getLecturerPhone());
            }
        };
        recycler_lecturers.setAdapter(contactAdapter);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Snackbar snackbar = Snackbar.make(
                coordinatorLayout, "Swipe Implemented", Snackbar.LENGTH_LONG
        );
        snackbar.setActionTextColor(Color.CYAN);
        snackbar.show();
    }
}