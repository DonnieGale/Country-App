package edu.uga.cs.countryquiz;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/**
 * The main activity for the Country Quiz application.
 * This activity serves as the entry point and manages the initial database setup
 * and the display of the splash screen.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * Initializes the UI, sets up window insets for edge-to-edge display,
     * triggers database initialization, and displays the splash screen.
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     */
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

        // start database initialization
        new CountryDBInitializer().execute();

        // display splash if started
        Fragment splash = new SplashFragment();
        FragmentManager manager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            manager.beginTransaction().replace(R.id.fragmentContainerView2, splash).commit();
        }
    }


    /**
     * Initializes the database and initializes the needed app data from the database
     */
    private class CountryDBInitializer extends AsyncTask<Void, Void> {

        private CountryQuizData data;

        /**
         * Constructor for CountryDBInitializer.
         */
        public CountryDBInitializer() {
            data = new CountryQuizData(MainActivity.this);
        }

        /**
         * Performs database operations in the background.
         * Opens the database, populates it if empty, and stores countries in memory.
         *
         * @return null.
         */
        @Override
        protected Void doInBackground(Void... voids) {

            // Open the database
            data.open();

            // Check if database has been populated. If not, populate database from CSV file
            if (data.retrieveAllCountries().isEmpty()) {
                data.loadCountriesFromCSV(MainActivity.this);
            }

            // Retrieve all countries from the database and store them in memory for later access
            List<Country> countries = data.retrieveAllCountries();
            CountryRepository.getInstance().setCountries(countries);

            // Close the database
            data.close();

            return null;
        }

        /**
         * Logs completion of database.
         *
         * @param unused Result of background task.
         */
        @Override
        protected void onPostExecute(Void unused) {
            // Optional: log or notify
            Log.d("MainActivity", "Country DB initialization complete");
        }
    }

    /**
     * Helper method to show the SplashFragment.
     */
    public void showSplashScreen(){

        Fragment splash = new SplashFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainerView2, splash).commit();
    }



}