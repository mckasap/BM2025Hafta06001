package com.example.bm2025hafta06001;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public class myTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                Document doc = Jsoup.connect(strings[0]).get();
                String title = doc.title();
                Elements Currencies = doc.select("div.home-currency span.home-currency__type");
                Elements Amounts = doc.select("div.home-currency strong.home-currency__val");
                for (int i = 0; i < Currencies.size(); i++) {
                    Log.d("Currency", Currencies.get(i).text());
                    Log.d("Amount", Amounts.get(i).text());
                }

                Log.d("Title",title);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myTask task = new myTask();
        task.execute("https://www.milliyet.com.tr");
    }
}