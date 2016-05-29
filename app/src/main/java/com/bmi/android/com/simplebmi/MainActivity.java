package com.bmi.android.com.simplebmi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends ActionBarActivity implements View.OnClickListener  {

    RadioButton male;
    RadioButton female;
    EditText weight;
    EditText height;
    EditText age;
    TextView Con;
    Button check;
    TextView result;
    TextView ibw;
    TextView fat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //male = (RadioButton) findViewById(R.id.male);
        //female = (RadioButton) findViewById(R.id.female);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height);
        age = (EditText)findViewById(R.id.age);
        Con= (TextView) findViewById(R.id.Con);

       /* if(male.isSelected()) {
            weight.setTextColor(Color.RED);
            height.setTextColor(Color.BLUE);
        } else if (female.isSelected())
        {
            weight.setTextColor(Color.CYAN);
            height.setTextColor(Color.CYAN);
        }*/

        result = (TextView) findViewById(R.id.result);
        ibw = (TextView) findViewById(R.id.ibw);
        fat = (TextView) findViewById(R.id.fat);
        check = (Button) findViewById(R.id.check);
        Con = (TextView) findViewById(R.id.Con);
        check.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        double A=0;
        double H=0;
        double W=0;
        try {
            W = Double.parseDouble(weight.getText().toString());
            H = Double.parseDouble(height.getText().toString());
            A = Double.parseDouble(age.getText().toString());
        }
        catch (NumberFormatException nfe) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        try {

            if (A < 10 || A > 99) {
                Toast.makeText(this, "Age should be between 10 to 99", Toast.LENGTH_LONG).show();
            }
            else if (H <= 60 && H >= 251) {
                Toast.makeText(this, "Height should be between 60 & 251", Toast.LENGTH_LONG).show();
            }
            else if (W <= 25 && W >= 350) {
                Toast.makeText(this, "Weight should be between 25 & 350", Toast.LENGTH_LONG).show();
            } else {


                double R = W / ((H / 100) * (H / 100));
                double Bmi = Double.parseDouble(df.format(R));
                double R1 = (double) (50 + 2.1 * ((H - 152.4) / 2.54));
                double Ibw = Double.parseDouble(df.format(R1));
                double R2 = (double) ((1.20 * Bmi) + (0.23 * A) - (10.8 * 1) - 5.4);
                double Fatper = Double.parseDouble(df.format(R2));

                String Conclusion = null;
                int flag = 0;

                // For adults ---------***----------

                if (Bmi <= 18.5) {
                    int difference = (int) (Ibw - W);
                    Conclusion = "You are underweight! \nYou should gain " + difference + "KGs to cover up!";
                    flag = 1;
                } else if (Bmi >= 25 && Bmi <= 29.9) {
                    int difference = (int) (W - Ibw);
                    Conclusion = "You are overweight! \nYou should lose " + difference + "KGs to cover up!";
                    flag = 1;
                } else if (Bmi > 30) {
                    int difference = (int) (W - Ibw);
                    Conclusion = "You are Obese! \nYou should lose " + difference + "KGs to match up!";
                    flag = 1;
                } else if (flag == 0) {
                    Conclusion = "You are fit and fine! Enjoy life :)";
                }

                result.setText("Your BMI is : " + Bmi);
                ibw.setText("Ideal Body Weight   : " + Ibw);
                fat.setText("Fat Percentage     : " + Fatper + "%");
                Con.setText(Conclusion);
            }
        }
            catch (NumberFormatException nfe) {
                Toast.makeText(this, "Oh crap! Please check the values.", Toast.LENGTH_SHORT).show();
            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.about:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Created By Ashish Barad \nwww.AshishBarad.com \nfeedback@AshishBarad.com\nV. 1.0.1");
                builder1.setCancelable(true);
                builder1.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();

        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
