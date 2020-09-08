package com.androidstrike.quizease.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import com.androidstrike.quizease.Model.RegisteredCourses;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class Database extends SQLiteAssetHelper {

//    database created to store the registered courses locally

    private final static String DB_NAME= "RegisteredCoursesDatabase.db";
    private final static int DB_VER= 1;

    private String sqlTable = "RegisteredCourses";
    public static int _id;

    Context _mContext;


    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
        _mContext = context;
    }


//    database table is created
    public List<RegisteredCourses> getCourses(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"CourseCode","CourseTitle","CourseLecturer","CourseCredit"};
        sqlTable = "RegisteredCourses";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        final List<RegisteredCourses> registeredCoursesList = new ArrayList<>();
        if (c.moveToFirst()){
//            _id = c.getInt(c.getColumnIndex("CourseCode"));
            do {
                registeredCoursesList.add(new RegisteredCourses (c.getString(c.getColumnIndex("CourseCode")),
                        c.getString(c.getColumnIndex("CourseTitle")),
                        c.getString(c.getColumnIndex("CourseLecturer")),
                        c.getString(c.getColumnIndex("CourseCredit"))
                ));
            }while (c.moveToNext());
//            _id = c.getInt(c.getColumnIndex("CourseCode"));
        }
        return registeredCoursesList;

    }

//    function to add registered courses to the database schema structure
    public void addToCourses (RegisteredCourses registeredCourses){
        Log.d("EQUA", "addToCourses: " );
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(sqlTable, new String[]{"CourseCode"}, null, null, null, null, null);
        String[] columnNames = c.getColumnNames();
        String columnNameshaha =registeredCourses.getCourseCode();


        //check for uniqueness of course
        boolean contains = false;
        while (c.moveToNext()){
            String code_present = c.getString(c.getColumnIndex("CourseCode"));
            if (code_present.equals(columnNameshaha)){
                contains = true;
                            Log.e("EQUA", "User tried to register an already registered course");
                Toast.makeText(_mContext, "Course Already Registered!", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if (!contains){
            //register course to db if unique
            Log.e("EQUA", "addToCourses: Course Added");
            String query = String.format("INSERT INTO RegisteredCourses (CourseCode,CourseTitle,CourseLecturer,CourseCredit) VALUES('%s','%s','%s','%s'); ",
                    registeredCourses.getCourseCode(),
                    registeredCourses.getCourseTitle(),
                    registeredCourses.getCourseLecturer(),
                    registeredCourses.getCourseCredit());
            db.execSQL(query);

            Toast.makeText(_mContext, "Course Added!", Toast.LENGTH_SHORT).show();


        }
    }


//    function to delete particular course from the database, could be invoked when the student is done with the semester or the student does not want to register that course
    public void cleanCourses(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM RegisteredCourses");
        db.execSQL(query);
    }
}
