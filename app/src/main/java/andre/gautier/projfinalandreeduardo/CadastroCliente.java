package andre.gautier.projfinalandreeduardo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import andre.gautier.projfinalandreeduardo.model.CEP;
import andre.gautier.projfinalandreeduardo.model.Cliente;
import andre.gautier.projfinalandreeduardo.model.Endereco;
import andre.gautier.projfinalandreeduardo.service.APIRetrofitService;
import andre.gautier.projfinalandreeduardo.service.CEPDeserializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CadastroCliente extends AppCompatActivity {

    private EditText etNome;
    private EditText etCPF;
    private RadioGroup rgSexo;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private EditText etIdade;
    private EditText etCEP;
    private Button btCadastrar;
    private EditText etRua;
    private EditText etNumero;
    private EditText etCidade;
    private EditText etBairro;
    private EditText etUF;
    private Button btBuscaEndereco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        etNome = findViewById(R.id.cc_et_nome);
        etCPF = findViewById(R.id.cc_et_cpf);
        etIdade = findViewById(R.id.cc_et_idade);
        rgSexo = findViewById(R.id.cc_rg_sexo);
        rbMasculino = findViewById(R.id.cc_rb_masculino);
        rbFeminino = findViewById(R.id.cc_rb_feminino);
        etCEP = findViewById(R.id.cc_et_cep);
        btCadastrar = findViewById(R.id.cc_bt_cadastrar);
        etRua = findViewById(R.id.cc_et_rua);
        etNumero = findViewById(R.id.cc_et_numero);
        etBairro = findViewById(R.id.cc_et_bairro);
        etCidade = findViewById(R.id.cc_et_cidade);
        etUF = findViewById(R.id.cc_et_uf);
        btBuscaEndereco = findViewById(R.id.cc_bt_buscacep);

        FirebaseApp.initializeApp(CadastroCliente.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("clientes");

        Gson g = new GsonBuilder()
                .registerTypeAdapter(CEP.class, new CEPDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();
        final APIRetrofitService service = retrofit.create(APIRetrofitService.class);

        btBuscaEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCEP.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), R.string.cep_invalido, Toast.LENGTH_LONG).show();
                } else {
                    Call<CEP> call = service.getCEP(etCEP.getText().toString());

                    call.enqueue(new Callback<CEP>() {
                        @Override
                        public void onResponse(Call<CEP> call, Response<CEP> response) {
                            if (response.isSuccessful()) {
                                CEP cep = response.body();

                                etRua.setText(cep.getLogradouro());
                                etBairro.setText(cep.getBairro());
                                etCidade.setText(cep.getLocalidade());
                                etUF.setText(cep.getUf());

                                hideKeyboard(CadastroCliente.this);
                            } else {
                                Toast.makeText(getBaseContext(), R.string.cliente_erro + response.message(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CEP> call, Throwable t) {
                                Toast.makeText(getBaseContext(), R.string.cliente_erro + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cliente c = new Cliente();
                Endereco e = new Endereco();

                if (etNome.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), R.string.nome_invalido, Toast.LENGTH_LONG).show();
                } else {
                    c.setNome(etNome.getText().toString());
                }
                if (etCPF.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), R.string.cpf_invalido, Toast.LENGTH_LONG).show();
                } else {
                    c.setCpf(Long.parseLong(etCPF.getText().toString()));
                }

                if (etIdade.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), R.string.idade_invalido, Toast.LENGTH_LONG).show();
                } else {
                    c.setIdade(Integer.parseInt(etIdade.getText().toString()));
                }

                if (rgSexo.getCheckedRadioButtonId() == 0) {
                    Toast.makeText(getBaseContext(), R.string.sexo_invalido, Toast.LENGTH_LONG).show();
                } else {
                    if (rgSexo.getCheckedRadioButtonId() == rbMasculino.getId()) {
                        c.setSexo(rbMasculino.getText().toString());
                    } else if (rgSexo.getCheckedRadioButtonId() == rbFeminino.getId()) {
                        c.setSexo(rbFeminino.getText().toString());
                    }
                }

                if (etCEP.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), R.string.cep_invalido, Toast.LENGTH_LONG).show();
                } else {
                    e.setCep(Long.parseLong(etCEP.getText().toString()));
                }

                e.setRua(etRua.getText().toString());
                e.setNumero(Integer.parseInt(etNumero.getText().toString()));
                e.setBairro(etBairro.getText().toString());
                e.setCidade(etCidade.getText().toString());
                e.setEstado(etUF.getText().toString());

                c.setEndereco(e);

                banco.push().setValue(c);



                Toast.makeText(getBaseContext(), R.string.sucesso, Toast.LENGTH_LONG).show();
                limpar();
            }//fechaonClick

        });//fecha ClickListener

    }//fecha oncreate

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

    private void limpar(){
        etNome.setText(null);
        etCPF.setText(null);
        rgSexo.clearCheck();
        etIdade.setText(null);
        etCEP.setText(null);
        etRua.setText(null);
        etBairro.setText(null);
        etCidade.setText(null);
        etUF.setText(null);
        etNumero.setText(null);
    }
}//fecha classe