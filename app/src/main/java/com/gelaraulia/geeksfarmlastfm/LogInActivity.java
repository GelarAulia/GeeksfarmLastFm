package com.gelaraulia.geeksfarmlastfm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    Context mContext;
    private FirebaseAuth auth;
    EditText et_email, et_password;
    TextView tv_forgot, tv_toSignup;
    Button btn_login;
    ProgressDialog pDial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mContext = LogInActivity.this;

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(mContext,MainActivity.class));
            finish();
        }

        et_email = (EditText)findViewById(R.id.et_loginEmail);
        et_password = (EditText)findViewById(R.id.et_loginPassword);
        btn_login = (Button)findViewById(R.id.btn_login);
        tv_forgot = (TextView)findViewById(R.id.tv_loginForgot);
        tv_toSignup = (TextView)findViewById(R.id.tv_toSignup);

        pDial = new ProgressDialog(mContext);

//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email = et_email.getText().toString();
//                String password = et_password.getText().toString();
//                if(TextUtils.isEmpty(email)){
//                    Toast.makeText(mContext,"Email is empty",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if(TextUtils.isEmpty(password)){
//                    Toast.makeText(mContext,"Password is empty",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                pDial.setMessage("Logging In...");
//                pDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                pDial.setCancelable(false);
//                pDial.show();
//
//                auth.signInWithEmailAndPassword(email,password)
//                        .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            }
//                        });
//            }
//        });
    }
}
