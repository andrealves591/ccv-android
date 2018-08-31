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

import andre.gautier.projfinalandreeduardo.adapter.ClienteAdapter;
import andre.gautier.projfinalandreeduardo.model.Cliente;

public class BuscaCliente extends AppCompatActivity {

    private RecyclerView rvClientes;

    private ArrayList<Cliente> clientes;
    private ClienteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_cliente);

        rvClientes = findViewById(R.id.bc_rv_clientes);

        FirebaseApp.initializeApp(BuscaCliente.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("clientes");

        clientes = new ArrayList<>();
        adapter = new ClienteAdapter(BuscaCliente.this, clientes);

        rvClientes.setAdapter(adapter);
        rvClientes.setHasFixedSize(true);
        rvClientes.setLayoutManager(new LinearLayoutManager(this));


        adapter.setOnItemClickListener(new ClienteAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(
                        getBaseContext(),
                        R.string.click,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(final int position, View v) {

                AlertDialog.Builder msg = new AlertDialog.Builder(BuscaCliente.this);
                msg.setTitle(getResources().getString(R.string.alert_titulo));
                msg.setMessage(getResources().getString(R.string.alert_msg));
                msg.setPositiveButton(getResources().getString(R.string.sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Cliente c = clientes.get(position);

                        banco.child(c.getKeyCliente()).removeValue();

                        clientes.remove(position);
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


                clientes.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Cliente c = data.getValue(Cliente.class);
                    c.setKeyCliente(data.getKey());
                    clientes.add(c);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
