package andre.gautier.projfinalandreeduardo;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import andre.gautier.projfinalandreeduardo.adapter.VeiculoAdapter;
import andre.gautier.projfinalandreeduardo.model.Veiculo;

public class BuscaVeiculo extends AppCompatActivity {

    private RecyclerView rvVeiculos;

    private ArrayList<Veiculo> veiculos;
    private VeiculoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_veiculo);

        rvVeiculos = findViewById(R.id.bv_rv_veiculos);

        FirebaseApp.initializeApp(BuscaVeiculo.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("veiculos");

        veiculos = new ArrayList<>();
        adapter = new VeiculoAdapter(BuscaVeiculo.this, veiculos);

        rvVeiculos.setAdapter(adapter);
        rvVeiculos.setHasFixedSize(true);
        rvVeiculos.setLayoutManager(new LinearLayoutManager(this));


        adapter.setOnItemClickListener(new VeiculoAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(
                        getBaseContext(),
                        R.string.click,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(final int position, View v) {

                AlertDialog.Builder msg = new AlertDialog.Builder(BuscaVeiculo.this);
                msg.setTitle(getResources().getString(R.string.alert_titulo));
                msg.setMessage(getResources().getString(R.string.alert_msg));
                msg.setPositiveButton(getResources().getString(R.string.sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Veiculo v = veiculos.get(position);

                        banco.child(v.getKeyVeiculo()).removeValue();

                        veiculos.remove(position);
                        adapter.notifyDataSetChanged();

                        Toast.makeText(
                                getBaseContext(),
                                R.string.removido,
                                Toast.LENGTH_LONG).show();

                    }
                });
                msg.setNegativeButton(getResources().getString(R.string.nao), null);
                msg.show();
            }
        });


        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                veiculos.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Veiculo v = data.getValue(Veiculo.class);
                    v.setKeyVeiculo(data.getKey());
                    veiculos.add(v);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
