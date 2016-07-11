package com.example.vinayaggarwal.registrationapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    View toolbarView;
    ImageView backToolbarImv;
    EditText mTitle, username_et, password_et;
    Button login;
    TextView signupText;
    String username, password;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    String login_url = "http://citimagic.com/index.php/api/user/authenticate ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbarView = (View) findViewById(R.id.toolbar_view);
        backToolbarImv = (ImageView) findViewById(R.id.back_btn_toolbar);
        backToolbarImv.setVisibility(View.GONE);
        mTitle = (EditText) findViewById(R.id.editText_toolbar);
        signupText = (TextView) findViewById(R.id.SignUp_textView);
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mTitle.setText("Login Page");
        username_et = (EditText) findViewById(R.id.edit_username_login);
        password_et = (EditText) findViewById(R.id.edit_password_login);
        login = (Button) findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_et.getText().toString();
                password = password_et.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "All field are mendatory", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                            .setCancelable(false)
                            .setMessage("All field are mandatory")
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    alertDialog = builder.create();
                    alertDialog.show();
                } else {


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String getuserName = jsonObject.getString("status");
                                if (getuserName.equals("Fail")) {
                                    Toast.makeText(MainActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(MainActivity.this, LoginSucessfulActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("userName", username);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> map = new HashMap<String, String>();
                            map.put("username", username);
                            map.put("password", password);

                            return map;
                        }
                    };
                    MyConnection.getInstance(MainActivity.this).addToRequestque(stringRequest);

                }
            }
        });
    }
}
