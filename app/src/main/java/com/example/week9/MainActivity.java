package com.example.week9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<TheatreData> theatreList = new ArrayList<>();
    ArrayList<TheatreData> scheduleList = new ArrayList<>();
    ArrayList<String> IDList = new ArrayList<String>();
    ArrayList<String> dateList = new ArrayList<String>();
    ArrayList<String> listviewList = new ArrayList<String>();
    ArrayList<String> eventsList = new ArrayList<String>();

    EditText etTime;
    Button search_button;
    ArrayAdapter<String> theatreAA, dateAA, eventAA;
    ArrayAdapter<String> listviewAA;
    Spinner spTeathre, spMovie, spDate;
    ListView listview;
    int selected_theatre, selected_date, selected_event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        spTeathre = findViewById(R.id.spTeathre);
        spMovie = findViewById(R.id.spMovie);
        spDate = findViewById(R.id.spDate);
        listview = findViewById(R.id.listview);
        etTime = findViewById(R.id.etTime);
        search_button = findViewById(R.id.search_button);

        initialize_spTheatre();
        initialize_spDate();
        initialize_spMovie();
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    executeButton();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initialize_spTheatre() {
        String url = "https://www.finnkino.fi/xml/TheatreAreas/";
        theatreList = new ArrayList<>();
        theatreList = ReadXML.TheatreAreas(url);
        ArrayList<String> tempNameList = new ArrayList<>();
        IDList.clear();
        for (int i = 0; i < theatreList.size(); i++) {
            tempNameList.add(theatreList.get(i).Theatre);
            IDList.add(theatreList.get(i).TheatreID);
        }
        theatreAA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tempNameList);
        theatreAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTeathre.setAdapter(theatreAA);
        spTeathre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_theatre = position;
                try {
                    executeButton();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void initialize_spDate() {
        dateList.clear();
        dateList.add("13.08.2020");
        dateList.add("01.09.2020");
        dateAA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dateList);
        dateAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDate.setAdapter(dateAA);
        spDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_date = position;
                try {
                    executeButton();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void initialize_spMovie() {
        String urlString = "https://www.finnkino.fi/xml/Events/";
        eventsList.clear();
        eventsList = ReadXML.Events(urlString);
        eventAA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventsList);
        eventAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMovie.setAdapter(eventAA);
        spMovie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_event = position;
                try {
                    executeButton();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public String getTime() {
        String given_time = etTime.getText().toString();
        if (given_time.isEmpty()) {
            System.out.println("\nisEmpty\n");
            return "E"; // = input is empty
        } else if (given_time.length() != 5) {
            return "L"; // input is false
        } else if (!given_time.contains(":")) {
            System.out.println("\nnotContains(':')\n");
            return "F"; // = input contains wrong characters
        } else if (!(Character.isDigit(given_time.charAt(0)) & Character.isDigit(given_time.charAt(1)))) {
            System.out.println("\n1st no digit / 2nd no digit\n");
            return "F"; // = input contains wrong characters
        } else if (!(Character.isDigit(given_time.charAt(3)) & Character.isDigit(given_time.charAt(4)))) {
            System.out.println("\n3rd no digit / 4th no digit\n");
            return "F"; // input contains wrong characters
        } else {
            String first_two = given_time.substring(0, 2);
            String last_two = given_time.substring(given_time.length() - 2);
            int first_2 = Integer.parseInt(first_two);
            int last_2 = Integer.parseInt(last_two);
            if (first_2 < -1 || first_2 > 24) {
                System.out.println("\nIncorrect hours\n");
                return "O"; // Incorrect time input.
            } else if (last_2 < -1 || last_2 > 61) {
                System.out.println("\nIncorrect minutes\n");
                return "O"; // Incorrect time input.
            } else {
                return given_time;
            }
        }
    }

    public void executeButton() throws ParseException {
        String timeStr = getTime();
        if (timeStr.equals("E")) {
            Toast.makeText(MainActivity.this, "Please, enter time.", Toast.LENGTH_LONG).show();
            etTime.setText("");
            etTime.setHint("HH:MM");
        } else if (timeStr.equals("L")) {
            Toast.makeText(MainActivity.this, "Anna aika oikeassa muodossa.", Toast.LENGTH_LONG).show();
            etTime.setText("");
            etTime.setHint("HH:MM");
        } else if (timeStr.equals("F")) {
            Toast.makeText(MainActivity.this, "Aika voi sisältää vain välimerkin ja numeroita.", Toast.LENGTH_LONG).show();
            etTime.setText("");
            etTime.setHint("HH:MM");
        } else if (timeStr.equals("O")) {
            Toast.makeText(MainActivity.this, "Antamasi aika ei ollut oikea.", Toast.LENGTH_LONG).show();
            etTime.setText("");
            etTime.setHint("HH:MM");
        } else {
            initialize_listView(timeStr);
        }
    }

    public void initialize_listView(String timeStr) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:MM");
        Date chosen_time = dateFormat.parse(timeStr);

        String id = null;
        String date = null;
        String event = null;
        for (int i = 0; i < IDList.size(); i++) {
            id = IDList.get(selected_theatre);
        }
        for (int j = 0; j < dateList.size(); j++) {
            date = dateList.get(selected_date);
        }
        for (int e = 0; e < eventsList.size(); e++) {
            event = eventsList.get(selected_event);
        }

        scheduleList.clear();
        String urlString = "https://www.finnkino.fi/xml/Schedule/?area=" + id + "&dt=" + date;
        scheduleList = ReadXML.Schedule(urlString);

        listviewAA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listviewList);
        listview.setAdapter(listviewAA);

        listviewList.clear();
        for (int i = 0; i < scheduleList.size(); i++) {
            if (scheduleList.get(i).getTitle().equals(event)) {
                String timeXML = scheduleList.get(i).dttmShowStart;
                String lastSevenChaarcters = timeXML.substring(timeXML.length() - 8);
                String time = lastSevenChaarcters.substring(0, 5);

                DateFormat xmlFormat = new SimpleDateFormat("HH:MM");
                Date movie_time = dateFormat.parse(time);

                if (chosen_time.compareTo(movie_time) == 0) {
                    System.out.println(time + "   " + scheduleList.get(i).Title + "\n    \t" + scheduleList.get(i).Theatre);
                    listviewList.add(time + "   " + scheduleList.get(i).Title + "\n    \t" + scheduleList.get(i).Theatre);
                    listviewAA.notifyDataSetChanged();
                } else if (chosen_time.compareTo(movie_time) < 0) {
                    System.out.println(time + "   " + scheduleList.get(i).Title + "\n    \t" + scheduleList.get(i).Theatre);
                    listviewList.add(time + "   " + scheduleList.get(i).Title + "\n    \t" + scheduleList.get(i).Theatre);
                    listviewAA.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Näytöksiä ei ole valitulla ajalla.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Kyseistä näytöstä ei ole valitulla ajalla.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
