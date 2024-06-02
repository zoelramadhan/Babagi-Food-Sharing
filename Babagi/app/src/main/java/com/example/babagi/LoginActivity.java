package com.example.babagi;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.babagi.config.DbConfig;

public class LoginActivity extends AppCompatActivity {
    private EditText etLoginEmail, etLoginPassword;
    private TextView tvErrorMessage;
    private Button btnLogin;
    private DbConfig dbConfig;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etLoginEmail = findViewById(R.id.et_login_email);
        etLoginPassword = findViewById(R.id.et_login_password);
        tvErrorMessage = findViewById(R.id.tv_login_error_message);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            String email = etLoginEmail.getText().toString().trim();
            String password = etLoginPassword.getText().toString().trim();

            if (email.isEmpty()) {
                etLoginEmail.setError("Please enter your email");
            } else if (password.isEmpty()) {
                etLoginPassword.setError("Please enter your password");
            } else {
                // Check user from database
                dbConfig = new DbConfig(this);
                Cursor cursor = dbConfig.login(email, password);
                if (cursor.moveToFirst()) {
                    // Login success
                    do {
                        preferences = getSharedPreferences("user_pref", MODE_PRIVATE);
