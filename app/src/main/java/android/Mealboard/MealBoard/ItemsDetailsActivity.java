package android.Mealboard.MealBoard;

import android.Mealboard.MealBoard.database.DatabaseHandler;
import android.Mealboard.MealBoard.models.ItemModel;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.List;

public class ItemsDetailsActivity extends AppCompatActivity {
    ImageView CartDetail;
    Button btnAddToCart;
    ImageView imageItem;
    ImageView imageBack;
    TextView txtQuantity,txtPrice,textToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_details);

        CartDetail = (ImageView) findViewById(R.id.addtocart) ;
        imageItem = findViewById(R.id.image_item);
        txtQuantity = findViewById(R.id.text_quantity);
        txtPrice = findViewById(R.id.text_price);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        textToolbarTitle = findViewById(R.id.text_toolbar_title);
        imageBack = findViewById(R.id.image_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("image")).into(imageItem);
        textToolbarTitle.setText(getIntent().getStringExtra("name"));
        txtPrice.setText("\u20B9"+getIntent().getStringExtra("price"));
        txtQuantity.setText(getIntent().getStringExtra("quantity"));

        CartDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemsDetailsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemModel itemModel = new ItemModel();
                itemModel.setId(getIntent().getStringExtra("id"));
                itemModel.setCategory(getIntent().getStringExtra("category"));
                itemModel.setName(getIntent().getStringExtra("name"));
                itemModel.setImage(getIntent().getStringExtra("image"));
                itemModel.setQuantity(getIntent().getStringExtra("quantity"));
                itemModel.setPrice(getIntent().getStringExtra("price"));

                DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
                databaseHandler.AddToCart(itemModel);
                MDToast.makeText(ItemsDetailsActivity.this,"Added To Cart !",Toast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
            }
        });
        DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
        List<ItemModel> cartitems = databaseHandler.getcartitems();

        for (int i=0; i<=cartitems.size()-1; i++){
            Log.d("CART_DATA", cartitems.get(i).getName() + " - " + cartitems.get(i).getPrice());

        }
    }
}