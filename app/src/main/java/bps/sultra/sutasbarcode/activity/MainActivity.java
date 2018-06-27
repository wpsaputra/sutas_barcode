package bps.sultra.sutasbarcode.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import bps.sultra.sutasbarcode.R;
import bps.sultra.sutasbarcode.database.ModelLogin;
import bps.sultra.sutasbarcode.database.ModelProvinsi;
import bps.sultra.sutasbarcode.model.Batch;
import bps.sultra.sutasbarcode.model.Login;
import bps.sultra.sutasbarcode.network.ApiClient;
import bps.sultra.sutasbarcode.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String status = "status_retrofit";
    AlertDialog alertDialog;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // making toolbar transparent
        transparentToolbar();
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
            }
        });
        findViewById(R.id.btn_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WebActivity.class));
            }
        });

        ModelLogin modelLogin = new ModelLogin(this);
        if(modelLogin.getById(1).getFlag()!=1){
            showRegistrasiDialog(this);
        }

    }

    private void transparentToolbar() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void showRegistrasiDialog(final Context context) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_registrasi, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        final EditText edit_no_hp = (EditText) promptsView.findViewById(R.id.edit_no_hp);
        final EditText edit_nama = (EditText) promptsView.findViewById(R.id.edit_nama);
        final Spinner spinner_status = promptsView.findViewById(R.id.spinner_status);
        final Spinner spinner_provinsi = promptsView.findViewById(R.id.spinner_provinsi);
        progressBar = promptsView.findViewById(R.id.progressBar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, R.layout.style_spinner);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.style_spinner);
        spinner_status.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ModelProvinsi(this).getAllArrayList());
        adapter2.setDropDownViewResource(R.layout.style_spinner);
        spinner_provinsi.setAdapter(adapter2);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Kirim", null);

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
                        if(edit_no_hp.getText().toString().length()>11&&edit_nama.getText().toString().length()>3){
                            //Dismiss once everything is OK.
                            getHpByPhoneNumber(edit_no_hp.getText().toString(), edit_nama.getText().toString(), spinner_status.getSelectedItemPosition()+1,
                                    spinner_provinsi.getSelectedItem().toString().substring(1,3), context);
//                            saveHp(edit_no_hp.getText().toString(), edit_nama.getText().toString(), spinner_status.getSelectedItemPosition()+1);
                            progressBar.setVisibility(View.VISIBLE);

                        }else{
                            Toast.makeText(context, "Isian ada yang kurang lengkap/kosong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        // show it
        alertDialog.show();
    }

    public void saveHp(final String no_hp, final String nama, final int id_status, final String kode_prop) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String template = "{\"no_hp\":\"x1\", \"nama\":\"x2\", \"id_status\":x3, \"kode_prop\":x4}";
        template = template.replace("x1", no_hp);
        template = template.replace("x2", nama);
        template = template.replace("x3", String.valueOf(id_status));
        template = template.replace("x4", kode_prop);


        apiService.saveHp(template).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    setStatus("success", no_hp, nama, id_status, kode_prop);
                }else{
                    setStatus("failed", no_hp, nama, id_status, kode_prop);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                setStatus("error", no_hp, nama, id_status, kode_prop);
            }
        });

    }

    public void setStatus(String x, String no_hp, String nama, int id_status, String kode_prop){
        this.status = x;
        if(status.equalsIgnoreCase("success")){
            Toast.makeText(getApplicationContext(), "Transaksi berhasil dilakukan", Toast.LENGTH_LONG).show();
            Login login = new Login();
            login.setId(1);
            login.setNama(nama);
            login.setNo_hp(no_hp);
            login.setNama(nama);
            login.setId_status(id_status);
            login.setFlag(1);
            login.setKode_prop(Integer.parseInt(kode_prop));

            ModelLogin modelLogin = new ModelLogin(getApplicationContext());
            modelLogin.update(login);

            alertDialog.dismiss();
        }else{
            Toast.makeText(getApplicationContext(), "Transaksi gagal dilakukan, mohon cek internet dan coba lagi", Toast.LENGTH_LONG).show();
        }
        progressBar.setVisibility(View.GONE);

    }

    public void getHpByPhoneNumber(final String no_hp, final String nama, final int id_status, final String kode_prop, final Context context){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService.getHpByPhoneNumber(no_hp).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject hp = response.body();
                try {
                    if(hp.get("no_hp").getAsString().equalsIgnoreCase(no_hp)){
                        Toast.makeText(context, "Gagal melakukan pendaftaran, sudah ada no hp yang sama segera hubungi admin", Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);

                }catch (Exception ex){
                    Log.e("Error", ex.getMessage());
                    saveHp(no_hp, nama, id_status, kode_prop);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("batch_hp", t.getMessage());
                setStatus("error", no_hp, nama, id_status, kode_prop);

            }
        });

    }


}
