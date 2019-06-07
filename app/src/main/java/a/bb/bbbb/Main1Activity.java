package a.bb.bbbb;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Main1Activity extends AppCompatActivity {
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        path = "data/data/a.bb.bbbb/files/test.txt";

        // COMPROBAMOS SI EXISTE EL FICHERO EN DIRECTORIO ASSETS
        AssetManager assetManager1 = getAssets();
        try {
            InputStream is1 = assetManager1.open("test.txt");
        } catch (IOException e) {
            // no existe el fichero, entonces continuar y luego no adjunta el fichero
            Log.i("MIAPP","No existe el fichero en assets  ");
            Log.i("MIAPP","Enviamos el e-mail sin fichero adjuto  ");
            new  SendFileEmail().send(path);
            finish();
            //e.printStackTrace();
        }
        // EXISTE EL FICHERO - ENVIAMOS E-MAIL CON FICHERO ADJUNTO
        File f = new File(path);  // siempre devuelve que f no existe
            // COPIA EL FICHERO DE ASSETS
            // src/main/assets/test.txt a --> data/data/a.bb.bbbb/files/test.txt
        if (!f.exists())
            try {
            Context context = getApplicationContext();
            //opening text file located in assets directory
            AssetManager assetManager = getAssets();
            InputStream is = assetManager.open("test.txt");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            Log.i("MIAPP","path interno es : "+path);
            FileOutputStream fos = openFileOutput("test.txt",MODE_PRIVATE);
            //FileOutputStream fos = new FileOutputStream(f);
            fos.write(buffer);
            fos.close();
            Log.i("MIAPP","Se ha creado test.txt en data/data... : "+path);

        } catch (Exception e) { throw new RuntimeException(e); }
        new  SendFileEmail().send(path);
        Log.i("MIAPP","el E-mail ha sido ordenado en SendFileEmail.send ");
    }
}
