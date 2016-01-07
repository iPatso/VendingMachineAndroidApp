package com.patricksimpelo.vendingmachine;

import android.content.Intent;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Enumeration;
import java.util.Hashtable;

public class VendingMachineActivity extends AppCompatActivity {
    private Hashtable<String, Integer> purchases = new Hashtable<>();

    //Sets all nine buttons with their name, cost, and onClickListener
    private void setItemButtonsData() {
        Button[] itemButtons = {
                (Button)findViewById(R.id.item1Button), (Button)findViewById(R.id.item2Button),
                (Button)findViewById(R.id.item3Button), (Button)findViewById(R.id.item4Button),
                (Button)findViewById(R.id.item5Button), (Button)findViewById(R.id.item6Button),
                (Button)findViewById(R.id.item7Button), (Button)findViewById(R.id.item8Button),
                (Button)findViewById(R.id.item9Button)
        } ;
        String[] items = {
                getString(R.string.item1_name), getString(R.string.item2_name),
                getString(R.string.item3_name), getString(R.string.item4_name),
                getString(R.string.item5_name), getString(R.string.item6_name),
                getString(R.string.item7_name), getString(R.string.item8_name),
                getString(R.string.item9_name)
        };
        String[] itemCosts = {
                getString(R.string.item1_value), getString(R.string.item2_value),
                getString(R.string.item3_value), getString(R.string.item4_value),
                getString(R.string.item5_value),  getString(R.string.item6_value),
                getString(R.string.item7_value), getString(R.string.item8_value),
                getString(R.string.item9_value)
        };

        for (int i=0; i<9; i++) {
            Button itemButton = itemButtons[i];
            final String itemString = items[i];
            final String itemCostString = itemCosts[i];
            String itemInfo = itemString + "\n$" + itemCostString;
            purchases.put(itemString, 0);
            itemButton.setText(itemInfo);
            itemButton.setOnClickListener(
                    new Button.OnClickListener() {
                        public void onClick(View view) {
                            TextView chosenItem = (TextView)findViewById(R.id.chosenItem);
                            TextView chosenItemCost = (TextView)findViewById(R.id.chosenItemCost);
                            TextView errorText = (TextView)findViewById(R.id.errorText);
                            errorText.setText(R.string.blank);
                            chosenItem.setText(itemString);
                            chosenItemCost.setText(itemCostString);
                        }
                    }
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vending_machine);

        //Set item buttons
        setItemButtonsData();

        //Stores inputted amount as a double
        Bundle vendingMachineData = getIntent().getExtras();
        if (vendingMachineData == null) {
            return;
        }
        String userInput = vendingMachineData.getString("amountInputted");
        Double decimalAmount = Double.parseDouble(userInput);
        BigDecimal bd = new BigDecimal(decimalAmount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        decimalAmount = bd.doubleValue();

        //Display balance
        final TextView balanceText = (TextView)findViewById(R.id.balanceText);
        balanceText.setText(String.format("%1.2f", decimalAmount));

        //Set listener for purchaseButton
        final Button purchaseButton = (Button)findViewById(R.id.purchaseButton);
        final MediaPlayer mpClick = MediaPlayer.create(this, R.raw.cha_ching);
        purchaseButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        TextView errorText = (TextView)findViewById(R.id.errorText);
                        TextView itemCostTextView = (TextView)findViewById(R.id.chosenItemCost);
                        errorText.setText(R.string.blank);
                        String itemCostText = itemCostTextView.getText().toString();
                        if (itemCostText.length() == 0) {
                            errorText.setText(R.string.noItemChosenError);
                        } else {
                            TextView chosenItem = (TextView)findViewById(R.id.chosenItem);
                            String itemString = chosenItem.getText().toString();
                            double itemCost = Double.parseDouble(itemCostText);
                            double balance = Double.parseDouble(balanceText.getText().toString());
                            if (balance < itemCost) {
                                errorText.setText(R.string.insufficientFundsError);
                            } else {
                                mpClick.start();
                                balance -= itemCost;
                                balanceText.setText(String.format("%1.2f", balance));
                                //Update 'purchases' hash table
                                int oldCount = purchases.get(itemString);
                                purchases.put(itemString, oldCount+1);
                            }
                        }
                    }
                }
        );

        //Set listener for viewPurchasesButton
        Button viewPurchasesButton = (Button)findViewById(R.id.viewPurchasesButton);
        viewPurchasesButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        //Storing 'purchases' into parallel arrays of 'keys' and 'values'
                        Enumeration itemsEnum = purchases.keys();
                        String[] items = new String[9];
                        String[] itemsCount = new String[9];
                        String temp;
                        int i = 0;
                        while(itemsEnum.hasMoreElements()) {
                            temp = (String) itemsEnum.nextElement();
                            items[i] = temp+": ";
                            itemsCount[i] = " " + purchases.get(temp).toString();
                            i++;
                        }

                        //Preparing to send data to new Activity
                        Intent intent = new Intent(view.getContext(), PurchasesActivity.class);
                        intent.putExtra("items", items);
                        intent.putExtra("itemsCount", itemsCount);
                        startActivity(intent);
                    }
                }
        );

        //Set listener for resetButton
        Button resetButton = (Button)findViewById(R.id.resetButton);
        resetButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                }
        );
    }
}
