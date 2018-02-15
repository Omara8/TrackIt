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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class supervisor_registration extends AppCompatActivity {
    ImageButton reg,bk,profile_pic;
    int Request=1;
    private Bitmap bitmap;
    EditText un,pw,pw2,name,em,age,mob,company,address,SSN;
    Uri picUri;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_registration);
        un=(EditText) findViewById(R.id.editText3);
        pw=(EditText) findViewById(R.id.editText4);
        pw2=(EditText) findViewById(R.id.editText5);
        name=(EditText) findViewById(R.id.editText7);
        em=(EditText) findViewById(R.id.editText6);
        mob=(EditText) findViewById(R.id.editText12);
        company=(EditText) findViewById(R.id.editText11);
        address=(EditText) findViewById(R.id.editText13);
        SSN=(EditText) findViewById(R.id.editText14);
        profile_pic=(ImageButton) findViewById(R.id.button7);
        reg=(ImageButton) findViewById(R.id.button6);
        bk=(ImageButton) findViewById(R.id.button8);
        final ProgressDialog pd  = new ProgressDialog(supervisor_registration.this);
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent g=new Intent(Intent.ACTION_GET_CONTENT);
                g.setType("image/*");
                startActivityForResult(g,Request);
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
                final String rName= name.getText().toString();
                final String rUsername= un.getText().toString();
                final String rPassword= pw.getText().toString();
                final String rPassword2= pw2.getText().toString();
                final String rEmail= em.getText().toString();
                final int rMobile= Integer.parseInt(mob.getText().toString());
                final int rSSN= Integer.parseInt(SSN.getText().toString());
                final String rCompany= company.getText().toString();
                final String rAddress= address.getText().toString();
                final String rImage_encoded = getStringImage(bitmap);

                if(rPassword.equals(rPassword2)) {
                    if(rEmail.matches(emailPattern)||rEmail.matches(emailPattern2)){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //System.out.println(response);
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Intent bktohome = new Intent(supervisor_registration.this, home.class);
                                    bktohome.putExtra("company", rCompany);
                                    pd.dismiss();
                                    startActivity(bktohome);
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(supervisor_registration.this);
                                    builder.setMessage("Register Failed")
                                            .setNegativeButton("Retry",null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    SupervisorRegistration registerRequest= new SupervisorRegistration(rUsername ,  rPassword ,  rName ,  rEmail,
                            rMobile ,  rCompany ,  rAddress , rSSN , rImage_encoded ,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(supervisor_registration.this);
                    queue.add(registerRequest);
                }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(supervisor_registration.this);
                        builder.setMessage("Wrong Email Format...")
                                .setNegativeButton("Retry",null)
                                .create()
                                .show();
                    }
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(supervisor_registration.this);
                    builder.setMessage(" Passwords do not match")
                            .setNegativeButton("Retry",null)
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
            picUri= i.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                if(picUri != null && bitmap !=  null) {
                    Toast.makeText(supervisor_registration.this, "Profile Picture selected successfully !", Toast.LENGTH_LONG).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(supervisor_registration.this);
                    builder.setMessage("Select profile picture first!")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
            } catch (IOException e) {
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
