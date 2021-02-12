package com.katshido.online_journal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.Result;

import static com.katshido.online_journal.MainActivity.school;

public class DocumentActivity extends AppCompatActivity
{
    private int position;
    private TextView text_caption;
    private TextView text_document;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_document);
        receiveIntent();
        text_caption = findViewById(R.id.text_caption);
        text_document = findViewById(R.id.text_document);
        switch (position)
        {
            case 0:
                formTeachersList();
                break;
            case 1:
                formLearnersList();
                break;
            case 2:
                formParticipantsList();
                break;
            case 3:
                formLearnersWithParentsList();
                break;
            case 4:
                formEmployeesList();
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

    public void formTeachersList()
    {
        text_caption.setText("Список преподавательского состава");
        text_document.setText(school.getListTeachers());
    }

    public void formLearnersList()
    {
        text_caption.setText("Список школьников");
        text_document.setText(school.getListLearners());
    }

    public void formParticipantsList()
    {
        text_caption.setText("Список людей, имеющих доступ в школу");
        text_document.setText(school.getParticipants());
    }

    public void formEmployeesList()
    {
        text_caption.setText("Список сотрудников");
        text_document.setText(school.getListEmployees());
    }

    public void formLearnersWithParentsList()
    {
        text_caption.setText("Список учеников класса вместе с родителями");
        text_document.setText(school.getListLearnersWithParents());
    }
}
