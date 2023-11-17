package edu.illinois.cs465.myquizappwithlifecycle.rso_recycler_view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs465.myquizappwithlifecycle.R;
import edu.illinois.cs465.myquizappwithlifecycle.data.FoodListing;

public class FoodCardAdapter extends RecyclerView.Adapter<FoodCardAdapter.FoodCardHolder> {
    private List<FoodListing> foodListings = new ArrayList<>();
    private OnClickListener onClickListener;

    @NonNull
    @Override
    public FoodCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rso_food_card, parent, false);
        return new FoodCardHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull FoodCardHolder holder, @SuppressLint("RecyclerView") int position) {
        FoodListing currentFoodListing = foodListings.get(position);

        holder.textViewTitle.setText(currentFoodListing.food_name);
        // holder.textViewDate.setText(new Timestamp(currentFoodListing.createdAt.getTime()).toString());
        String expiryTime = new Timestamp(currentFoodListing.createdAt.getTime() + 30*60000).toString();
        holder.textViewExpiryTime.setText("Available until " + expiryTime.substring(0, expiryTime.length() - 7));
        // imageView.setColorFilter(Color.argb(255, 255, 255, 255)); // White Tint
        holder.statusButtonImg.setColorFilter(currentFoodListing.status.equals("LOW") ? Color.rgb(255, 255, 0) : Color.rgb(0, 255, 0));
        //TODO: Repeat for time and date use String.valueOf()

        holder.statusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onClickListener != null) {
                    onClickListener.onClick(view, position, foodListings.get(position));
                }
            }
        });

        holder.vertMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onClickListener != null) {
                    onClickListener.onClick(view, position, foodListings.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodListings.size();
    }

    public void setFoodListings(List<FoodListing> foodListings){
        this.foodListings = foodListings;
        notifyDataSetChanged();
    }

    public FoodListing getFoodListingAt(int position) {
        return foodListings.get(position);
    }
    class FoodCardHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        // private TextView textViewDate;
        private TextView textViewExpiryTime;
        private LinearLayout statusButton;
        private ImageView statusButtonImg;
        private ImageButton vertMenu;

        public FoodCardHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            // textViewDate = itemView.findViewById(R.id.text_view_card_date);
            textViewExpiryTime = itemView.findViewById(R.id.text_view_expiry_time);
            statusButton = itemView.findViewById(R.id.food_card_status_button);
            statusButtonImg = itemView.findViewById(R.id.food_card_status_button_img);
            vertMenu = itemView.findViewById(R.id.vert_icon_button);

        }
    }
    public interface OnClickListener {
        void onClick(View view, int position, FoodListing foodListing);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    // Method to remove a food listing
    public void removeFoodListing(int position) {
        if (position >= 0 && position < foodListings.size()) {
            foodListings.remove(position);
            notifyItemRemoved(position);
        }
    }

}




