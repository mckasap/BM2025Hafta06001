package com.example.bm2025hafta06001;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    public class myTask extends AsyncTask<String, String, String[][]> {
        @Override
        protected String[][] doInBackground(String... strings) {
            String degerler[][]= new String[4][2];
            try {
                Document doc = Jsoup.connect(strings[0]).get();
                String title = doc.title();
                Elements Currencies = doc.select("div.home-currency span.home-currency__type");
                Elements Amounts = doc.select("div.home-currency strong.home-currency__val");
                for (int i = 0; i < Currencies.size(); i++) {
                    degerler[i][0] = Currencies.get(i).text();
                    degerler[i][1] = Amounts.get(i).text();
                }

                Log.d("Title",title);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            return degerler;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TextView tvDolor = findViewById(R.id.tvDolor);
        TextView tvEuro = findViewById(R.id.tvEuro);
        TextView tvBIST = findViewById(R.id.tvBIST);
        TextView tvAltın = findViewById(R.id.tvAltın);
        myTask task = new myTask();
        try {
       String[][] degerler =    task.execute("https://www.milliyet.com.tr").get();
            tvDolor.setText(degerler[0][0]+" "+degerler[0][1]);
            tvEuro.setText(degerler[1][0]+" "+degerler[1][1]);
            tvBIST.setText(degerler[2][0]+" "+degerler[2][1]);
            tvAltın.setText(degerler[3][0]+" "+degerler[3][1]);

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}