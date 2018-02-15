package com.example.omara.trackit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class driver_registration extends AppCompatActivity {
    ImageButton reg, bk, profile_pic;
    int Request = 1;
    private Bitmap bitmap;
    EditText username, password, password2, name, email, age, mobile, company, address, ssn, dln;
    Uri picUri;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);
        username = (EditText) findViewById(R.id.editText15);
        password = (EditText) findViewById(R.id.editText16);
        password2 = (EditText) findViewById(R.id.editText17);
        name = (EditText) findViewById(R.id.editText19);
        email = (EditText) findViewById(R.id.editText18);
        mobile = (EditText) findViewById(R.id.editText24);
        company = (EditText) findViewById(R.id.editText23);
        address = (EditText) findViewById(R.id.editText25);
        ssn = (EditText) findViewById(R.id.editText26);
        dln = (EditText) findViewById(R.id.editText27);
        profile_pic = (ImageButton) findViewById(R.id.imageButton7);
        reg = (ImageButton) findViewById(R.id.imageButton8);
        bk = (ImageButton) findViewById(R.id.imageButton9);
        final ProgressDialog pd  = new ProgressDialog(driver_registration.this);
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent g = new Intent(Intent.ACTION_GET_CONTENT);
                g.setType("image/*");
                startActivityForResult(g, Request);
            }
        });
        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setCancelable(true);
                pd.setMessage("Please Wait .... ");
                pd.show();
                final String rdName = name.getText().toString();
                final String rdUsername = username.getText().toString();
                final String rdPassword = password.getText().toString();
                final String rdPassword2 = password2.getText().toString();
                final String rdEmail = email.getText().toString();
                final int rdMobile = Integer.parseInt(mobile.getText().toString());
                final int rdSSN = Integer.parseInt(ssn.getText().toString());
                final String rdCompany = company.getText().toString();
                final String rdAddress = address.getText().toString();
                final int rdDLN = Integer.parseInt(dln.getText().toString());
                final String rdImage_encoded = getStringImage(bitmap);

                if (rdPassword.equals(rdPassword2)) {
                    if(rdEmail.matches(emailPattern)||rdEmail.matches(emailPattern2)){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //System.out.println(response);
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    pd.dismiss();
                                    Intent bktohome = new Intent(driver_registration.this, home.class);
                                    bktohome.putExtra("company", rdCompany);
                                    startActivity(bktohome);
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(driver_registration.this);
                                    builder.setMessage("Register Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    DriverRegistration registerRequest = new DriverRegistration(rdUsername, rdPassword, rdName, rdEmail,
                            rdMobile, rdCompany, rdAddress, rdSSN, rdDLN,rdImage_encoded, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(driver_registration.this);
                    queue.add(registerRequest);
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(driver_registration.this);
                        builder.setMessage("Wrong Email Format...")
                                .setNegativeButton("Retry",null)
                                .create()
                                .show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(driver_registration.this);
                    builder.setMessage(" Passwords do not match")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
                //finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        super.onActivityResult(requestCode, resultCode, i);
        if (requestCode == Request && resultCode == RESULT_OK && i != null && i.getData() != null) {
            picUri = i.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                if(picUri != null && bitmap !=  null) {
                    Toast.makeText(driver_registration.this, "Profile Picture selected successfully !", Toast.LENGTH_LONG).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(driver_registration.this);
                    builder.setMessage("Select profile picture first!")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
            } catch (IOException e) {
                System.out.println("--->>  el moshkila hna !!!!!");
                e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        //System.out.println("--->>DRIVER: encodedImage: " + encodedImage);
        return encodedImage;
    }
}