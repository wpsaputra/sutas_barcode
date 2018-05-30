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
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import bps.sultra.sutasbarcode.R;
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
//        Toast.makeText(getApplicationContext(), barcode,  Toast.LENGTH_LONG).show();
        showDokDialog(this);
//        saveBatch();
//        saveHp();
    }

    private void showDokDialog(final Context context) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_jumlah_dok, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(promptsView);

        final EditText edit_blok = (EditText) promptsView.findViewById(R.id.editblok);
        final EditText edit_l1 = (EditText) promptsView.findViewById(R.id.editl1);
        final EditText edit_l2 = (EditText) promptsView.findViewById(R.id.editl2);

        edit_blok.setText(barcode);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Kirim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                String username = edit_name.getText().toString();
//                                String password = edit_password.getText().toString();
//                                ModelLogin modelLogin = new ModelLogin(context);
//                                Login login;
//
//                                if((username.equals("bps7401")&&password.equals("bps7401")||(username.equals("userbps")&&password.equals("userbps")))){
//                                    login = modelLogin.getById(1);
//                                    login.setFlag(1);
//                                    modelLogin.update(login);
//                                    Toast.makeText(context, "Login Succesful", Toast.LENGTH_SHORT).show();
//
//                                }else{
//                                    Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_SHORT).show();
//                                }
//
//                                initializeLogin(context);

                                saveHp(edit_l1.getText().toString(), edit_l2.getText().toString());


                            }
                        })
                .setNegativeButton("Batal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

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

    public void getPhoneNumbers() {
//        SubscriptionManager subscriptionManager = SubscriptionManager.from(getApplicationContext());
//        List<SubscriptionInfo> subsInfoList = subscriptionManager.getActiveSubscriptionInfoList();
//
//        Log.d("Test", "Current list = " + subsInfoList);
//
//        for (SubscriptionInfo subscriptionInfo : subsInfoList) {
//
//            String number = subscriptionInfo.getNumber();
//
//            Log.d("Test", " Number is  " + number);
//        }

        TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String mPhoneNumber = tMgr.getLine1Number();
    }

    public void saveBatch(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Batch batch = new Batch();
        batch.setKodeProp("74");
        batch.setKodeKab("05");
        batch.setKodeKec("001");
        batch.setKodeDesa("001");
        batch.setKodeBs("001B");
        batch.setJumlahL1(7);
        batch.setJumlahL2(9);

        Toast.makeText(getApplicationContext(), "tess",  Toast.LENGTH_LONG).show();

        apiService.savePost(batch).enqueue(new Callback<Batch>() {
            @Override
            public void onResponse(Call<Batch> call, Response<Batch> response) {
                if(response.isSuccessful()) {
//                    showResponse(response.body().toString());
                    Log.i("REST_API", "post submitted to API." + response.body().toString());
                    Toast.makeText(getApplicationContext(), response.body().toString(),  Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Batch> call, Throwable t) {
                Log.e("REST_API", "Unable to submit post to API.");
                Toast.makeText(getApplicationContext(), "Unable to submit post to API.",  Toast.LENGTH_LONG).show();

            }
        });

    }

    public void saveHp(String no_hp, String nama) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

//        Toast.makeText(getApplicationContext(), "tes save hp", Toast.LENGTH_LONG).show();


//        apiService.saveHp(no_hp, nama, 1).enqueue(new Callback<Hp>() {
//            @Override
//            public void onResponse(Call<Hp> call, Response<Hp> response) {
//            }
//
//            @Override
//            public void onFailure(Call<Hp> call, Throwable t) {
//            }
//
//
//        });

        apiService.saveHp2("{\"no_hp\":\"nobanobaye\", \"nama\":\"dsadasd\", \"id_status\":2}").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
//                    showResponse(response.body().toString());
                    Log.i("REST_API", "post submitted to API." + response.body().toString());
                    Toast.makeText(getApplicationContext(), "success"+response.body().toString()+"code"+response.code(),  Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "fail"+t.getMessage().toString(),  Toast.LENGTH_LONG).show();


            }
        });
    }


}
