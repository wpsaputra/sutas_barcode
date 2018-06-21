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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;
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

//        showDokDialog(this);
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
        final Spinner spinner_posisi_sekarang = promptsView.findViewById(R.id.spinner_posisi_sekarang);

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
                            if(spinner_status.getSelectedItemPosition()+1!=1){
                                // Jika penerimaan pertama bukan TU
                                Toast.makeText(context, "Penerimaan dokumen awal harus melewati TU", Toast.LENGTH_LONG).show();
                            }else{
                                saveBatch(edit_blok.getText().toString(), edit_no_hp.getText().toString(),
                                        spinner_posisi_sekarang.getSelectedItemPosition()+1, edit_l1.getText().toString(), edit_l2.getText().toString());
                                progressBar.setVisibility(View.VISIBLE);
                            }

                        }else{
                            Toast.makeText(context, "Isian jumlah L1/L2 ada yang kurang lengkap/kosong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.posisi_sekarang_array, R.layout.style_spinner);
        adapter2.setDropDownViewResource(R.layout.style_spinner);
        spinner_posisi_sekarang.setAdapter(adapter2);
        spinner_posisi_sekarang.setEnabled(false);
        spinner_posisi_sekarang.setSelection(0);

        // show it
        alertDialog.show();
    }

    private void showDokDialog(final Context context, Batch batch) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_jumlah_dok, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(promptsView);

        final EditText edit_blok = (EditText) promptsView.findViewById(R.id.editblok);
        final EditText edit_no_hp = (EditText) promptsView.findViewById(R.id.edithp);
        final EditText edit_nama = (EditText) promptsView.findViewById(R.id.editnama);
        final Spinner spinner_status = promptsView.findViewById(R.id.spinner_status);
        final Spinner spinner_posisi_sekarang = promptsView.findViewById(R.id.spinner_posisi_sekarang);

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

                            if(spinner_status.getSelectedItemPosition()+1==1&&spinner_posisi_sekarang.getSelectedItemPosition()+1>1){
                                // Jika penerimaan pertama bukan TU
                                Toast.makeText(context, "TU hanya boleh melakukan penerimaan TU", Toast.LENGTH_LONG).show();
                                return;
                            }

                            if(spinner_status.getSelectedItemPosition()+1==3&&spinner_posisi_sekarang.getSelectedItemPosition()+1<3){
                                // Jika penerimaan pertama bukan TU
                                Toast.makeText(context, "Pengentri hanya boleh melakukan entri atau validasi", Toast.LENGTH_LONG).show();
                                return;
                            }

                            saveBatch(edit_blok.getText().toString(), edit_no_hp.getText().toString(),
                                    spinner_posisi_sekarang.getSelectedItemPosition()+1, edit_l1.getText().toString(), edit_l2.getText().toString());
                            progressBar.setVisibility(View.VISIBLE);
                        }else{
                            Toast.makeText(context, "Isian jumlah L1/L2 ada yang kurang lengkap/kosong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //custom for batch
        edit_l1.setText(batch.getJumlah_l1());
        edit_l2.setText(batch.getJumlah_l2());
        edit_l1.setEnabled(false);
        edit_l2.setEnabled(false);

        String[] posisi_array = getResources().getStringArray(R.array.posisi_array);
        edit_posisi_sebelum.setText("Posisi Sebelum : "+posisi_array[Integer.parseInt(batch.getId_posisi())-1]+" ("+batch.getId_posisi()+")");
//        edit_posisi_sebelum.setText("Posisi Sebelum : "+batch.getId_posisi()+posisi_array[0]);
        edit_posisi_sebelum.setEnabled(false);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.posisi_sekarang_array, R.layout.style_spinner);
        adapter2.setDropDownViewResource(R.layout.style_spinner);
        spinner_posisi_sekarang.setAdapter(adapter2);

        spinner_posisi_sekarang.setEnabled(spinner_status.getSelectedItemPosition()+1==2);
        spinner_posisi_sekarang.setSelection(Integer.parseInt(batch.getId_posisi()));

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

    public void saveBatch(final String blok, final String no_hp, final int id_posisi, final String l1, final String l2) {
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
        template = template.replace("x7", String.valueOf(id_posisi));
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
//                btn_back.setText(call.request().url().toString());


                JsonObject batch = response.body();
//                String json = batch.get("batch").getAsJsonObject().get("records").getAsJsonArray().toString();
                List<Batch> batchList = new ArrayList<Batch>();

                try {
                    JsonArray recordArray = batch.get("batch").getAsJsonObject().get("records").getAsJsonArray();
                    for(int i=0; i<recordArray.size(); i++){
                        JsonArray record = recordArray.get(i).getAsJsonArray();
                        Batch batchTemp = new Batch();
                        batchTemp.setId(record.get(0).getAsString());
                        batchTemp.setKode_prop(record.get(1).getAsString());
                        batchTemp.setKode_kab(record.get(2).getAsString());
                        batchTemp.setKode_kec(record.get(3).getAsString());
                        batchTemp.setKode_desa(record.get(4).getAsString());
                        batchTemp.setKode_bs(record.get(5).getAsString());
                        batchTemp.setId_barcode(record.get(6).getAsString());
                        batchTemp.setNo_hp(record.get(7).getAsString());
                        batchTemp.setId_posisi(record.get(8).getAsString());
                        batchTemp.setJumlah_l1(record.get(9).getAsString());
                        batchTemp.setJumlah_l2(record.get(10).getAsString());
                        batchTemp.setDate_terima(record.get(11).getAsString());

                        batchList.add(batchTemp);
                    }

                }catch (Exception ex){
                    Log.e("Error", ex.getMessage());
                }

//                btn_back.setText(batchList.get(0).getId_posisi());
                if(batchList.size()>0){
                    showDokDialog(context, batchList.get(0));
                }else{
                    showDokDialog(context);
                }

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
