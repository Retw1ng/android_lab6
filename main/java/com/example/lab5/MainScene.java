package com.example.lab5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import androidx.annotation.Nullable;

public class MainScene extends Activity implements View.OnClickListener {
    TextView userName;
    Button mycollection;
    Button exit;
    Button deleteUser;
    Button changePassword;
    Button accept;
    EditText newPassword;
    TextView errorMessage;

    Intent intent;
    Intent intent1;

    DatabaseHandler db = new DatabaseHandler(this);
    User user = new User();


    FutureTask<Void> future1;
    Callable task;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_scene);
        userName = findViewById(R.id.userName);

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            user = (User) arguments.getSerializable("user");
        }
        userName.setText(user.getLogin());

        mycollection = findViewById(R.id.showmycollection);
        mycollection.setOnClickListener(this);

        exit = findViewById(R.id.exitButton);
        exit.setOnClickListener(this);
        deleteUser = findViewById(R.id.deleteUserButton);
        deleteUser.setOnClickListener(this);
        changePassword = findViewById(R.id.changePassword);
        changePassword.setOnClickListener(this);
        accept = findViewById(R.id.acceptNewPassword);
        accept.setOnClickListener(this);

        newPassword = findViewById(R.id.newPassword);
        errorMessage = findViewById(R.id.errorNewPassword);

        intent = new Intent(this, InitScene.class);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.showmycollection:
                intent1 = new Intent(this, PlantsList.class);
                startActivity(intent1);
                break;

            case R.id.exitButton:
                startActivity(intent);
                break;

            case R.id.deleteUserButton:
                task = () -> {
                    Log.i("Thread2", "start");
                    //System.out.println("new thread: delete user from db");
                    db.deleteUserFromDB(user);
                    db.close();
                    return null;
                };
                future1 = new FutureTask<>(task);
                new Thread(future1).start();
                startActivity(intent);
                break;

            case R.id.changePassword:
                newPassword.setVisibility(View.VISIBLE);
                accept.setVisibility(View.VISIBLE);
                break;

            case R.id.acceptNewPassword:

                String newPwd = newPassword.getText().toString();
                System.out.println("new " + newPwd);
                System.out.println(user.getPass());
                if(newPwd.equals("")){
                    errorMessage.setText("Введите новый пароль!");
                    errorMessage.setVisibility(View.VISIBLE);
                }else if(newPwd.equals(user.getPass())){
                    errorMessage.setText("Пароли совпадают!");
                    errorMessage.setVisibility(View.VISIBLE);
                }else {
                    errorMessage.setText("Пароль успешно изменен!");
                    errorMessage.setVisibility(View.VISIBLE);
                    newPassword.setVisibility(View.INVISIBLE);
                    accept.setVisibility(View.INVISIBLE);

                    db.updateUserPassword(user, newPwd);
                    db.close();
                }
                break;

            default:
                break;
        }
    }
}
