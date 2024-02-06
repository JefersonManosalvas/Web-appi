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
        //String url="https://ejemplo2apimovil20240128220859.azurewebsites.net/api/Operaciones?a="+v1.getText()+"&b="+v2.getText();
        String url="http://192.168.1.106/CORECCION1/wvs/wapi.php?op=validar&cla="+v2.getText()+"&usu="+v1.getText();


        OkHttpClient cliente=new OkHttpClient();

        Request get=new Request.Builder().url(url).build();


        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "Fallo la conexión", Toast.LENGTH_SHORT).show();
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
                                //Toast.makeText(MainActivity.this, respuesta, Toast.LENGTH_SHORT).show();

//                                if (respuesta.equals("1")) {
//                                    Toast.makeText(MainActivity.this, "Usuario correcto", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(MainActivity.this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
//                                }


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