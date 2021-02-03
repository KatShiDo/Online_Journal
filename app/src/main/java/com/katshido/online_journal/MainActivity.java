package com.katshido.online_journal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ConstraintLayout content_main, content_account, content_documents, content_online_journal, content_actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        toolbar.setTitle(R.string.menu_main);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        Editable login = edit_text_login.getText();
        Editable password = edit_text_password.getText();

    }
}