package com.tbcmad.todoapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tbcmad.todoapp.viewModel.TodoViewModel;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment fragment;
    FloatingActionButton floatingActionButton;

    String param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        fragment = new ListTodoFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.list_activity_container, fragment)
                .commit();

        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnu_delete_all:
                param = "Delete All?";
                ShowAlert(param,1);
              break;
            case R.id.mnu_delete_cpmpleted:
                param = "Delete Completed?";
                ShowAlert(param,2);
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    void ShowAlert(String parameter,int numbor){
        android.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(parameter)
                .setTitle(getString(R.string.app_name))
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userChose(numbor);
                    }

                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        alertDialog.show();


    }

    void userChose(int parameter_1)
    {
        if(parameter_1 == 1)
        {
            Toast.makeText(getApplicationContext(),"All Tasks Deleted", Toast.LENGTH_LONG).show();
            new ViewModelProvider(this).get(TodoViewModel.class).deleteAll();
        }
        else if (parameter_1 == 2)
        {
            Toast.makeText(getApplicationContext(),"Completed Tasks Deleted", Toast.LENGTH_LONG).show();
            new ViewModelProvider(this).get(TodoViewModel.class).deleteCompleted();
        }
        else if(parameter_1 ==3)
        {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("todo_pref", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

}