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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.List;

import bps.sultra.sutasbarcode.R;
import bps.sultra.sutasbarcode.database.ModelLogin;
import bps.sultra.sutasbarcode.model.Batch;
import bps.sultra.sutasbarcode.model.Hp;
import bps.sultra.sutasbarcode.model.Login;
import bps.sultra.sutasbarcode.network.ApiClient;
import bps.sultra.sutasbarcode.network.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SentActivity extends AppCompatActivity {
    String barcode, status;
    AlertDialog alertDialog;
    ProgressBar progressBar;
    EditText edit_posisi_sebelum;

    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });

        barcode = getIntent().getStringExtra("code");
        getBatchByBarcode(barcode, this);

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

        edit_posisi_sebelum = (EditText) promptsView.findViewById(R.id.editposisisebelum);
        progressBar = promptsView.findViewById(R.id.progressBar);

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
        // get posisi sebelum
//        getBatchByBarcode(barcode);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Kirim", null)
                .setNegativeButton("Batal", null);

        // create alert dialog
        alertDialog = alertDialogBuilder.create();

        // add listener
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        if(edit_l1.getText().toString().length()>0&&edit_l2.getText().toString().length()>0){
                            //Dismiss once everything is OK.
//                            saveHp(edit_no_hp.getText().toString(), edit_nama.getText().toString(), spinner_status.getSelectedItemPosition()+1);
                            saveBatch(edit_blok.getText().toString(), edit_no_hp.getText().toString(),
                                    spinner_status.getSelectedItemPosition()+1, edit_l1.getText().toString(), edit_l2.getText().toString());
                            progressBar.setVisibility(View.VISIBLE);

                        }else{
                            Toast.makeText(context, "Isian jumlah L1/L2 ada yang kurang lengkap/kosong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

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

    public void saveBatch(final String blok, final String no_hp, final int id_status, final String l1, final String l2) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String template = "{\"kode_prop\":\"x1\",\"kode_kab\":\"x2\",\"kode_kec\":\"x3\"," +
                "\"kode_desa\":\"x4\",\"kode_bs\":\"x5\",\"no_hp\":\"x6\",\"id_posisi\":x7,\"jumlah_l1\":x8,\"jumlah_l2\":x9}";
//        template = template.replace("x1", no_hp);
//        template = template.replace("x2", nama);
//        template = template.replace("x3", String.valueOf(id_status));
        String[] split = blok.split("_");

        template = template.replace("x1", split[0]);
        template = template.replace("x2", split[1]);
        template = template.replace("x3", split[2]);
        template = template.replace("x4", split[3]);
        template = template.replace("x5", split[4]);
        template = template.replace("x6", no_hp);
        template = template.replace("x7", String.valueOf(id_status));
        template = template.replace("x8", l1);
        template = template.replace("x9", l2);



        apiService.saveBatch(template).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    setStatus("success");
                }else{
                    setStatus("failed");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                setStatus("error");
            }
        });

    }

    public void setStatus(String x){
        this.status = x;
        if(status.equalsIgnoreCase("success")){
            Toast.makeText(getApplicationContext(), "Transaksi berhasil dilakukan", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
        }else{
            Toast.makeText(getApplicationContext(), "Transaksi gagal dilakukan, mohon cek internet dan coba lagi", Toast.LENGTH_LONG).show();
        }
        progressBar.setVisibility(View.GONE);

    }

    public void closeActivity(){
        this.finish();
    }

    public void getBatchByBarcode(String barcode, final Context context){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String template = "id_barcode,eq,x1";
        template = template.replace("x1", barcode);

        apiService.getBatchByBarcode(template, "date_terima,desc").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                edit_posisi_sebelum.setText(response.message());
                Log.d("batch_barcode", response.message());
                Toast.makeText(context, response.body().toString(), Toast.LENGTH_LONG).show();
//                Toast.makeText(context, call.request().url().toString(), Toast.LENGTH_LONG).show();
                btn_back.setText(call.request().url().toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                edit_posisi_sebelum.setText(t.getMessage());
                Log.e("batch_barcode", t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                btn_back.setText(call.request().url().toString());

            }
        });

    }

}
