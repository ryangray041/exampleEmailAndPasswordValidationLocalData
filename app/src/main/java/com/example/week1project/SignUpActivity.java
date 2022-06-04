package com.example.week1project;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private SharedPreferences myPreferences;
    private ArrayList<String> userData;


    private static final String userInfo = "userInfo";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");

    private EditText emailValidate;
    private EditText passwordValidate;
    private EditText confirmPassword;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailValidate = findViewById(R.id.input_email);
        passwordValidate = findViewById(R.id.input_password);
        confirmPassword = findViewById(R.id.input_password_again);
        signUpButton = findViewById(R.id.create_acc_btn2);

        myPreferences = getSharedPreferences(userInfo, MODE_PRIVATE);
        userData = new ArrayList<>();

    }

    @Override
    protected void onResume() {
        super.onResume();

        emailValidate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkEmail();
                enableButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordValidate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPassword();
                enableButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPasswordAgain();
                enableButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    private void saveData(){
        String userEmail = emailValidate.getText().toString();

        userData.add(userEmail);

        myPreferences.edit().putString("KEY_EMAIL", userEmail).apply();
    }

    private boolean isEmailValid(EditText emailValidate){
        String email = emailValidate.getText().toString();
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean checkEmail(){
        String email = emailValidate.getText().toString();

        //noinspection deprecation
        @SuppressLint("UseCompatLoadingForDrawables") Drawable customErrorDrawable = getResources().getDrawable(R.drawable.cross);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        if(email.isEmpty()){
            emailValidate.setError("Email cannot be blank", customErrorDrawable);
            return false;
        }
        else{
            if(userData.contains(email)){
                emailValidate.setError("This email is not available", customErrorDrawable);
                return false;
            }
            else if(!isEmailValid(emailValidate)){
                emailValidate.setError("Enter a valid email", customErrorDrawable);
                return false;
            }
            else{
                emailValidate.setError(null);
                return true;
            }
        }
    }

    private boolean isPasswordValid(EditText passwordValidate){
        String password = passwordValidate.getText().toString();
        return !password.isEmpty() && PASSWORD_PATTERN.matcher(password).matches();
    }

    private boolean checkPassword(){
        String password = passwordValidate.getText().toString();

        //noinspection deprecation
        @SuppressLint("UseCompatLoadingForDrawables") Drawable customErrorDrawable = getResources().getDrawable(R.drawable.cross);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        if(password.isEmpty()){
            passwordValidate.setError("Password cannot be blank", customErrorDrawable);
            return false;
        }
        else if(!isPasswordValid(passwordValidate)){
            passwordValidate.setError("Invalid password", customErrorDrawable);
            return false;
        }
        else{
            passwordValidate.setError(null);
            return true;
        }
    }

    private boolean doesPasswordMatch(EditText passwordValidate, EditText confirmPassword){
        String password = passwordValidate.getText().toString();
        String passwordAgain = confirmPassword.getText().toString();
        return !passwordAgain.isEmpty() && password.equals(passwordAgain);
    }

    private boolean checkPasswordAgain(){
        String passwordAgain = confirmPassword.getText().toString();

        //noinspection deprecation
        @SuppressLint("UseCompatLoadingForDrawables") Drawable customErrorDrawable = getResources().getDrawable(R.drawable.cross);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        if(passwordAgain.isEmpty()){
            confirmPassword.setError("Password cannot be blank", customErrorDrawable);
            return false;
        }
        else if(!doesPasswordMatch(passwordValidate, confirmPassword)){
            confirmPassword.setError("Password does not match", customErrorDrawable);
            return false;
        }
        else{
            confirmPassword.setError(null);
            return true;
        }
    }

    private void enableButton(){
        String email = emailValidate.getText().toString().trim();
        String password = passwordValidate.getText().toString().trim();
        String passwordAgain = confirmPassword.getText().toString().trim();

        if (password.isEmpty() || passwordAgain.isEmpty() || email.isEmpty()) {
            signUpButton.setEnabled(false);
        }
        else {
            signUpButton.setEnabled(checkPassword() && checkPasswordAgain() && checkEmail());
        }
    }

}