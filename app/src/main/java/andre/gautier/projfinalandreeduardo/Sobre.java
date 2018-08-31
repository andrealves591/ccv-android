package andre.gautier.projfinalandreeduardo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.Button;


public class Sobre extends AppCompatActivity {

    private Button btLigar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btLigar = findViewById(R.id.sb_bt_contato);

        ActivityCompat.requestPermissions(Sobre.this, new String[]{Manifest.permission.CALL_PHONE}, 0);

        btLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Sobre.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Sobre.this, Manifest.permission.CALL_PHONE)) {
                        ActivityCompat.requestPermissions(Sobre.this, new String[]{Manifest.permission.CALL_PHONE}, 0);
                    } else {
                        ActivityCompat.requestPermissions(Sobre.this, new String[]{Manifest.permission.CALL_PHONE}, 0);
                    }
                } else {
                    Uri uri = Uri.parse("tel: " + "51982580748");
                    Intent it = new Intent(Intent.ACTION_CALL, uri);
                    startActivity(it);
                }
            }
        });
    }
}