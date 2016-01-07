package com.patricksimpelo.vendingmachine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InputActivity extends AppCompatActivity {

    final static double MAX_INPUT = 1000000000.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        //Set listener for nextButton
        Button nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        TextView errorText = (TextView)findViewById(R.id.errorText);
                        EditText amountInput = (EditText)findViewById(R.id.amountInput);
                        String userInput = amountInput.getText().toString();
                        if (userInput.length() == 0) {
                            //Empty input
                            errorText.setText(R.string.noInputAmountError);
                        } else if (Double.parseDouble(userInput) > MAX_INPUT) {
                            //Input too large
                            errorText.setText(R.string.maxInput);
                        } else {
                            //Send data to vending machine activity
                            Intent i = new Intent(view.getContext(), VendingMachineActivity.class);
                            i.putExtra("amountInputted", userInput);
                            startActivity(i);
                        }
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Reset errorText and amountInput to blank
        TextView errorText = (TextView)findViewById(R.id.errorText);
        errorText.setText(R.string.blank);
        EditText amountInput = (EditText)findViewById(R.id.amountInput);
        amountInput.setText(R.string.blank);
    }
}
