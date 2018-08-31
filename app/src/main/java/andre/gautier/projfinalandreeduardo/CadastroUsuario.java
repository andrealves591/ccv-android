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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import andre.gautier.projfinalandreeduardo.model.Usuario;

public class CadastroUsuario extends AppCompatActivity {


    private EditText etEmail;
    private EditText etSenha;
    private EditText etRepetirSenha;
    private Button btOK;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        etEmail = findViewById(R.id.cu_et_email);
        etSenha = findViewById(R.id.cu_et_senha);
        etRepetirSenha = findViewById(R.id.cu_et_repetir_senha);
        btOK = findViewById(R.id.cu_bt_cadastrar);
        mAuth = FirebaseAuth.getInstance();

        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etEmail.getText().toString().isEmpty() &&
                        !etSenha.getText().toString().isEmpty() &&
                        !etRepetirSenha.getText().toString().isEmpty()) {

                    if (etSenha.getText().toString().equalsIgnoreCase(etRepetirSenha.getText().toString())) {
                        Usuario u = new Usuario();
                        u.setLogin(etEmail.getText().toString());
                        u.setSenha(etSenha.getText().toString());

                        mAuth.createUserWithEmailAndPassword(u.getLogin(), u.getSenha())
                                .addOnCompleteListener(CadastroUsuario.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(
                                                    getBaseContext(),
                                                    R.string.cliente_erro,
                                                    Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(
                                                    getBaseContext(),
                                                    R.string.sucesso,
                                                    Toast.LENGTH_LONG).show();

                                            Intent it = new Intent(CadastroUsuario.this, Login.class);
                                            startActivity(it);
                                            finish();
                                        }
                                    }
                                });
                        hideKeyboard(CadastroUsuario.this);
                        limpar();
                    } else {
                        Toast.makeText(
                                getBaseContext(),
                                R.string.senha_validada,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(
                            getBaseContext(),
                            R.string.cad_user,
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
    private void limpar() {
        etEmail.setText(null);
        etSenha.setText(null);
        etRepetirSenha.setText(null);
    }
}
