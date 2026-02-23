package com.example.mybookstore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;
import android.graphics.drawable.Drawable;
import android.content.res.Resources;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private TextView totalPriceText;
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize RecyclerView and other UI components
        cartRecyclerView = findViewById(R.id.cart_recyclerview);
        totalPriceText = findViewById(R.id.total_price);
        Button clearCartButton = findViewById(R.id.clear_cart);

        // Set up RecyclerView
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add divider decoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(cartRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        cartRecyclerView.addItemDecoration(dividerItemDecoration);

        // Set up the adapter
        updateCartView();

        clearCartButton.setOnClickListener(v -> {
            CartManager.getInstance().clearCart();
            updateCartView();
        });
    }

    private void updateCartView() {
        List<CartManager.CartItem> cartItems = CartManager.getInstance().getCartItems();

        // Set up the adapter with the updated cart items
        cartAdapter = new CartAdapter(this, cartItems);
        cartRecyclerView.setAdapter(cartAdapter);

        // Update the total price of the cart at the bottom
        totalPriceText.setText(String.format(Locale.US, "Total: $%.2f", CartManager.getInstance().getTotalPrice()));
    }
}
