package bps.sultra.sutasbarcode.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import bps.sultra.sutasbarcode.R;
import bps.sultra.sutasbarcode.database.ModelLogin;
import bps.sultra.sutasbarcode.model.Batch;
import bps.sultra.sutasbarcode.model.Hp;
import bps.sultra.sutasbarcode.network.ApiClient;
import bps.sultra.sutasbarcode.network.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SentActivity extends AppCompatActivity {
    String barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barcode = getIntent().getStringExtra("code");
        showDokDialog(this);
    }

    private void showDokDialog(final Context context) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_jumlah_dok, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(promptsView);

        final EditText edit_blok = (EditText) promptsView.findViewById(R.id.editblok);
        final EditText edit_no_hp = (EditText) promptsView.findViewById(R.id.edithp);
        final EditText edit_nama = (EditText) promptsView.findViewById(R.id.editnama);
        final Spinner spinner_status = promptsView.findViewById(R.id.spinner_status);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, R.layout.style_spinner);
        adapter.setDropDownViewResource(R.layout.style_spinner);
        spinner_status.setAdapter(adapter);
        spinner_status.setEnabled(false);

        ModelLogin modelLogin = new ModelLogin(getApplicationContext());
        edit_no_hp.setText(modelLogin.getById(1).getNo_hp());
        edit_nama.setText(modelLogin.getById(1).getNama());
        spinner_status.setSelection(modelLogin.getById(1).getId_status()-1);




        final EditText edit_l1 = (EditText) promptsView.findViewById(R.id.editl1);
        final EditText edit_l2 = (EditText) promptsView.findViewById(R.id.editl2);

        edit_blok.setText(barcode);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Kirim", null)
                .setNegativeButton("Batal", null);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
