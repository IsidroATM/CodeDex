package com.example.codedex;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codedex.models.UserManager;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button btnRegister, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar vistas
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);

        // Manejar clic en el botón de registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

        // Manejar clic en el botón de login (para ir a la actividad de login)
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Simplemente cerramos esta actividad para volver al login
            }
        });
    }

    private void registerNewUser() {
        String email = emailTextView.getText().toString().trim();
        String password = passwordTextView.getText().toString().trim();

        // Validaciones
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor ingrese un email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        // Registrar usuario
        boolean registrationSuccess = UserManager.getInstance().registerUser(email, password);

        if (registrationSuccess) {
            Toast.makeText(this, "Registro exitoso!", Toast.LENGTH_SHORT).show();
            finish(); // Volver al login después del registro
        } else {
            Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
        }
    }
}