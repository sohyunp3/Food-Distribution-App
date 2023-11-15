package edu.illinois.cs465.myquizappwithlifecycle;

import android.content.pm.PackageManager;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.annotation.MenuRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import edu.illinois.cs465.myquizappwithlifecycle.databinding.FoodPopupBinding;
import edu.illinois.cs465.myquizappwithlifecycle.databinding.LandingScreenBinding;
import edu.illinois.cs465.myquizappwithlifecycle.databinding.RsoBaseScreenBinding;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static String DEBUG = "DEBUG";
    private static final String KEY = "KEY";
    private static final String VALUE = "We passed the bundle of data";
    private Button falseButton;
    private Button trueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(DEBUG, "onCreate()");
        setContentView(R.layout.landing_screen);

//        RsoBaseScreenBinding binding = RsoBaseScreenBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            String value = new String(savedInstanceState.getString(KEY));
            if (value != null) {
                Log.d(DEBUG, value);
            }
        }
        createNotificationChannel();
        showNotification();

        findViewById(R.id.deleted_post_button).setOnClickListener(v -> {
            showDeletedEventPopup();
        });

        findViewById(R.id.food_popup).setOnClickListener(v -> {
            // Inflater to be able to grab button in dialog
            LayoutInflater inflater = getLayoutInflater();
            View dialogLayout = inflater.inflate(R.layout.food_popup, null);

        RsoBaseScreenBinding binding = RsoBaseScreenBinding.inflate(getLayoutInflater());
        binding.floatingActionButton.setOnClickListener(v -> showBottomDialog());

//        findViewById(R.id.floating_action_button).setOnClickListener(v -> {
//            showBottomDialog();
//        });
//        falseButton = (Button) findViewById(R.id.false_button);
//        trueButton = (Button) findViewById(R.id.true_button);

//        falseButton.setOnClickListener(this);
//        trueButton.setOnClickListener(this);
            showFoodInfoPopup(dialogLayout);

            Button seeMoreButton = (Button)dialogLayout.findViewById(R.id.see_more_button);
            seeMoreButton.setOnClickListener(b -> {
                Intent intent = new Intent(this, FoodInfoActivity.class);
                startActivity(intent);
            });

        });

        findViewById(R.id.post_food).setOnClickListener(v -> {
            Intent intent = new Intent(this, FoodPostActivity.class);
            startActivity(intent);
        });
    }

    protected void onSaveInstanceState(Bundle savedInstance) {
        super.onSaveInstanceState(savedInstance);
        Log.d(DEBUG, "onSavedInstanceState()");
        savedInstance.putString(KEY, VALUE);
    }

    protected void onStart() {
        super.onStart();
        Log.d(DEBUG, "OnStart()");
    }

    protected void onResume() {
        super.onResume();
        Log.d(DEBUG, "OnResume()");
    }

    protected void onRestart() {
        super.onRestart();
        Log.d(DEBUG, "OnRestart()");
    }

    protected void onPause() {
        super.onPause();
        Log.d(DEBUG, "OnPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.d(DEBUG, "OnStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(DEBUG, "OnDestroy()");
    }


    public void onClick(View v) {
        if (v.getId() == R.id.false_button) {
            Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.true_button) {
            Toast.makeText(this, "INCORRECT", Toast.LENGTH_SHORT).show();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Free Food Alert!";
            String description = "Pizza Available at Siebel CS NOW!";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.account_circle_24px) // replace with your own icon
                .setContentTitle("Free Food Alert!")
                .setContentText("Pizza Available at Siebel CS NOW!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }

//    bottomAppBar.setNavigationOnClickListener {
//            // Handle navigation icon press
//    }
//
//    bottomAppBar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.search -> {
//                    // Handle search icon press
//                    true
//                }
//                R.id.more -> {
//                    // Handle more item (inside overflow menu) press
//                    true
//                }
//                else -> false
//            }
//        }

    private void showDeletedEventPopup() {
        AlertDialog dialog = new MaterialAlertDialogBuilder(MainActivity.this)
            .setMessage("Post has been deleted by host")
            .setPositiveButton("Ok", null)
            .show();
    }

    private void showFoodInfoPopup(View dialog_layout) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(dialog_layout);
        dialog.show();
    }
    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        LinearLayout user1 = dialog.findViewById(R.id.user1);
        LinearLayout user2 = dialog.findViewById(R.id.user2);
        LinearLayout user3 = dialog.findViewById(R.id.user3);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        user1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Switching to user 1", Toast.LENGTH_SHORT).show();
            }
        });

        user2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Switching to user 2", Toast.LENGTH_SHORT).show();
            }
        });

        user3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Switching to user 3", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = com.google.android.material.R.style.MaterialAlertDialog_Material3_Animation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    private void showMenu(View v, @MenuRes int menuRes) {
        PopupMenu popup = new PopupMenu(MainActivity.this, v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Respond to menu item click.
                return false;
            }
        });
        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // Respond to popup being dismissed.
            }
        });
        popup.show();
    }
}

