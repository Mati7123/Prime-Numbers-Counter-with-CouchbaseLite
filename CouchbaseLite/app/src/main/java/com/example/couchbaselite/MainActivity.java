package com.example.couchbaselite;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;


public class MainActivity extends AppCompatActivity {

    private int first;
    private int second;

    private EditText firstNumber;
    private EditText secondNumber;
    private TextView primesCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNumber = findViewById(R.id.firstNumber);
        secondNumber = findViewById(R.id.secondNumber);
        primesCount = findViewById(R.id.primesCount);
        primesCount.setVisibility(View.GONE);

        CouchbaseLiteDb.createOrReadDatabase(this);
        first = CouchbaseLiteDb.getInitNumbers()[0];
        second = CouchbaseLiteDb.getInitNumbers()[1];

    }

    @Override
    protected void onResume() {
        super.onResume();
        firstNumber.setText(String.valueOf(first));
        secondNumber.setText(String.valueOf(second));
    }

    public void showCountOfPrimes(View view) {
        first = Integer.parseInt(firstNumber.getText().toString());
        second = Integer.parseInt(secondNumber.getText().toString());
        String output = String.valueOf(getCount(first, second));
        primesCount.setText("Between entered digits are " + output + " primes");
        primesCount.setVisibility(View.VISIBLE);
    }

    public void saveData(View view) {
        first = Integer.parseInt(firstNumber.getText().toString());
        second = Integer.parseInt(secondNumber.getText().toString());
        try {
            CouchbaseLiteDb.saveData(first, second);
            showToast(R.string.data_saved);
        } catch (CouchbaseLiteException ex) {
            showToast(R.string.data_not_saved);
            ex.printStackTrace();
        }
    }

    private void showToast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public int getCount(int first, int second) {

        if (first > second) {
            int tmp = second;
            second = first;
            first = tmp;
        }
        int count = 0;
        for (int i = first + 1; i < second; i++) {

            boolean flag = false;
            for (int j = 2; j <= i / 2; ++j) {
                if (i % j == 0) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                count++;
            if (i == 1) {
                count--;
            }
        }
        return count;
    }

    public void setFirstNumber(EditText firstNumber) {
        this.firstNumber = firstNumber;
    }

    public void setSecondNumber(EditText secondNumber) {
        this.secondNumber = secondNumber;
    }
}