package com.example.codedex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.codedex.models.UserManager;

public class LoginActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button btnLogin, btnRegister;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Mostrar mensaje de error si existe
        String errorMessage = getIntent().getStringExtra("error_message");
        if (errorMessage != null && !errorMessage.isEmpty()) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }

        // Inicialización
        userManager = UserManager.getInstance();
        initViews();
        setupListeners();
    }

    private void initViews() {
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(v -> handleLogin());
        btnRegister.setOnClickListener(v -> navigateToRegister());
    }

    private void handleLogin() {
        String email = emailTextView.getText().toString().trim();
        String password = passwordTextView.getText().toString().trim();

        if (!validateForm(email, password)) {
            return;
        }

        // Verificar credenciales primero
        if (UserManager.getInstance().loginUser(email, password)) {
            // Guardar estado de login ANTES de navegar
            UserManager.getInstance().saveLoginState(this, true, email);

            // Esperar 100ms para asegurar la escritura en SharedPreferences
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                navigateToMenu();
            }, 100);
        } else {
            showError("Credenciales incorrectas");
        }
    }

    private void navigateToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        Log.d("LoginActivity", "Navegación completada a MenuActivity");
    }
    private boolean validateForm(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            showError("Ingrese un email");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Email inválido");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            showError("Ingrese una contraseña");
            return false;
        }

        return true;
    }

    private void onLoginSuccess() {
        Log.d("LoginActivity", "Login exitoso, navegando a MenuActivity");
        showSuccess("Inicio exitoso!");
        navigateToMain();
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void navigateToRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}