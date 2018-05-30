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

import bps.sultra.sutasbarcode.R;
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

        showRegistrasiDialog(this);
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
        progressBar = promptsView.findViewById(R.id.progressBar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, R.layout.style_spinner);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.style_spinner);
        spinner_status.setAdapter(adapter);

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
                            saveHp(edit_no_hp.getText().toString(), edit_nama.getText().toString(), spinner_status.getSelectedItemPosition()+1);
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

    public void saveHp(String no_hp, String nama, int id_status) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String template = "{\"no_hp\":\"x1\", \"nama\":\"x2\", \"id_status\":x3}";
        template = template.replace("x1", no_hp);
        template = template.replace("x2", nama);
        template = template.replace("x3", String.valueOf(id_status));


        apiService.saveHp2(template).enqueue(new Callback<String>() {
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


}
