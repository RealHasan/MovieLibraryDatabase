package com.example.sqldatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    //references to buttons and other control in the layout
    Button btn_add, btn_viewAll;
    EditText et_name, et_release;
    Switch sw_active;
    ListView lv_movieList;

    ArrayAdapter movieArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        et_release = findViewById(R.id.et_Release);
        et_name = findViewById(R.id.et_name);
        sw_active = findViewById(R.id.sw_active);
        lv_movieList = findViewById(R.id.lv_movieList);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        ShowMovieOnListView(dataBaseHelper);

        // button listeners for the add and view all buttons
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MovieModel movieModel;
                try {
                    movieModel = new MovieModel(-1,et_name.getText().toString(), Integer.parseInt(et_release.getText().toString()), sw_active.isChecked());
                    Toast.makeText(MainActivity.this, movieModel.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error adding movie", Toast.LENGTH_SHORT).show();
                    movieModel = new MovieModel( -1, "error", 0, false);
                }


                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                boolean success = dataBaseHelper.addOne(movieModel);
                Toast.makeText(MainActivity.this, "Successfully added" + success, Toast.LENGTH_SHORT).show();
                ShowMovieOnListView(dataBaseHelper);

            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                ShowMovieOnListView(dataBaseHelper);
                // Toast.makeText(MainActivity.this, movies.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        lv_movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieModel clickedMovie = (MovieModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickedMovie);
                ShowMovieOnListView(dataBaseHelper);
                Toast.makeText(MainActivity.this, "Deleted Movie", Toast.LENGTH_SHORT).show();

            }
        });





    }

    private void ShowMovieOnListView(DataBaseHelper dataBaseHelper2) {
        movieArrayAdapter = new ArrayAdapter<MovieModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getAll());
        lv_movieList.setAdapter(movieArrayAdapter);
    }
}