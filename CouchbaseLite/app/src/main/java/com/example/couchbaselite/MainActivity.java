package com.example.couchbaselite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.*;

import java.io.File;
import java.net.URI;

public class MainActivity extends AppCompatActivity {


    private static final Object TAG = "Lag";
    int first;
    int second;
    EditText firstNumber;
    EditText secondNumber;
    Button saveButton;
    Button primesButton;
    Database database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstNumber = findViewById(R.id.firstNumber);
        secondNumber = findViewById(R.id.secondNumber);
        TextView primesCount = findViewById(R.id.primesCount);
        saveButton = findViewById(R.id.save);
        primesButton = findViewById(R.id.primes);

        CouchbaseLite.init(this);

        DatabaseConfiguration config = new DatabaseConfiguration();
        MutableDocument mutableDoc = new MutableDocument();


        System.out.println(Database.exists("mydb5", this.getFilesDir()));
        if (!Database.exists("mydb5", this.getFilesDir())) {
            mutableDoc.setInt("first", 2)
                    .setInt("second", 5);

            try {
                database = new Database("mydb5", config);
                database.save(mutableDoc);
            } catch (CouchbaseLiteException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                database = new Database("mydb5", config);
            } catch (CouchbaseLiteException ex) {
                ex.printStackTrace();
            }
        }


        //mutableDoc = database.getDocument("e9a2fa5f-4b76-4af5-836e-bfddb346e4e3").toMutable();
        //mutableDoc = database.getDocument(database.)
        Document document = database.getDocument(mutableDoc.getId());
        System.out.println(document);
        Query query = QueryBuilder.select(
                SelectResult.property("first"),
                SelectResult.property("second")
        )
                .from(DataSource.database(database));

        try {
            ResultSet rs = query.execute();
            for (Result result : rs) {
                first = result.getInt("first");
                System.out.println("First" + first);
                second = result.getInt("second");
                System.out.println();

            }
        } catch (CouchbaseLiteException e) {
            // Log.e("Sample", e.getLocalizedMessage());
        }

        // Create a new document (i.e. a record) in the database.


//
//
//// Save it to the database.
//        try {
//            database.save(mutableDoc);
//        } catch (CouchbaseLiteException ex) {
//            ex.printStackTrace();
//        }

        primesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first = Integer.parseInt(firstNumber.getText().toString());
                second = Integer.parseInt(secondNumber.getText().toString());
                if (first > second) {
                    int tmp = second;
                    second = first;
                    first = tmp;
                }
                int count = 0;
                for (int i = first; i <= second; i++) {

                    boolean flag = false;
                    for (int j = 2; j <= i / 2; ++j) {
                        if (i % j == 0) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag)
                        count++;
                }

                String output = String.valueOf(count);
                primesCount.setText(output);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first = Integer.parseInt(firstNumber.getText().toString());
                second = Integer.parseInt(secondNumber.getText().toString());
                mutableDoc.setInt("first", first)
                        .setInt("second", second);

                try {
                    database.save(mutableDoc);
                } catch (CouchbaseLiteException ex) {
                    ex.printStackTrace();
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println(firstNumber.getText());
        System.out.println(first);
        firstNumber.setText(String.valueOf(first));
        secondNumber.setText(String.valueOf(second));

    }


}