package andre.gautier.projfinalandreeduardo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import andre.gautier.projfinalandreeduardo.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Drawer result = null;
    private Intent it;
    private Usuario u =  new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.gray500)
                .addProfiles(
                        new ProfileDrawerItem().withName("Só Carrão Multimarcas").withEmail("multimarcas@socarrao.com")
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.cadastrar_veiculo)).withIdentifier(0).withIcon(GoogleMaterial.Icon.gmd_add_circle),
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.cadastrar_cliente)).withIdentifier(1).withIcon(GoogleMaterial.Icon.gmd_add_circle),
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.listar_veiculo)).withIdentifier(2).withIcon(GoogleMaterial.Icon.gmd_assignment),
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.listar_cliente)).withIdentifier(3).withIcon(GoogleMaterial.Icon.gmd_assignment),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.cadastrar_usuario)).withIdentifier(5).withIcon(GoogleMaterial.Icon.gmd_person_add),
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.action_settings)).withIdentifier(4).withIcon(GoogleMaterial.Icon.gmd_phonelink_setup),
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.sair)).withIdentifier(6).withIcon(GoogleMaterial.Icon.gmd_exit_to_app)


                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch ((int) drawerItem.getIdentifier()) {
                            case 0:
                                it = new Intent(MainActivity.this, CadastroVeiculo.class);
                                startActivity(it);
                                break;

                            case 1:
                                it = new Intent(MainActivity.this, CadastroCliente.class);
                                startActivity(it);
                                break;
                            case 2:
                                it = new Intent(MainActivity.this, BuscaVeiculo.class);
                                startActivity(it);
                                break;

                            case 3:
                                it = new Intent(MainActivity.this, BuscaCliente.class);
                                startActivity(it);
                                break;

                            case 4:
                                it = new Intent(MainActivity.this, Sobre.class);
                                startActivity(it);
                                break;
                            case 5:
                                it = new Intent(MainActivity.this, CadastroUsuario.class);
                                startActivity(it);
                                break;
                            case 6:
                                FirebaseAuth.getInstance().signOut();
                                it = new Intent(MainActivity.this, Login.class);
                                startActivity(it);
                                finish();
                                break;
                        }
                        return false;
                    }
                }).build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mm_it_sobre:
                Intent it = new Intent(MainActivity.this, Sobre.class);
                startActivity(it);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
