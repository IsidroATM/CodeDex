package com.example.codedex;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.codedex.fragments.AboutFragment;
import com.example.codedex.fragments.AlgoritmosFragment;
import com.example.codedex.fragments.HomeFragment;
import com.example.codedex.fragments.LenguajesFragment;
import com.example.codedex.fragments.SettingsFragment;
import com.example.codedex.fragments.StateFragment;
import com.example.codedex.models.UserManager;
import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_menu);
        // Verificación de login
        if (!UserManager.getInstance().isUserLoggedIn(this)) {
            redirectToLogin("Acceso no autorizado"); // Pasar mensaje como parámetro
            return;
        }

        try {
            setContentView(R.layout.activity_menu);

            // Configuración del Toolbar
            if (!setupToolbar()) {
                redirectToLogin("Error al configurar la aplicación");
                return;
            }

            // Configuración del Navigation Drawer
            if (!setupNavigationDrawer()) {
                redirectToLogin("Error al configurar el menú");
                return;
            }

            // Carga del fragment inicial
            if (savedInstanceState == null) {
                loadInitialFragment();
            }

        } catch (Exception e) {
            Log.e("MenuActivity", "Error al iniciar actividad", e);
            redirectToLogin("Error al cargar la aplicación: " + e.getMessage());
        }
    }

    private void redirectToLogin(String message) {
        Intent intent = new Intent(this, LoginActivity.class);
        if (message != null) {
            intent.putExtra("error_message", message);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void redirectToLogin() {
        redirectToLogin(null); // Llama a la versión con parámetro
    }

    private boolean setupToolbar() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar == null) {
                Log.e("MenuActivity", "Toolbar no encontrado - Verifica activity_menu.xml");
                return false;
            }

            setSupportActionBar(toolbar);

            // Configuración adicional del ActionBar
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setTitle(""); // Opcional: elimina el título por defecto
            } else {
                Log.e("MenuActivity", "getSupportActionBar() retornó null");
                return false;
            }

            return true;
        } catch (Exception e) {
            Log.e("MenuActivity", "Error en setupToolbar: " + e.getMessage(), e);
            return false;
        }
    }

    private boolean setupNavigationDrawer() {
        try {
            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view); // Inicialización de la variable de clase

            if (drawerLayout == null || navigationView == null) {
                throw new NullPointerException("Componentes del Navigation Drawer no encontrados");
            }

            navigationView.setNavigationItemSelectedListener(this);
            toggle = new ActionBarDrawerToggle(
                    this,
                    drawerLayout,
                    R.string.open_nav,
                    R.string.close_nav);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            return true;
        } catch (Exception e) {
            Log.e("MenuActivity", "Error en setupNavigationDrawer", e);
            return false;
        }
    }

    private void loadInitialFragment() {
        if (navigationView != null) { // Validación añadida
            navigationView.setCheckedItem(R.id.nav_home);
            loadFragment(new HomeFragment());//Aqui se cambia la vista principal
        } else {
            Log.e("MenuActivity", "NavigationView es nulo al cargar fragmento inicial");
        }
    }

    private void loadFragment(Fragment fragment) {
        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        } catch (Exception e) {
            Log.e("MenuActivity", "Error al cargar fragment", e);
            Toast.makeText(this, "Error al cargar la pantalla", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sincronizar el estado del toggle
        if (toggle != null) {
            toggle.syncState();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                loadFragment(new HomeFragment());
            } else if (id == R.id.nav_settings) {
                loadFragment(new SettingsFragment());
            } else if (id == R.id.nav_state) {
                loadFragment(new StateFragment());
            } else if (id == R.id.nav_about) {
                loadFragment(new AboutFragment());
            } else if (id == R.id.nav_algoritmos) {
                loadFragment(new AlgoritmosFragment());
            } else if (id == R.id.nav_lenguajes) {
                loadFragment(new LenguajesFragment());
            } else if (id == R.id.nav_logout) {
                performLogout();
            }

            if (drawerLayout != null) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            return true;
        } catch (Exception e) {
            Log.e("MenuActivity", "Error en navegación", e);
            return false;
        }
    }

    private void performLogout() {
        UserManager.getInstance().logout(this);
        Toast.makeText(this, "Sesión cerrada exitosamente", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Manejar clic en el ícono de navegación (hamburguesa)
        if (toggle != null && toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}