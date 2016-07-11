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

public class RegistrationActivity extends AppCompatActivity {
    View toolbarView;
    ImageView toolbarImv;
    EditText toolbarText;
    EditText firstName_et, lastName_et, address_et_1, address_et_2, email_et, password_et, pincode_et, mobile_et;
    String firstName, lastName, address1, address2, email, password, pincode, mobile;
    AlertDialog dialog;
    AlertDialog.Builder bulider;
    Button save_btn;
    String reg_url = "http://citimagic.com/index.php/api/user/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        toolbarView = (View) findViewById(R.id.toolbar_registration);
        toolbarImv = (ImageView) findViewById(R.id.back_btn_toolbar);
        toolbarText = (EditText) findViewById(R.id.editText_toolbar);

        firstName_et = (EditText) findViewById(R.id.edit_first_name);
        lastName_et = (EditText) findViewById(R.id.edit_last_name);
        address_et_1 = (EditText) findViewById(R.id.edit_address1);
        address_et_2 = (EditText) findViewById(R.id.edit_address2);
        email_et = (EditText) findViewById(R.id.edit_email);
        password_et = (EditText) findViewById(R.id.edit_password);
        pincode_et = (EditText) findViewById(R.id.edit_pincode);
        mobile_et = (EditText) findViewById(R.id.edit_mobile_no);
        save_btn = (Button) findViewById(R.id.edit_save_btn);

        bulider = new AlertDialog.Builder(RegistrationActivity.this);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = firstName_et.getText().toString();
                lastName = firstName_et.getText().toString();
                address1 = firstName_et.getText().toString();
                address2 = firstName_et.getText().toString();
                email = firstName_et.getText().toString();
                password = firstName_et.getText().toString();
                pincode = firstName_et.getText().toString();
                mobile = firstName_et.getText().toString();

                if (firstName.isEmpty() || lastName.isEmpty() || address1.isEmpty() || address2.isEmpty() || email.isEmpty() || password.isEmpty() || pincode.isEmpty() || mobile.isEmpty()) {
                    bulider.setMessage("All the fileds are mendatory");
                    displayAlert("empty");
                } else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String mStatus = jsonObject.getString("status");
                                String mMessage = jsonObject.getString("msg");
//                              bulider.setMessage(mMessage);
//                              displayAlert(mStatus);
                                if (mStatus.equals("Fail")) {
                                    Toast.makeText(RegistrationActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                                    firstName_et.setText("");
                                    lastName_et.setText("");
                                    address_et_1.setText("");
                                    address_et_2.setText("");
                                    email_et.setText("");
                                    password_et.setText("");
                                    pincode_et.setText("");
                                    mobile_et.setText("");
                                } else if (mStatus.equals("Success")) {
                                    Toast.makeText(RegistrationActivity.this, jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
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
                            map.put("user_email", email);
                            map.put("user_password", password);
                            map.put("user_mobile", mobile);
                            map.put("firstname", firstName);
                            map.put("lastname", lastName);
                            map.put("address1", address1);
                            map.put("address2", address2);
                            map.put("pin_code", pincode);
                            return map;
                        }
                    };
                    MyConnection.getInstance(RegistrationActivity.this).addToRequestque(stringRequest);


//                    Toast.makeText(RegistrationActivity.this,"registered Sucessfully",Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
//                    startActivity(intent);
//                    finish();

                }
            }
        });

        toolbarText.setText("Registration");
        toolbarImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void displayAlert(final String code) {
        bulider.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (code.equals("Empty")) {
                    dialog.dismiss();
                } else if (code.equals("Success")) {
                    finish();
                } else if (code.equals("Fail")) {

                    firstName_et.setText("");
                    lastName_et.setText("");
                    address_et_1.setText("");
                    address_et_2.setText("");
                    email_et.setText("");
                    password_et.setText("");
                    pincode_et.setText("");
                    mobile_et.setText("");

                }

            }

        });
        AlertDialog alertDialog = bulider.create();
        alertDialog.show();
    }


}
