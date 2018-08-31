package andre.gautier.projfinalandreeduardo;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import andre.gautier.projfinalandreeduardo.model.Usuario;

public class Login extends AppCompatActivity {

    private EditText etLogin;
    private EditText etSenha;
    private Button btLogin;
    private TextView tvEsqueciSenha;
    private Button btCadastrar;
    private ProgressBar progress;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin = findViewById(R.id.lg_et_email);
        etSenha = findViewById(R.id.lg_et_senha);
        btLogin = findViewById(R.id.lg_bt_login);
        tvEsqueciSenha = findViewById(R.id.lg_tv_esqueceu_senha);
        btCadastrar = findViewById(R.id.lg_bt_register);
        progress = findViewById(R.id.lg_progess);

        progress.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                /*if (user != null) {
                    Toast.makeText(
                            getBaseContext(),
                            R.string.ja_logado,
                            Toast.LENGTH_LONG).show();
                    Intent it = new Intent(Login.this, MainActivity.class);
                    startActivity(it);
                    finish();

                } else {
                    Toast.makeText(
                            getBaseContext(),
                            R.string.nao_logado,
                            Toast.LENGTH_LONG).show();
                }*/
            }
        };

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etLogin.getText().toString().isEmpty() &&
                        !etSenha.getText().toString().isEmpty()) {

                    progress.setVisibility(View.VISIBLE);

                    Usuario u = new Usuario();
                    u.setLogin(etLogin.getText().toString());
                    u.setSenha(etSenha.getText().toString());

                    mAuth.signInWithEmailAndPassword(u.getLogin(), u.getSenha())
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(
                                                getBaseContext(),
                                                R.string.usuario_nao,
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(
                                                getBaseContext(),
                                                R.string.usuario_autenticado,
                                                Toast.LENGTH_LONG).show();

                                        Intent it = new Intent(Login.this, MainActivity.class);
                                        startActivity(it);
                                        finish();
                                    }
                                    progress.setVisibility(View.INVISIBLE);

                                    hideKeyboard(Login.this);
                                }
                            });
                } else {
                    Toast.makeText(
                            getBaseContext(),
                            R.string.dados_incorretos,
                            Toast.LENGTH_LONG).show();
                }
            }//fecha onclick
        });

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Login.this, CadastroUsuario.class);
                startActivity(it);
            }
        });

        tvEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth auth = FirebaseAuth.getInstance();

                if (!etLogin.getText().toString().isEmpty()) {

                    progress.setVisibility(View.VISIBLE);

                    Usuario u = new Usuario();
                    u.setLogin(etLogin.getText().toString());

                    auth.sendPasswordResetEmail(u.getLogin())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(
                                            getBaseContext(),
                                            R.string.redefinicao_senha,
                                            Toast.LENGTH_LONG).show();
                                    progress.setVisibility(View.INVISIBLE);
                                }
                            });
                } else {
                    Toast.makeText(
                            getBaseContext(),
                            R.string.email_incompleto,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}