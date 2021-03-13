package ro.raduca.liceum;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import ro.raduca.liceum.R;
import ro.raduca.liceum.data.Category;
import ro.raduca.liceum.data.Database;


public class Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView nav_name_header, nav_email_header;
    Button c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11;
    private ProgressDialog progressBar;
    public final static String Message = "ro.raduca.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);

        Database database = new Database(this);
        database.createDatabase();
        database.openDatabase();
        database.getWritableDatabase();

        ArrayList<Category> categories = Category.getAll(database.sqlite);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);

        String nav_header_name = sharedPreferences.getString("name", "xyz");
        String nav_header_email = sharedPreferences.getString("email", "abc@gmail.com");
        View header = navigationView.getHeaderView(0);

        nav_name_header = header.findViewById(R.id.nav_header_name);
        nav_email_header = header.findViewById(R.id.nav_header_email);
        nav_name_header.setText(nav_header_name);
        nav_email_header.setText(nav_header_email);

        c1 = findViewById(R.id.b1);
        c2 = findViewById(R.id.b2);
        c3 = findViewById(R.id.b3);
        c4 = findViewById(R.id.b4);
        c5 = findViewById(R.id.b5);
        c6 = findViewById(R.id.b6);
        c7 = findViewById(R.id.b7);
        c8 = findViewById(R.id.b8);
        c9 = findViewById(R.id.b9);
        c10 = findViewById(R.id.b10);
        c11 = findViewById(R.id.b11);

        c1.setText(categories.get(0).getTranslatedName());
        c2.setText(categories.get(1).getTranslatedName());
        c3.setText(categories.get(2).getTranslatedName());
        c4.setText(categories.get(3).getTranslatedName());
        c5.setText(categories.get(4).getTranslatedName());
        c6.setText(categories.get(5).getTranslatedName());
        c7.setText(categories.get(6).getTranslatedName());
        c8.setText(categories.get(7).getTranslatedName());
        c9.setText(categories.get(8).getTranslatedName());
        c10.setText(categories.get(9).getTranslatedName());
        c11.setText(categories.get(10).getTranslatedName());

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {}}, 400);

                progressBar = new ProgressDialog(v.getContext());

                progressBar.setCancelable(false);

                progressBar.setMessage("Getting questions ready...");

                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                // pentru a fi afisat si vizualizat, voi adauga un delay progress barului

                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // progressBar-ul va fi disparea de pe ecran dupa ce delay-ul va expira
                        progressBar.cancel();

                        // intent pentru a deschide navigation drawer
                        Intent intent = new Intent(Navigation.this, Questions.class);
                        intent.putExtra(Message, "c1");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {}}, 400);
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Getting questions ready...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.cancel();
                        Intent intent = new Intent(Navigation.this, Questions.class);
                        intent.putExtra(Message, "c2");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {}}, 400);
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Getting questions ready...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.cancel();
                        Intent intent = new Intent(Navigation.this, Questions.class);
                        intent.putExtra(Message, "c3");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {}}, 400);
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Getting questions ready...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.cancel();
                        Intent intent = new Intent(Navigation.this, Questions.class);
                        intent.putExtra(Message, "c4");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {}}, 400);
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Getting questions ready...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.cancel();
                        Intent intent = new Intent(Navigation.this, Questions.class);
                        intent.putExtra(Message, "c5");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });

        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {}}, 400);
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Getting questions ready...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.cancel();
                        Intent intent = new Intent(Navigation.this, Questions.class);
                        intent.putExtra(Message, "c6");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });

        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {}}, 400);
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Getting questions ready...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.cancel();
                        Intent intent = new Intent(Navigation.this, Questions.class);
                        intent.putExtra(Message, "c7");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });

        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {}}, 400);
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Getting questions ready...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.cancel();
                        Intent intent = new Intent(Navigation.this, Questions.class);
                        intent.putExtra(Message, "c8");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });

        c9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {}}, 400);
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Getting questions ready...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.cancel();
                        Intent intent = new Intent(Navigation.this, Questions.class);
                        intent.putExtra(Message, "c9");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });

        c10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {}}, 400);
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Getting questions ready...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.cancel();
                        Intent intent = new Intent(Navigation.this, Questions.class);
                        intent.putExtra(Message, "c10");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });

        c11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {}}, 400);
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Getting questions ready...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.cancel();
                        Intent intent = new Intent(Navigation.this, Questions.class);
                        intent.putExtra(Message, "c11");
                        startActivity(intent);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_scorecard) {
            Intent intent = new Intent(this, ScoreCard.class);
            startActivity(intent);

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, Settings.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
    }
}
