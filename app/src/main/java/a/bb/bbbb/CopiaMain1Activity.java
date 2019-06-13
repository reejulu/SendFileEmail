package a.bb.bbbb;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CopiaMain1Activity extends AppCompatActivity {
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        // EL FICHERO CON EL INFORME IRA GUARDADO EN path:
        //  para pruebas lo hemos llamado "text.txt"
        path = "data/data/a.bb.bbbb/files/test.txt";
        // SI EL FICHERO EXISTE HAY QUE BORRARLO PUES ESO SIGNIFICA QUE ES ANTIGUO
        File f = new File(path);
        f.delete();

        // FICHERO ORIGEN : DONDE ESTA EL INFORME
        // src/main/assets/test.txt         * esta localizado en directorio assets
        // FICHERO DESTINO : DONDE VAMOS A GUARDAR TEMPORALMENTE EL INFORME
        // src/main/assets/test.txt a --> data/data/a.bb.bbbb/files/test.txt

        // BUSCAMOS SI EL FICHERO DESTION ORIGEN EXISTE:
        // 1- SI EXISTE : LO COPIAMOS AL DESTINO
        // 2- NO EXISTE : ENVIAREMOS EL E-MAIL SIN EL INFORME INDICANDO QUE NO ESTA DISPONIBLE
            try {
                Context context = getApplicationContext();
                //opening text file located in assets directory
                AssetManager assetManager = getAssets();
                InputStream is = assetManager.open("test.txt");

                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                Log.i("MIAPP", "path interno es : " + path);
                FileOutputStream fos = openFileOutput("test.txt", MODE_PRIVATE);
                //FileOutputStream fos = new FileOutputStream(f);
                fos.write(buffer);
                fos.close();
                Log.i("MIAPP", "Se ha creado test.txt en data/data... : " + path);

            } catch (Exception e) {
                Log.i("MIAPP","No exite el fichero en assest- continuo");
                //throw new RuntimeException(e);
            }
        // REQUERIMOS ENVIAR EL E-MAIL: CON O SIN FICHERO ADJUNTADO
        Intent intent=new Intent();
        intent.putExtra("MESSAGE",path);
        setResult(2,intent);
        finish();//finishing activity
        Log.i("MIAPP","el E-mail ha sido ordenado en SendFileEmail.send ");
        // AHORA LA EJECUCION DEL PROGRAMA CONTINUARA EN MAINACTIVITY
    }
}
