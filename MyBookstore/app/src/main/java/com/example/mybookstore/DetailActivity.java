package com.example.mybookstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;

public class DetailActivity extends AppCompatActivity {

    private double bookPrice;
    private String bookTitle;
    private int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Retrieve data from intent
        Intent intent = getIntent();
        bookTitle = intent.getStringExtra("BOOK_TITLE");
        String bookAuthor = intent.getStringExtra("BOOK_AUTHOR");
        String bookDescription = intent.getStringExtra("BOOK_DESCRIPTION");
        bookPrice = intent.getDoubleExtra("BOOK_PRICE", 0.0);
        int bookImageResId = intent.getIntExtra("BOOK_IMAGE", R.drawable.default_image); // Fallback image

        // Initialize UI components
        ImageView bookImageView = findViewById(R.id.book_image);
        TextView titleTextView = findViewById(R.id.book_title);
        TextView descriptionTextView = findViewById(R.id.book_description);
        TextView authorTextView = findViewById(R.id.book_author);
        TextView priceTextView = findViewById(R.id.book_price);
        EditText quantityInput = findViewById(R.id.quantity_input);
        Button addItemButton = findViewById(R.id.add_item_button);

        // Set data to views
        titleTextView.setText(bookTitle);
        descriptionTextView.setText(bookDescription);
        authorTextView.setText(getString(R.string.author_label, bookAuthor));
        priceTextView.setText(String.format(Locale.US, "$%.2f", bookPrice));

        // Use Glide to load the image (from URL or drawable resource)
        Glide.with(this)
                .load(bookImageResId)
                .downsample(DownsampleStrategy.AT_MOST) // Resize the image to fit the display
                .into(bookImageView);

        // Handle "Add This Item" button click
        addItemButton.setOnClickListener(v -> {
            String quantityStr = quantityInput.getText().toString().trim();

            // Validate input
            if (quantityStr.isEmpty()) {
                Toast.makeText(this, "Please enter a quantity!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    Toast.makeText(this, "Quantity must be at least 1!", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid quantity!", Toast.LENGTH_SHORT).show();
                return;
            }

            double totalCost = bookPrice * quantity;

            // Send data back to BookListActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("BOOK_TITLE", bookTitle);
            resultIntent.putExtra("BOOK_AUTHOR", bookAuthor);
            resultIntent.putExtra("BOOK_DESCRIPTION", bookDescription);
            resultIntent.putExtra("BOOK_PRICE", bookPrice);
            resultIntent.putExtra("BOOK_QUANTITY", quantity);
            resultIntent.putExtra("BOOK_IMAGE", bookImageResId);
            setResult(RESULT_OK, resultIntent);
            finish();
            // Close activity and return to BookListActivity
        });
    }
}
