package ro.raduca.liceum;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ro.raduca.liceum.R;

public class MainActivity extends AppCompatActivity {

    // Definirea variabilelor

    Button show, getStarted, show2, signIn;
    EditText edit_name, edit_email, edit_password, edit_password2;
    TextView name_display, forget;
    private String Default = "N/A";
    private ProgressDialog progressBar; // am creat un progress Bar


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE); // referinta catre shared preferences

        // crearea unui fisier cu sharedPreferences pentru a salva numele, adresa e-mail, parola

        String name_file = sharedPreferences.getString("name", Default);
        String pass_file = sharedPreferences.getString("password", Default);
        String email_file = sharedPreferences.getString("email", Default);
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);

        // daca nu exista user inregistrat

        if (name_file.equals(Default) || pass_file.equals(Default) || email_file.equals(Default)) {

            setContentView(R.layout.activity_main);

            edit_name = findViewById(R.id.name); // numele jucatorului
            edit_email = findViewById(R.id.email); // adresa de e-mail a jucatorului
            edit_password = findViewById(R.id.password); // parola setata
            show = findViewById(R.id.show); // butonul pentru afisarea parolei
            getStarted = findViewById(R.id.getStarted); // buton de Sign Up

            show.setOnClickListener(new showOrHidePassword()); // invocarea clasei showOrHidePassword pentru a afisa parola

            // ce se intampla in momentul apasarii butonului de Get Started

            getStarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String save_name = edit_name.getText().toString();
                    String save_email = edit_email.getText().toString();
                    String save_password = edit_password.getText().toString();

                    // verificam daca cele 3 campuri sunt necompletate

                    if (save_name.equals("") || save_email.equals("") || save_password.equals("")) {
                        try {
                            Toast.makeText(MainActivity.this, "Please enter the details!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("Error", "Error");
                        }
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", save_name);
                        editor.putString("email", save_email);
                        editor.putString("password", save_password);
                        editor.apply();

                        // crearea unui obiect de tip progress bar
                        progressBar = new ProgressDialog(v.getContext());
                        progressBar.setCancelable(false); // nu poate fi anulat apasand pe ecran
                        progressBar.setMessage("Please Wait..."); // mesajul care este afisat
                        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER); // stilul
                        progressBar.setProgress(0);
                        progressBar.setMax(100);
                        progressBar.show(); // afisarea

                        // adaugarea unui delay
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.cancel();
                                Intent intent = new Intent(MainActivity.this, Navigation.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }
                }
            });
        }
        // daca exista user inregistrat, se trece la alta activitate
        else {
            setContentView(R.layout.activity_main_signin);

            name_display = findViewById(R.id.name_display);
            name_display.setText(name_file);
            edit_password2 = findViewById(R.id.password2);
            show2 = findViewById(R.id.show2);
            show2.setOnClickListener(new showOrHidePassword2());
            forget = findViewById(R.id.forget);
            signIn = findViewById(R.id.Continue);

            try {
                signIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String local_pass2 = edit_password2.getText().toString();

                        // daca parola introdusa este corecta
                        if (sharedPreferences.getString("password", Default).equals(local_pass2)) {

                            progressBar = new ProgressDialog(v.getContext());
                            progressBar.setCancelable(false);
                            progressBar.setMessage("Please Wait...");
                            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressBar.setProgress(0);
                            progressBar.setMax(100);
                            progressBar.show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.cancel();
                                    Intent intent = new Intent(MainActivity.this, Navigation.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 2000);
                        }
                        // parola introdusa este gresita
                        else {
                            Toast.makeText(MainActivity.this, "Please enter correct password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Warning", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // vom creea o clasa pentru a afisa sau a ascunde parola in momentul apasarii butonului SHOW
    class showOrHidePassword implements View.OnClickListener {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            if (show.getText().toString().equals("SHOW")) {
                edit_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                show.setText("HIDE");

            } else {
                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                show.setText("SHOW");
            }
        }
    }

    class showOrHidePassword2 implements View.OnClickListener {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            if (show2.getText().toString().equals("SHOW")) {
                edit_password2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                show2.setText("HIDE");
            } else {
                edit_password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                show2.setText("SHOW");
            }
        }
    }

    // butonul de Forgot Password

    public void showDialog(View view) {

        // Crearea unui obiect de tip dialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        // camp de tip EditText pentru a aparea AlertDialog, astfel incat utilizatorul sa introduca adresa de e-mail folosita pentru crearea contului
        final EditText editTextDialog = new EditText(MainActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editTextDialog.setLayoutParams(layoutParams);
        editTextDialog.setHint("Email address");

        // adaugarea EditText in casuta de Dialog
        alertDialog.setView(editTextDialog);
        alertDialog.setTitle("Enter e-mail address!");
        final SharedPreferences sharedPreferences = getSharedPreferences("Content_main", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        // adaugarea butoanelor

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email_dialog = editTextDialog.getText().toString();

                // utilizatorul a apasat pe butonul OK
                if (sharedPreferences.getString("email", Default).equals(email_dialog)) {
                    // setam valorile Preferences in sharedPreferences cu Default
                    editor.putString("name", Default);
                    editor.putString("email", Default);
                    editor.putString("password", Default);
                    editor.apply();

                    // acest Intent va striga package manager si va restarta activitatea curenta
                    Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    assert i != null;
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Enter correct e-mail address!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // cand butonul cancel este apasat
            }
        });

        // afisarea casutei de dialog
        alertDialog.show();
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
