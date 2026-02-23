package com.example.mybookstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mybookstore.utils.ImageUtils;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Category Click Listeners
        FrameLayout categoryCookbooks = findViewById(R.id.category_cookbooks);
        FrameLayout categoryHumour = findViewById(R.id.category_humour);
        FrameLayout categoryJapaneseFiction = findViewById(R.id.category_japanese_fiction);
        FrameLayout categoryManga = findViewById(R.id.category_manga);


        categoryCookbooks.setOnClickListener(v -> openBookList("Cookbooks"));
        categoryHumour.setOnClickListener(v -> openBookList("Humour"));
        categoryJapaneseFiction.setOnClickListener(v -> openBookList("Japanese Fiction"));
        categoryManga.setOnClickListener(v -> openBookList("Manga"));

        // Load Optimized Images
        loadOptimizedImages();
    }

    private void openBookList(String category) {
        Intent intent = new Intent(this, BookListActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }

    private void loadOptimizedImages() {
        ImageView imgCookbooks = findViewById(R.id.image_cookbooks);
        ImageView imgHumour = findViewById(R.id.image_humour);
        ImageView imgJapaneseFiction = findViewById(R.id.image_japanese_fiction);
        ImageView imgManga = findViewById(R.id.image_manga);

        if (imgCookbooks != null) {
            imgCookbooks.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.cookbooks, 600, 400));
        }
        if (imgHumour != null) {
            imgHumour.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.humour, 600, 400));
        }
        if (imgJapaneseFiction != null) {
            imgJapaneseFiction.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.japanese_fiction, 600, 400));
        }
        if (imgManga != null) {
            imgManga.setImageBitmap(ImageUtils.decodeSampledBitmapFromResource(getResources(), R.drawable.manga, 600, 400));
        }
    }
}
