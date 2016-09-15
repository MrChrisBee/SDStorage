package de.cokuss.chrisbee.sdstorage;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    File externDirPrivat;
    File externDirOeffentlich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Beispiel zum Zugriff auf eine SDKarte
         */
        sysoSpacer(2);
        final String state = Environment.getExternalStorageState();
        switch (state) {
            case Environment.MEDIA_MOUNTED:
                System.out.println("schreiben und lesen möglich");
                System.out.println("Pfad zum externen Speicher : " + Environment.getExternalStorageDirectory());
                System.out.println("Pfad zum External Picures Verzeichniss : " + (externDirOeffentlich = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)));
                System.out.println(externDirPrivat = getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS));
                schreibsMir(externDirPrivat);
                break;
            case Environment.MEDIA_MOUNTED_READ_ONLY:
                System.out.println("nur lesen möglich");
                break;
            case Environment.MEDIA_CHECKING:
                System.out.println("bin gerade am Testen");
                break;
            default:
                System.out.println("kann weder lesen noch schrieben");
        }
    }

    private void sysoSpacer(int i) {
        for(int j=0;j<i;j++) {
            System.out.println("-");
        }
    }

    private void dir(File dir) {
        File[] results;
        results = dir.listFiles();
        System.out.println("File list:");
        for (File entry : results) {
            System.out.println(entry);
        }
    }

    private void schreibsMir(File file) {
        if (file.canWrite()) {
            sysoSpacer(5);
            Toast.makeText(getApplicationContext(), "Ich schreibe jetzt!", Toast.LENGTH_LONG).show();
            System.out.println("Ich kann auf dieses Fileobjekt schreibend zugreifen");
            File toWrite = new File(file + "/demo.txt");
            System.out.println(toWrite.length());
            try (FileOutputStream fos = new FileOutputStream(toWrite, true);
                 OutputStreamWriter ow = new OutputStreamWriter(fos);
                 BufferedWriter bw = new BufferedWriter(ow)) {
                bw.write("Erste Zeile");
                bw.newLine();
                bw.write("Zweite Zeile");
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage().toString());
            }
            System.out.println(toWrite.length());


            try (FileInputStream fis = new FileInputStream(toWrite);
                 InputStreamReader isr = new InputStreamReader(fis);
                 BufferedReader br = new BufferedReader(isr)) {
                String str;
                while ((str = br.readLine()) != null) {
                    System.out.println(str);
                }
                dir(file);

            } catch (IOException e) {
                System.out.println(e.getMessage().toString());
            }


        } else {
            Toast.makeText(getApplicationContext(), "Schreibzugriff ist in diesem Verzeiuchniss nicht möglich!", Toast.LENGTH_LONG).show();
            System.out.println("Ich kann auf dieses Fileobjekt NICHT schreibend zugreifen");
        }
    }
}
