package com.example.vinayaggarwal.registrationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginSucessfulActivity extends AppCompatActivity {
    View toolbarView;
    ImageView toolbarImv;
    EditText toolbarText;
    TextView username;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sucessful);
        toolbarView = (View) findViewById(R.id.toolbar_login_sucess);
        toolbarImv = (ImageView) findViewById(R.id.back_btn_toolbar);
        toolbarText = (EditText) findViewById(R.id.editText_toolbar);
        toolbarText.setText("Login Success");
        username = (TextView) findViewById(R.id.textView_login_sucess);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            value = extras.getString("userName");
        }
        username.setText(value);

        toolbarImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSucessfulActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(LoginSucessfulActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

}
