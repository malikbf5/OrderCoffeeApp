/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int numberOfCoffees = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String priceMessage;
        String clientName;
        EditText name = findViewById(R.id.name_editText);
        clientName =  name.getText().toString();
        if (numberOfCoffees> 0) {
           priceMessage = orderSummary(clientName,numberOfCoffees);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + clientName);
            intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
      }
      else if ( numberOfCoffees == 0) {
            Toast.makeText(this,"You can't order less than 1 cup of coffee",Toast.LENGTH_SHORT).show();
      }

        }
    }
    public void increment(View view) {
       if (numberOfCoffees < 100)  { numberOfCoffees = numberOfCoffees +1;
        display(numberOfCoffees);
       }
       else if (numberOfCoffees == 100) {
           Toast.makeText(this,"You can't order more than 100 cup of coffee",Toast.LENGTH_SHORT).show();
       }
    }
    public void decrement(View view) {
       if( numberOfCoffees > 0 ) {
           numberOfCoffees = numberOfCoffees -1;
        display(numberOfCoffees);
       }
       else if (numberOfCoffees == 0) {
           Toast.makeText(this,"You can't order less than 1 cup of coffee",Toast.LENGTH_SHORT).show();
       }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */


    private String orderSummary (String name , int quantity ) {
        CheckBox creamCheck = findViewById(R.id.cream_check_box);
        CheckBox chocolateCheck = findViewById(R.id.chocolate_check_box);
        int whipped = 0, chocolate = 0,totalPrice;
        if (creamCheck.isChecked()) {whipped = 1;}
        if (chocolateCheck.isChecked() ) {chocolate = 2;}
        totalPrice = quantity * (5 + chocolate + whipped);

        return "Name: " + name + "\nAdd whipped cream? " + creamCheck.isChecked() + "\nAdd chocolate? " + chocolateCheck.isChecked() + "\nQuantity: " + quantity + "\nTotal: $" + totalPrice + "\nThank you!";
    }

}