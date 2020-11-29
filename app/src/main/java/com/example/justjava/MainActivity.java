
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
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        EditText text = (EditText) findViewById(R.id.Name_ET);
        String  name = text.getText().toString();
        CheckBox wippedCreamCheckbox = (CheckBox)  findViewById(R.id.wipped_cream_checkbox);
        boolean hasCream = wippedCreamCheckbox.isChecked();
        CheckBox Chocolate = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate =  Chocolate.isChecked();
        int price = calculatePrice(hasCream,hasChocolate);

        String priceMessage = createSummary(price,hasCream,hasChocolate,name) ;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java order for " +name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
//        displayMessage(priceMessage);
    }

    /*dum mod*/




    /*
    *this method used to display summary of order
    * @param one for price;
    */
    private String createSummary(int price,boolean addWippedCream,boolean addChocolate,String name){
        String cream,choco;
        if(addWippedCream){
            cream = "Yes";
        }
        else cream = "NO";
        if (addChocolate) choco = "Yes";
        else choco = "NO";

        String printing = getString(R.string.Name,name);
        printing += " \n Added  Wipped Cream :  "+ cream;
        printing += "\n Added Chocolate : " + choco ;
        printing += "\n Quantity :" + quantity;
        printing += "\n Total :" + price ;
        printing += "\nThank You !";
        return  printing;
    }


    /*
    * method for calculating price with res to 5$ per cup
    * hard code val and return
    * no @param
    *
    */
    private int calculatePrice(boolean hascream, boolean hasChocolate){
        int price = 5;
        if(hascream){
            price +=1;
        }
        if(hasChocolate){
            price += 2;
        }
        return quantity*price;
    }
    //method for increasing the order

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */



    public void increment(View view) {
//        int quantity = 2;
        if(quantity == 100){
            Toast.makeText(getApplicationContext(), "cannot be more than 100!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = ++quantity;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
//            int quantity = 2;
            if(quantity == 0){
                Toast.makeText(getApplicationContext(), "cannot be less than 0!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            quantity = --quantity;
            displayQuantity(quantity);
    }
}
