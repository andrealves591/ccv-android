package andre.gautier.projfinalandreeduardo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import andre.gautier.projfinalandreeduardo.model.Veiculo;

public class CadastroVeiculo extends AppCompatActivity {

    private EditText etNome;
    private Spinner spMarca;
    private EditText etAno;
    private EditText etModelo;
    private Spinner spCombustivel;
    private RadioGroup rgPortas;
    private RadioButton rd2p;
    private RadioButton rd3p;
    private RadioButton rd4p;
    private EditText etCor;
    private EditText etValorCusto;
    private CheckBox cbAr;
    private CheckBox cbDirecao;
    private CheckBox cbAirbag;
    private Button btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veiculo);

        etNome = findViewById(R.id.cv_et_nome);
        spMarca = findViewById(R.id.cv_sp_marcas);
        etAno = findViewById(R.id.cv_et_ano);
        etModelo = findViewById(R.id.cv_et_modelo);
        spCombustivel = findViewById(R.id.cv_sp_combustivel);
        rgPortas = findViewById(R.id.cv_rg_portas);
        rd2p = findViewById(R.id.cv_rb_2);
        rd3p = findViewById(R.id.cv_rb_3);
        rd4p = findViewById(R.id.cv_rb_4);
        etCor = findViewById(R.id.cv_et_cor);
        etValorCusto = findViewById(R.id.cv_et_valor_custo);
        cbAr = findViewById(R.id.cv_cb_ar);
        cbDirecao = findViewById(R.id.cv_cb_direcao);
        cbAirbag = findViewById(R.id.cv_cb_airbag);
        btCadastrar = findViewById(R.id.cv_bt_cadastrar);

        FirebaseApp.initializeApp(CadastroVeiculo.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("veiculos");

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Veiculo v = new Veiculo();

                if (etNome.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), R.string.nome_invalido, Toast.LENGTH_LONG).show();
                } else {
                    v.setNome(etNome.getText().toString());
                }

                if (spMarca.getSelectedItemPosition() == 0) {
                    Toast.makeText(getBaseContext(), R.string.marca_invalido, Toast.LENGTH_LONG).show();
                } else {
                    v.setMarca(spMarca.getSelectedItem().toString());
                }

                if (etAno.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), R.string.ano_invalido, Toast.LENGTH_LONG).show();
                } else {
                    v.setAno(Integer.parseInt(etAno.getText().toString()));
                }

                if (etModelo.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), R.string.modelo_invalido, Toast.LENGTH_LONG).show();
                } else {
                    v.setModelo(Integer.parseInt(etModelo.getText().toString()));
                }

                if (spCombustivel.getSelectedItemPosition() == 0) {
                    Toast.makeText(getBaseContext(), R.string.combustivel_invalido, Toast.LENGTH_LONG).show();
                } else {
                    v.setCombustivel(spCombustivel.getSelectedItem().toString());
                }
                if (rgPortas.getCheckedRadioButtonId() == 0) {
                    Toast.makeText(getBaseContext(), R.string.portas_invalido, Toast.LENGTH_LONG).show();
                } else {
                    if (rgPortas.getCheckedRadioButtonId() == rd2p.getId()) {
                        v.setPortas(rd2p.getText().toString());
                    } else if (rgPortas.getCheckedRadioButtonId() == rd3p.getId()) {
                        v.setPortas(rd3p.getText().toString());
                    } else if (rgPortas.getCheckedRadioButtonId() == rd4p.getId()) {
                        v.setPortas(rd4p.getText().toString());
                    }
                }

                if (etCor.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), R.string.cor_invalido, Toast.LENGTH_LONG).show();
                } else {
                    v.setCor(etCor.getText().toString());
                }

                if (etValorCusto.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), R.string.preco_invalido, Toast.LENGTH_LONG).show();
                } else {
                    v.setValorCusto(Double.parseDouble(etValorCusto.getText().toString()));
                }

                ArrayList<String> complementos = new ArrayList<>();

                if (cbAr.isChecked()) {
                    complementos.add(getResources().getString(R.string.sim));
                } else {
                    complementos.add(getResources().getString(R.string.nao));
                }

                if (cbDirecao.isChecked()) {
                    complementos.add(getResources().getString(R.string.sim));
                } else {
                    complementos.add(getResources().getString(R.string.nao));
                }

                if (cbAirbag.isChecked()) {
                    complementos.add(getResources().getString(R.string.sim));
                } else {
                    complementos.add(getResources().getString(R.string.nao));
                }

                v.setComplementos(complementos);

                banco.push().setValue(v);

                hideKeyboard(CadastroVeiculo.this);
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

    private void limpar() {
        etNome.setText(null);
        spMarca.setSelection(0);
        etAno.setText(null);
        etModelo.setText(null);
        spCombustivel.setSelection(0);
        rgPortas.clearCheck();
        etCor.setText(null);
        etValorCusto.setText(null);
        cbAr.setChecked(false);
        cbDirecao.setChecked(false);
        cbAirbag.setChecked(false);
    }
}//fecha classe