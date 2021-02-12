package com.katshido.online_journal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ConstraintLayout content_main, content_account, content_documents, content_online_journal, content_actions;
    public static Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    private int choice = -1;
    private boolean isEntered = false;
    private NavigationView navigationView;
    public static School school = new School();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list_documents = findViewById(R.id.list_documents);
        String[] documents_array = getResources().getStringArray(R.array.documents_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, documents_array);
        list_documents.setAdapter(adapter);

        list_documents.setOnItemClickListener((parent, view, position, id) ->
        {
            Intent intent = new Intent(MainActivity.this, DocumentActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        new Thread(() ->
        {
            DBConnection();
            try {
                formSchool();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }).start();


        /*new Thread((Runnable) this::DBConnection).start();
        try {
            formSchool();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/

        {
            content_main = findViewById(R.id.content_main);
            content_account = findViewById(R.id.content_account);
            content_documents = findViewById(R.id.content_documents);
            content_online_journal = findViewById(R.id.content_online_journal);
            content_actions = findViewById(R.id.content_actions);

            content_main.setVisibility(View.VISIBLE);
            content_account.setVisibility(View.INVISIBLE);
            content_documents.setVisibility(View.INVISIBLE);
            content_online_journal.setVisibility(View.INVISIBLE);
            content_actions.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        toolbar.setTitle(R.string.menu_main);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_main:
                toolbar.setTitle(R.string.menu_main);
                content_main.setVisibility(View.VISIBLE);
                content_account.setVisibility(View.INVISIBLE);
                content_documents.setVisibility(View.INVISIBLE);
                content_online_journal.setVisibility(View.INVISIBLE);
                content_actions.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_account:
                toolbar.setTitle(R.string.menu_account);
                content_main.setVisibility(View.INVISIBLE);
                content_account.setVisibility(View.VISIBLE);
                content_documents.setVisibility(View.INVISIBLE);
                content_online_journal.setVisibility(View.INVISIBLE);
                content_actions.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_documents:
                toolbar.setTitle(R.string.menu_documents);
                content_main.setVisibility(View.INVISIBLE);
                content_account.setVisibility(View.INVISIBLE);
                content_documents.setVisibility(View.VISIBLE);
                content_online_journal.setVisibility(View.INVISIBLE);
                content_actions.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_online_journal:
                toolbar.setTitle(R.string.menu_online_journal);
                content_main.setVisibility(View.INVISIBLE);
                content_account.setVisibility(View.INVISIBLE);
                content_documents.setVisibility(View.INVISIBLE);
                content_online_journal.setVisibility(View.VISIBLE);
                content_actions.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_actions:
                toolbar.setTitle(R.string.menu_actions);
                content_main.setVisibility(View.INVISIBLE);
                content_account.setVisibility(View.INVISIBLE);
                content_documents.setVisibility(View.INVISIBLE);
                content_online_journal.setVisibility(View.INVISIBLE);
                content_actions.setVisibility(View.VISIBLE);
                break;
        }
        return true;
    }

    public void onEnter(View view)
    {
        EditText edit_text_login, edit_text_password;
        edit_text_login = findViewById(R.id.edit_text_login);
        edit_text_password = findViewById(R.id.edit_text_password);
        String entered_login = edit_text_login.getText().toString();
        String entered_password = edit_text_password.getText().toString();

        new Thread(() ->
        {
            try
            {
                switch (choice)
                {
                    case 0:
                    {
                        st = con.createStatement();
                        rs = st.executeQuery("SELECT * FROM Students WHERE StudentLogin = '" + entered_login + "'");
                        if (rs.next())
                        {
                            if (rs.getString("StudentPassword").equals(entered_password))
                            {
                                runOnUiThread(() ->
                                {
                                    TextView login, status;
                                    login = findViewById(R.id.login);
                                    status = findViewById(R.id.status);
                                    try {
                                        login.setText(rs.getString("StudentFullName"));
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    status.setText(R.string.student_choice);
                                    isEntered = true;
                                });

                            }
                            else
                            {
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), "Неверный пароль", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                        else
                        {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "Неверный логин", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                    break;
                    case 1:
                    {
                        st = con.createStatement();
                        rs = st.executeQuery("SELECT * FROM Teachers WHERE TeacherLogin = '" + entered_login + "'");
                        if (rs.next())
                        {
                            if (rs.getString("TeacherPassword").equals(entered_password))
                            {
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        TextView login, status;
                                        login = findViewById(R.id.login);
                                        status = findViewById(R.id.status);
                                        try {
                                            login.setText(rs.getString("TeacherFullName"));
                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        }
                                        status.setText(R.string.teacher_choice);
                                        isEntered = true;
                                    }
                                });

                            }
                            else
                            {
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), "Неверный пароль", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                        else
                        {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "Неверный логин", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                    break;
                    case 2:
                    {
                        if (entered_login.equals(getApplicationContext().getString(R.string.root_login)))
                        {
                            if (entered_password.equals(getApplicationContext().getString(R.string.root_password)))
                            {
                                runOnUiThread(() ->
                                {
                                    TextView login, status;
                                    login = findViewById(R.id.login);
                                    status = findViewById(R.id.status);
                                    login.setText(R.string.director_full_name);
                                    status.setText(R.string.director_choice);
                                    isEntered = true;
                                });
                            }
                            else
                            {
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), "Неверный пароль", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                        else
                        {
                            Looper.prepare();
                            Toast.makeText(getApplicationContext(), "Неверный логин", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                    break;
                    default:
                    {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "Вы не выбрали статус", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    break;
                }
            }
            catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
            runOnUiThread(() ->
            {
                if (isEntered)
                {
                    edit_text_login.setEnabled(false);
                    edit_text_password.setEnabled(false);
                    Button button, button_popup;
                    button = findViewById(R.id.button);
                    button_popup = findViewById(R.id.button_popup);
                    button.setEnabled(false);
                    button_popup.setEnabled(false);
                    navigationView.getMenu().findItem(R.id.nav_account).setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Вы успешно вошли в аккаунт", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            });
        }).start();
    }

    public void DBConnection()
    {
        final String MSSQL_DB = "jdbc:jtds:sqlserver://katshido.database.windows.net:1433;databaseName=MS_BD;" +
                "encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;" +
                "loginTimeout=30;Authentication=ActiveDirectoryIntegrated";
        final String MSSQL_LOGIN = "KatShiDo@katshido";
        final String MSSQL_PASS= "20030117ybrbnF";

        try
        {
            java.lang.Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = null;
            try
            {
                con = DriverManager.getConnection(MSSQL_DB, MSSQL_LOGIN, MSSQL_PASS);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void onPopUp(View view)
    {
        Button button_popup;
        button_popup = findViewById(R.id.button_popup);
        PopupMenu popup = new PopupMenu(MainActivity.this, button_popup);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            public boolean onMenuItemClick(MenuItem item)
            {
                button_popup.setText(item.getTitle());
                CharSequence title = item.getTitle();
                if ("Ученик".contentEquals(title)) choice = 0;
                else if ("Учитель".contentEquals(title)) choice = 1;
                else if ("Директор".contentEquals(title)) choice = 2;
                return true;
            }
        });
        popup.show();
    }

    public void formSchool() throws SQLException
    {
        school.Address = getResources().getString(R.string.school_address);
        school.Name = getResources().getString(R.string.school_name);
        st = con.createStatement();
        rs = st.executeQuery("SELECT EmployeeCardID, EmployeeFullName, EmployeePhone, EmployeePosition FROM Employees");
        int i = 0;
        school.Employees = new Employee[0];
        while (rs.next())
        {
            int CardID;
            String FullName, Phone, Position;
            CardID = rs.getInt("EmployeeCardID");
            FullName = rs.getString("EmployeeFullName");
            Phone = rs.getString("EmployeePhone");
            Position = rs.getString("EmployeePosition");

            school.Employees = Arrays.copyOf(school.Employees, school.Employees.length + 1);
            school.Employees[i] = new Employee(CardID, FullName, Phone, Position);
            i++;
        }

        rs = st.executeQuery("SELECT * FROM Classes");
        school.Classes = new Class[0];
        i = 0;
        while (rs.next())
        {
            school.Classes = Arrays.copyOf(school.Classes, school.Classes.length + 1);
            school.Classes[i] = new Class(rs.getString("ClassCaption"));
            i++;
        }

        rs = st.executeQuery("SELECT " +
                "t1.StudentCardID, " +
                "t1.StudentFullName, " +
                "t1.StudentPhone, " +
                "t1.StudentAge, " +
                "t3.ClassCaption, " +
                "t4.ParentFullName, " +
                "t4.ParentPhone " +
                "FROM Students t1 " +
                "JOIN RelationStudentClass t2 " +
                "ON t1.StudentCardID = t2.StudentID " +
                "JOIN Classes t3 " +
                "ON t3.ClassCaption = t2.ClassCaption " +
                "JOIN Parents t4 " +
                "ON t1.StudentCardID = t4.StudentID");
        i = -1;
        int j = 0, TempStudentID = 0, k = 0;
        String TempClassCaption = null;
        school.Learners = new Learner[0];
        while (rs.next())
        {
            int CardID, Age;
            String FullName, Phone;
            CardID = rs.getInt("StudentCardID");
            FullName = rs.getString("StudentFullName");
            Phone = rs.getString("StudentPhone");
            Age = rs.getInt("StudentAge");
            if (rs.getInt("StudentCardID") != TempStudentID)
            {
                j = 0; k = 0; i++;
                school.Learners = Arrays.copyOf(school.Learners, school.Learners.length + 1);
                school.Learners[i] = new Learner(CardID, FullName, Phone, Age);

                school.Learners[i].Parents = new Parent[0];
                school.Learners[i].Parents = Arrays.copyOf(school.Learners[i].Parents, school.Learners[i].Parents.length + 1);
                school.Learners[i].Parents[j] = new Parent(rs.getString("ParentFullName"), rs.getString("ParentPhone"));

                school.Learners[i].ClassCaptions = new String[0];
                school.Learners[i].ClassCaptions = Arrays.copyOf(school.Learners[i].ClassCaptions, school.Learners[i].ClassCaptions.length + 1);
                school.Learners[i].ClassCaptions[k] = rs.getString("ClassCaption");
                j++; k++;
            }
            else if (rs.getString("ClassCaption").equals(TempClassCaption))
            {
                school.Learners[i].Parents = Arrays.copyOf(school.Learners[i].Parents, school.Learners[i].Parents.length + 1);
                school.Learners[i].Parents[j] = new Parent(rs.getString("ParentFullName"), rs.getString("ParentPhone"));
                j++;
            }
            else
            {
                school.Learners[i].ClassCaptions = Arrays.copyOf(school.Learners[i].ClassCaptions, school.Learners[i].ClassCaptions.length + 1);
                school.Learners[i].ClassCaptions[k] = rs.getString("ClassCaption");
                k++;
            }
            TempStudentID = rs.getInt("StudentCardID");
            TempClassCaption = rs.getString("ClassCaption");
        }
        for (i = 0; i < school.Learners.length; i++)
        {
            for (j = 0; j < school.Learners[i].ClassCaptions.length; j++)
            {
                for (k = 0; k < school.Classes.length; k++)
                {
                    if (school.Classes[k].Number.equals(school.Learners[i].ClassCaptions[j]))
                    {
                        school.Classes[k].Learners = new Learner[0];
                        school.Classes[k].Learners = Arrays.copyOf(school.Classes[k].Learners, school.Classes[k].Learners.length + 1);
                        school.Classes[k].Learners[school.Classes[k].Learners.length - 1] = school.Learners[i];
                    }
                }
            }
        }

        rs = st.executeQuery("SELECT " +
                "t1.TeacherCardID, " +
                "t1.TeacherFullName, " +
                "t1.TeacherPhone, " +
                "t1.TeacherPosition, " +
                "t1.TeacherQualification, " +
                "t3.ClassCaption " +
                "FROM Teachers t1 " +
                "JOIN RelationTeacherClass t2 " +
                "ON t1.TeacherCardID = t2.TeacherID " +
                "JOIN Classes t3 " +
                "ON t3.ClassCaption = t2.ClassCaption");
        i = 0;
        school.Teachers = new Teacher[0];
        while (rs.next())
        {
            int CardID;
            String FullName, Phone, Position, Qualification;
            CardID = rs.getInt("TeacherCardID");
            FullName = rs.getString("TeacherFullName");
            Phone = rs.getString("TeacherPhone");
            Position = rs.getString("TeacherPosition");
            Qualification = rs.getString("TeacherQualification");

            school.Teachers = Arrays.copyOf(school.Teachers, school.Teachers.length + 1);
            school.Teachers[i] = new Teacher(CardID, FullName, Phone, Position, Qualification);
            i++;
        }
    }
}