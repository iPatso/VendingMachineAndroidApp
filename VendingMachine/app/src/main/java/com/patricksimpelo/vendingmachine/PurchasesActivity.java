package com.patricksimpelo.vendingmachine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PurchasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);

        //Store purchasesData into parallel arrays (items & itemsCount)
        Bundle purchasesData = getIntent().getExtras();
        if (purchasesData == null) {
            return;
        }
        String[] items = purchasesData.getStringArray("items"); //Item names
        String[] itemsCount = purchasesData.getStringArray("itemsCount"); //Count for each item
        TextView[] itemTextViews = {
                (TextView) findViewById(R.id.item1), (TextView) findViewById(R.id.item2),
                (TextView) findViewById(R.id.item3), (TextView) findViewById(R.id.item4),
                (TextView) findViewById(R.id.item5), (TextView) findViewById(R.id.item6),
                (TextView) findViewById(R.id.item7), (TextView) findViewById(R.id.item8),
                (TextView) findViewById(R.id.item9)
        };
        TextView[] itemCountTextViews = {
                (TextView) findViewById(R.id.item1Count), (TextView) findViewById(R.id.item2Count),
                (TextView) findViewById(R.id.item3Count), (TextView) findViewById(R.id.item4Count),
                (TextView) findViewById(R.id.item5Count), (TextView) findViewById(R.id.item6Count),
                (TextView) findViewById(R.id.item7Count), (TextView) findViewById(R.id.item8Count),
                (TextView) findViewById(R.id.item9Count)
        };
        for (int i=0; i<9; i++) {
            itemTextViews[i].setText(items[i]);
            itemCountTextViews[i].setText(itemsCount[i]);
        }

        //Set listener for resetButton
        Button returnButton = (Button)findViewById(R.id.returnButton);
        returnButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                }
        );
    }
}
