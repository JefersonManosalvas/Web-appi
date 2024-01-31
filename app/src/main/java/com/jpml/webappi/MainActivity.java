package com.jpml.webappi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    EditText v1,v2;
    Button btn1;
    TextView res1;
    String respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v1=findViewById(R.id.edV1);
        v2=findViewById(R.id.edV2);
        btn1=findViewById(R.id.button);
        res1=findViewById(R.id.txtREs);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsumirApi();
            }
        });

    }

    public void ConsumirApi(){
        String url="http://10.10.30.142/CORECCION1/wvs/wapi.php?op=validar&cla="+v1.getText()+"&usu="+v2.getText();


        OkHttpClient cliente=new OkHttpClient();

        Request get=new Request.Builder().url(url).build();


        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{

                    ResponseBody responseBody=response.body();
                    if( response.isSuccessful()){



                        respuesta = responseBody.string();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                res1.setText(respuesta);
                            }
                        });
                    }else{
                        throw new IOException("Respuesta inesperada"+response);




                    }
                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        });



    }
}