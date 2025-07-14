package com.example.nhom6btlon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<Product> cartList;
    private Context context;
    private boolean isCartView;

    public CartAdapter(Context context, List<Product> cartList, boolean isCartView) {
        this.context = context;
        this.cartList = cartList;
        this.isCartView = isCartView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView img;
        Button btnAddToCart, btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            price = itemView.findViewById(R.id.txtPrice);
            img = itemView.findViewById(R.id.imgProduct);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int i) {
        Product p = cartList.get(i);
        h.name.setText(p.name);
        h.price.setText(p.price);
        h.img.setImageResource(p.image);

        if (isCartView) {
            h.btnAddToCart.setVisibility(View.GONE);
        } else {
            h.btnAddToCart.setVisibility(View.VISIBLE);
            h.btnAddToCart.setOnClickListener(v -> {
                DatabaseHelper db = new DatabaseHelper(context);
                db.addToCart(p);
            });
        }

        h.btnDelete.setVisibility(View.VISIBLE);

        h.btnDelete.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(context);
            boolean deleted;
            if (isCartView) {
                deleted = db.deleteCartItem(p.getId());
            } else {
                deleted = db.deleteProduct(p.getId());
            }
            if (deleted) {
                cartList.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, cartList.size());
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
