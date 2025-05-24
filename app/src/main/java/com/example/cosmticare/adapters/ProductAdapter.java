package com.example.cosmticare.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosmticare.R;
import com.example.cosmticare.entity.Product;
import com.example.cosmticare.utils.FavoriteUtils;

import java.util.List;
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private static final int SMS_PERMISSION_REQUEST_CODE = 124;
    private static final String SUPPORT_PHONE_NUMBER = "29263403";
    private static final String TAG = "ProductAdapter";
    private final List<Product> productList;
    private final Context context;
    private OnItemRemovedListener itemRemovedListener;
    public interface OnItemRemovedListener {
        void onItemRemoved(int position);
    }
    public ProductAdapter(@NonNull Context context, @NonNull List<Product> products) {
        this.context = context;
        this.productList = products;
        this.itemRemovedListener = null;
    }
    public ProductAdapter(@NonNull Context context, @NonNull List<Product> products, @NonNull OnItemRemovedListener listener) {
        this.context = context;
        this.productList = products;
        this.itemRemovedListener = listener;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.imageView.setImageResource(product.imageResId);
        holder.name.setText(product.name);
        holder.price.setText(product.price);
        holder.availability.setText(product.availability);
        holder.description.setText(product.description);

        updateFavoriteButton(holder.btnFavorite, product.id);

        holder.btnFavorite.setOnClickListener(v -> {
            boolean isNowFavorite = FavoriteUtils.toggleFavorite(context, product.id);
            updateFavoriteButton(holder.btnFavorite, product.id);

            Toast.makeText(context,
                    product.name + (isNowFavorite ? " ajouté aux favoris" : " retiré des favoris"),
                    Toast.LENGTH_SHORT).show();

            if (!isNowFavorite && itemRemovedListener != null) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    itemRemovedListener.onItemRemoved(currentPosition);
                }
            }
        });

        holder.btnBuy.setOnClickListener(v -> {
            Activity activity = (Activity) context;
            showConfirmationDialog(activity);
        });
    }

    private void updateFavoriteButton(Button button, long productId) {
        if (FavoriteUtils.isFavorite(context, productId)) {
            button.setText("Retirer des favoris");
            button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.grey));
        } else {
            button.setText("Ajouter aux favoris");
            button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.errorColor));
        }
    }

    private void showConfirmationDialog(Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("Confirmation")
                .setMessage("Vous allez recevoir un message du service Cosmticare.")
                .setPositiveButton("OK", (dialog, which) -> {
                    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.SEND_SMS},
                                SMS_PERMISSION_REQUEST_CODE);
                    } else {
                        sendThankYouSms(activity);
                    }
                })
                .setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void sendThankYouSms(Context context) {
        SmsManager smsManager = SmsManager.getDefault();
        String message = "Achat confirmé! Merci pour votre confiance.😊";
        try {
            smsManager.sendTextMessage(SUPPORT_PHONE_NUMBER, null, message, null, null);
            Toast.makeText(context, "SMS envoyé !", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de l'envoi du SMS", e);
            Toast.makeText(context, "Échec de l'envoi du SMS", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, price, availability, description;
        Button btnFavorite, btnBuy;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewProduct);
            name = itemView.findViewById(R.id.textViewProductName);
            price = itemView.findViewById(R.id.textViewPrice);
            availability = itemView.findViewById(R.id.textViewAvailability);
            description = itemView.findViewById(R.id.textViewDescription);
            btnFavorite = itemView.findViewById(R.id.buttonFavorite);
            btnBuy = itemView.findViewById(R.id.buttonBuy);
        }
    }
}
