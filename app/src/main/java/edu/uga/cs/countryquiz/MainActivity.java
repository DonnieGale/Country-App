package edu.uga.cs.countryquiz;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

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

        new CountryDBInitializer().execute();
        // CountryQuizData data = new CountryQuizData(this);
        // data.open();

        Fragment splash = new SplashFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainerView2, splash).commit();
    }



    private class CountryDBInitializer extends AsyncTask<Void, Void> {

        private CountryQuizData data;

        public CountryDBInitializer() {
            data = new CountryQuizData(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            data.open();

            // Check if countries table is empty
            if (data.retrieveAllCountries().isEmpty()) {
                data.loadCountriesFromCSV(MainActivity.this);
            }

            data.close();

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            // Optional: log or notify
            Log.d("MainActivity", "Country DB initialization complete");
        }
    }



}