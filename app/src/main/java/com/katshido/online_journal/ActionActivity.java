package com.katshido.online_journal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.sql.SQLException;
import java.sql.Statement;

import static com.katshido.online_journal.MainActivity.con;
import static com.katshido.online_journal.MainActivity.school;

public class ActionActivity extends AppCompatActivity
{
    private int position;
    private ConstraintLayout content_action_employee, content_action_learner, content_action_teacher, content_action_class,
            content_action_add_learner_in_class, content_action_add_teacher_in_class, content_action_mark;
    private Statement st = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_action);
        receiveIntent();
        content_action_employee = findViewById(R.id.content_action_employee);
        content_action_learner = findViewById(R.id.content_action_learner);
        content_action_teacher = findViewById(R.id.content_action_teacher);
        content_action_class = findViewById(R.id.content_action_class);
        content_action_add_learner_in_class = findViewById(R.id.content_action_add_learner_in_class);
        content_action_add_teacher_in_class = findViewById(R.id.content_action_add_teacher_in_class);
        content_action_mark = findViewById(R.id.content_action_mark);
        switch (position)
        {
            case 0:
                content_action_employee.setVisibility(View.VISIBLE);
                content_action_learner.setVisibility(View.INVISIBLE);
                content_action_teacher.setVisibility(View.INVISIBLE);
                content_action_class.setVisibility(View.INVISIBLE);
                content_action_add_learner_in_class.setVisibility(View.INVISIBLE);
                content_action_add_teacher_in_class.setVisibility(View.INVISIBLE);
                content_action_mark.setVisibility(View.INVISIBLE);
                break;
            case 1:
                content_action_employee.setVisibility(View.INVISIBLE);
                content_action_learner.setVisibility(View.VISIBLE);
                content_action_teacher.setVisibility(View.INVISIBLE);
                content_action_class.setVisibility(View.INVISIBLE);
                content_action_add_learner_in_class.setVisibility(View.INVISIBLE);
                content_action_add_teacher_in_class.setVisibility(View.INVISIBLE);
                content_action_mark.setVisibility(View.INVISIBLE);
                break;
            case 2:
                content_action_employee.setVisibility(View.INVISIBLE);
                content_action_learner.setVisibility(View.INVISIBLE);
                content_action_teacher.setVisibility(View.VISIBLE);
                content_action_class.setVisibility(View.INVISIBLE);
                content_action_add_learner_in_class.setVisibility(View.INVISIBLE);
                content_action_add_teacher_in_class.setVisibility(View.INVISIBLE);
                content_action_mark.setVisibility(View.INVISIBLE);
                break;
            case 3:
                content_action_employee.setVisibility(View.INVISIBLE);
                content_action_learner.setVisibility(View.INVISIBLE);
                content_action_teacher.setVisibility(View.INVISIBLE);
                content_action_class.setVisibility(View.VISIBLE);
                content_action_add_learner_in_class.setVisibility(View.INVISIBLE);
                content_action_add_teacher_in_class.setVisibility(View.INVISIBLE);
                content_action_mark.setVisibility(View.INVISIBLE);
                break;
            case 4:
                content_action_employee.setVisibility(View.INVISIBLE);
                content_action_learner.setVisibility(View.INVISIBLE);
                content_action_teacher.setVisibility(View.INVISIBLE);
                content_action_class.setVisibility(View.INVISIBLE);
                content_action_add_learner_in_class.setVisibility(View.VISIBLE);
                content_action_add_teacher_in_class.setVisibility(View.INVISIBLE);
                content_action_mark.setVisibility(View.INVISIBLE);
                break;
            case 5:
                content_action_employee.setVisibility(View.INVISIBLE);
                content_action_learner.setVisibility(View.INVISIBLE);
                content_action_teacher.setVisibility(View.INVISIBLE);
                content_action_class.setVisibility(View.INVISIBLE);
                content_action_add_learner_in_class.setVisibility(View.INVISIBLE);
                content_action_add_teacher_in_class.setVisibility(View.VISIBLE);
                content_action_mark.setVisibility(View.INVISIBLE);
                break;
            case 6:
                content_action_employee.setVisibility(View.INVISIBLE);
                content_action_learner.setVisibility(View.INVISIBLE);
                content_action_teacher.setVisibility(View.INVISIBLE);
                content_action_class.setVisibility(View.INVISIBLE);
                content_action_add_learner_in_class.setVisibility(View.INVISIBLE);
                content_action_add_teacher_in_class.setVisibility(View.INVISIBLE);
                content_action_mark.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void receiveIntent()
    {
        Intent intent = getIntent();
        if (intent != null)
        {
            position = intent.getIntExtra("position", -1);
        }
    }

    public void onAddEmployee(View view)
    {
        EditText et1, et2, et3, et4;
        et1 = findViewById(R.id.edit_text_employee_card_id);
        et2 = findViewById(R.id.edit_text_employee_full_name);
        et3 = findViewById(R.id.edit_text_employee_phone);
        et4 = findViewById(R.id.edit_text_employee_position);
        int CardID;
        String FullName, Phone, Position;
        CardID = Integer.parseInt(et1.getText().toString());
        FullName = et2.getText().toString();
        Phone = et3.getText().toString();
        Position = et4.getText().toString();
        new Thread(() ->
        {
            try
            {
                st = con.createStatement();
                st.executeUpdate("INSERT INTO Employees " +
                        "(EmployeeCardID, EmployeeFullName, EmployeePhone, EmployeePosition) " +
                        "VALUES ('" + CardID + "', '" + FullName + "', '" + Phone + "', '" + Position + "')");
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Запись успешно добавлена", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            catch (SQLException throwables)
            {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Данные некорректны", Toast.LENGTH_SHORT).show();
                Looper.loop();
                throwables.printStackTrace();
            }
        }).start();

    }

    public void onAddLearner(View view)
    {
        EditText et1, et2, et3, et4, et5, et6;
        et1 = findViewById(R.id.edit_text_learner_card_id);
        et2 = findViewById(R.id.edit_text_learner_full_name);
        et3 = findViewById(R.id.edit_text_learner_login);
        et4 = findViewById(R.id.edit_text_learner_password);
        et5 = findViewById(R.id.edit_text_learner_phone);
        et6 = findViewById(R.id.edit_text_learner_age);
        int CardID, Age;
        String FullName, Login, Password, Phone;
        CardID = Integer.parseInt(et1.getText().toString());
        Age = Integer.parseInt(et6.getText().toString());
        FullName = et2.getText().toString();
        Login = et3.getText().toString();
        Password = et4.getText().toString();
        Phone = et5.getText().toString();
        new Thread(() ->
        {
            try
            {
                st = con.createStatement();
                st.executeUpdate("INSERT INTO Students " +
                        "(StudentCardID, StudentFullName, StudentLogin, StudentPassword, StudentPhone, StudentAge) " +
                        "VALUES ('" + CardID + "', '" + FullName + "', '" + Login + "', '" + Password + "', '" + Phone + "', '" + Age + "')");
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Запись успешно добавлена", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            catch (SQLException throwables)
            {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Данные некорректны", Toast.LENGTH_SHORT).show();
                Looper.loop();
                throwables.printStackTrace();
            }
        }).start();
    }

    public void onAddTeacher(View view)
    {
        EditText et1, et2, et3, et4, et5, et6, et7;
        et1 = findViewById(R.id.edit_text_teacher_card_id);
        et2 = findViewById(R.id.edit_text_teacher_full_name);
        et3 = findViewById(R.id.edit_text_teacher_login);
        et4 = findViewById(R.id.edit_text_teacher_password);
        et5 = findViewById(R.id.edit_text_teacher_phone);
        et6 = findViewById(R.id.edit_text_teacher_position);
        et7 = findViewById(R.id.edit_text_teacher_qualification);
        int CardID;
        String FullName, Login, Password, Phone, Position, Qualification;
        CardID = Integer.parseInt(et1.getText().toString());
        FullName = et2.getText().toString();
        Login = et3.getText().toString();
        Password = et4.getText().toString();
        Phone = et5.getText().toString();
        Position = et6.getText().toString();
        Qualification = et7.getText().toString();
        new Thread(() ->
        {
            try
            {
                st = con.createStatement();
                st.executeUpdate("INSERT INTO Teachers " +
                        "(TeacherCardID, TeacherFullName, TeacherLogin, TeacherPassword, TeacherPhone, TeacherPosition, TeacherQualification) " +
                        "VALUES ('" + CardID + "', '" + FullName + "', '" + Login + "', '" +
                        Password + "', '" + Phone + "', '" + Position + "', '" + Qualification + "')");
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Запись успешно добавлена", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            catch (SQLException throwables)
            {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Данные некорректны", Toast.LENGTH_SHORT).show();
                Looper.loop();
                throwables.printStackTrace();
            }
        }).start();
    }

    public void onAddClass(View view)
    {
        EditText et1;
        et1 = findViewById(R.id.edit_text_class_caption);
        String Caption;
        Caption = et1.getText().toString();
        new Thread(() ->
        {
            try
            {
                st = con.createStatement();
                st.executeUpdate("INSERT INTO Classes " +
                        "(ClassCaption) " +
                        "VALUES ('" + Caption + "')");
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Запись успешно добавлена", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            catch (SQLException throwables)
            {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Данные некорректны", Toast.LENGTH_SHORT).show();
                Looper.loop();
                throwables.printStackTrace();
            }
        }).start();
    }

    public void onAddRelationLearnerClass(View view)
    {
        EditText et1, et2;
        et1 = findViewById(R.id.edit_text_learner_class_learner_card_id);
        et2 = findViewById(R.id.edit_text_learner_class_class_caption);
        int ID;
        String Caption;
        ID = Integer.parseInt(et1.getText().toString());
        Caption = et2.getText().toString();
        new Thread(() ->
        {
            try
            {
                st = con.createStatement();
                st.executeUpdate("INSERT INTO RelationStudentClass " +
                        "(StudentID, ClassCaption) " +
                        "VALUES ('" + ID + "', '" + Caption + "')");
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Запись успешно добавлена", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            catch (SQLException throwables)
            {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Данные некорректны", Toast.LENGTH_SHORT).show();
                Looper.loop();
                throwables.printStackTrace();
            }
        }).start();
    }

    public void onAddRelationTeacherClass(View view)
    {
        EditText et1, et2;
        et1 = findViewById(R.id.edit_text_teacher_class_teacher_card_id);
        et2 = findViewById(R.id.edit_text_teacher_class_class_caption);
        int ID;
        String Caption;
        ID = Integer.parseInt(et1.getText().toString());
        Caption = et2.getText().toString();
        new Thread(() ->
        {
            try
            {
                st = con.createStatement();
                st.executeUpdate("INSERT INTO RelationTeacherClass " +
                        "(TeacherID, ClassCaption) " +
                        "VALUES ('" + ID + "', '" + Caption + "')");
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Запись успешно добавлена", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            catch (SQLException throwables)
            {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "Данные некорректны", Toast.LENGTH_SHORT).show();
                Looper.loop();
                throwables.printStackTrace();
            }
        }).start();
    }
}
