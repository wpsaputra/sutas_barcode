package bps.sultra.sutasbarcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String barcode = getIntent().getStringExtra("code");
        Toast.makeText(getApplicationContext(), barcode,  Toast.LENGTH_LONG).show();
    }
}
