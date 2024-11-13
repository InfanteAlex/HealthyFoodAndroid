package com.example.healthyfoodandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Set up the layout insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up login button to validate credentials
        Button loginButton = findViewById(R.id.button2);
        loginButton.setOnClickListener(view -> {
            EditText usernameEditText = findViewById(R.id.editTextTextUsername);
            EditText passwordEditText = findViewById(R.id.editTextTextPassword4);

            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (dbHelper.validateLogin(username, password)) {
                Intent intent = new Intent(MainActivity.this, MenuScreen.class);
                startActivity(intent);
                finish(); // Close the login activity
            } else {
                Toast.makeText(MainActivity.this, "Invalid login credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up "Create Account" button to navigate to registration screen
        Button createAccountButton = findViewById(R.id.createaccount);
        createAccountButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    public void forgotPassword(View view) {
        // Optional: Add code here if you want to implement forgot password functionality
    }
}
