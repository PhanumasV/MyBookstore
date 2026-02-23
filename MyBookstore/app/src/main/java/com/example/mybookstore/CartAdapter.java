package com.example.mybookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<CartManager.CartItem> cartItems;

    public CartAdapter(Context context, List<CartManager.CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item view layout (cart_item.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartManager.CartItem cartItem = cartItems.get(position);

        // Display item name
        holder.itemName.setText(cartItem.getTitle());

        // Correctly display item quantity
        holder.num.setText(String.valueOf(cartItem.getQuantity()));

        // Set unit price and calculated total
        holder.price.setText(String.format(Locale.US, "$%.2f", cartItem.getPrice())); // Unit price
        holder.total.setText(String.format(Locale.US, "$%.2f", cartItem.getPrice() * cartItem.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // ViewHolder to hold the view references for each item
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView num;
        TextView price;
        TextView total;

        public CartViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            num = itemView.findViewById(R.id.num);
            price = itemView.findViewById(R.id.price);
            total = itemView.findViewById(R.id.total);
        }
    }
}
