package com.androidstrike.quizease.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.androidstrike.quizease.Model.RegisteredCourses;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class Database extends SQLiteAssetHelper {

//    database creaated to store the registered courses locally

    private final static String DB_NAME= "RegisteredCoursesDB.db";
    private final static int DB_VER= 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }


//    database table is created
    public List<RegisteredCourses> getCourses(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"ID","CourseTitle","CourseCode","CourseLecturer","CourseCredit"};
        String sqlTable = "RegisteredCourses";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        final List<RegisteredCourses> registeredCoursesList = new ArrayList<>();
        if (c.moveToFirst()){
            do {
                registeredCoursesList.add(new RegisteredCourses (c.getString(c.getColumnIndex("ID")),
                        c.getString(c.getColumnIndex("CourseTitle")),
                        c.getString(c.getColumnIndex("CourseCode")),
                        c.getString(c.getColumnIndex("CourseLecturer")),
                        c.getString(c.getColumnIndex("CourseCredit"))
                ));

            }while (c.moveToNext());
        }
        return registeredCoursesList;

    }

//    function to add registered courses to the database schema structure
    public void addToCourses (RegisteredCourses registeredCourses){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO RegisteredCourses (CourseTitle,CourseCode,CourseLecturer,CourseCredit) VALUES('%s','%s','%s','%s');",
                registeredCourses.getCourseTitle(),
                registeredCourses.getCourseCode(),
                registeredCourses.getCourseLecturer(),
                registeredCourses.getCourseCredit());
        db.execSQL(query);
    }


//    function to delete particular course from the database, could be invoked when the student is done with the semester
    public void cleanCourses(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM RegisteredCourses");
        db.execSQL(query);
    }
}
