package com.example.aleknik.execomhackathon2017.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleknik.execomhackathon2017.R;
import com.example.aleknik.execomhackathon2017.model.SaleItem;
import com.example.aleknik.execomhackathon2017.repository.SaleItemDAORepository;
import com.example.aleknik.execomhackathon2017.repository.UserDAORepository;
import com.example.aleknik.execomhackathon2017.utils.FileUtils;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@EActivity(R.layout.activity_new_sale_item)
public class NewSaleItemActivity extends AppCompatActivity {

    private static final String TAG = NewSaleItemActivity.class.getSimpleName();

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int PICK_IMAGE = 2;

    String currentPhotoPath = null;

    @Bean
    FileUtils fileUtils;

    @ViewById
    TextView name;

    @ViewById
    TextView description;

    @ViewById
    TextView price;

    @ViewById
    SimpleDraweeView image;

    @ViewById
    Button add;

    @Bean
    UserDAORepository userDAORepository;

    @Bean
    SaleItemDAORepository saleItemDAORepository;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Click
    void add() {

        if (name.getText().toString().equals("") ||
                description.getText().toString().equals("") ||
                price.getText().toString().equals("")) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_LONG).show();
            return;
        }

        SaleItem saleItem = new SaleItem(name.getText().toString(),
                description.getText().toString(), new Date(),
                Double.parseDouble(price.getText().toString()));
        saleItem.setUser(userDAORepository.getLoggedInUser());
        saleItem.setImagePath(currentPhotoPath);
        saleItemDAORepository.create(saleItem);
        setResult(RESULT_OK);
        finish();
    }

    @Click
    void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                final File photoFile = fileUtils.createImageFile();

                currentPhotoPath = photoFile.getAbsolutePath();
                Log.d(TAG, currentPhotoPath);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    @OnActivityResult(REQUEST_IMAGE_CAPTURE)
    void onOpenCamera(int resultCode) {
        if (resultCode == RESULT_OK) {
            Log.d(TAG, currentPhotoPath);
            image.setImageURI(new Uri.Builder().scheme(UriUtil.LOCAL_FILE_SCHEME).path(currentPhotoPath).build());
        }
    }

    @Click
    void pickImage() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @OnActivityResult(PICK_IMAGE)
    void onPickImage(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            currentPhotoPath = data.getData().getPath();
            image.setImageURI(new Uri.Builder().scheme(UriUtil.LOCAL_FILE_SCHEME).path(data.getData().getPath()).build());
        }
    }
}
