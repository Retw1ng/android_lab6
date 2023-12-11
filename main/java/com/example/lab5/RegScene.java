package com.example.lab5;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class RegScene extends Activity {
    TextView changeTextPrev;
    TextView errorMessage;
    Button mainBtn;
    EditText inputName;
    EditText inputPassword;
    User user;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.enter_scene);
        final Context a = RegScene.this.getApplicationContext();

        changeTextPrev = findViewById(R.id.enterTEXT);
        mainBtn = findViewById(R.id.regenterBtn);

        inputName = findViewById(R.id.inputName);
        inputPassword = findViewById(R.id.inputPassword);

        errorMessage = findViewById(R.id.errorMessage);
        //Intent intent = new Intent(this, InitScene.class);
        Bundle changeMode = this.getIntent().getExtras();

        int flag = changeMode.getInt("mode");
        Intent intent1 = new Intent(this, MainScene.class);

        if(flag == 1){
            changeTextPrev.setText("Регистрация");
            mainBtn.setText("Регистрация");

            mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputName.getText().toString().trim().equals("") || inputPassword.getText().toString().trim().equals("")) {
                    errorMessage.setText("Заполните поля регистрации");
                    errorMessage.setVisibility(View.VISIBLE);
                }else {
                    user = new User(inputName.getText().toString().trim(), inputPassword.getText().toString().trim());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("Thread1", "start");

                            boolean check = user.checkName(db);

                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if(check){
                                //if(user.checkName(db)){

                                errorMessage.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        errorMessage.setText("Пользователь с таким именем уже существует!");
                                        errorMessage.setVisibility(View.VISIBLE);
                                    }
                                });

                            /*}else if(user.checkPass()){
                                errorMessage.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        errorMessage.setText("Пароль менее 8 символов!");
                                        errorMessage.setVisibility(View.VISIBLE);
                                    }
                                });*/

                            }else {
                                errorMessage.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        errorMessage.setText("Регистрация прошла успешно!");
                                        errorMessage.setVisibility(View.VISIBLE);
                                    }
                                });

                                Log.i("MESSAGE", "onClick: USER ADD");

                                db.addUser(user);
                                db.close();

                                intent1.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                intent1.putExtra("user", user);
                                a.startActivity(intent1);
                                // startActivity(intent1);

                                /*inputName.post(new Runnable() {

                                @Override
                                public void run() {
                                    inputName.setText(String.valueOf(user.checkName(db)));
                                    try {
                                        Thread.sleep(10000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });*/

                            }
                        Log.i("Thread1", "Down");
                        }
                    }).start();
                }
            }
            });
        }else {
            mainBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User logUser = new User(inputName.getText().toString().trim(), inputPassword.getText().toString().trim());
                    if(logUser.getLogin().equals("") || logUser.getPass().equals("")) {
                        errorMessage.setText("Заполните поля!");
                        errorMessage.setVisibility(View.VISIBLE);
                    }else if(CheckUser(logUser)){
                        db.close();
                        intent1.putExtra("user", logUser);
                        startActivity(intent1);
                    }else{
                        errorMessage.setText("Неверный логин или пароль!");
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public boolean CheckUser(User user) {
        return db.selectUserData(user);
    }
}
