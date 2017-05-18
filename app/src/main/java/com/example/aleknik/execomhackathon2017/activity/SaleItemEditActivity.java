package com.example.aleknik.execomhackathon2017.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

@OptionsMenu(R.menu.edit_menu)
@EActivity(R.layout.activity_sale_item_edit)
public class SaleItemEditActivity extends AppCompatActivity {

    private static final String TAG = NewSaleItemActivity.class.getSimpleName();

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PICK_IMAGE = 2;

    String currentPhotoPath = null;

    @Extra
    String item;

    @ViewById
    TextView name;

    @ViewById
    TextView description;

    @ViewById
    TextView price;

    @ViewById
    Button save;

    @ViewById
    SimpleDraweeView image;

    @Bean
    UserDAORepository userDAORepository;

    @Bean
    SaleItemDAORepository saleItemDAORepository;

    @Bean
    FileUtils fileUtils;

    private final Gson gson = new Gson();

    private SaleItem saleItem;

    @AfterViews
    void init() {
        saleItem = gson.fromJson(item, SaleItem.class);
        initData();
    }

    void initData() {
        name.setText(saleItem.getName());
        description.setText(saleItem.getDescription());
        price.setText(String.format(Locale.ENGLISH, "%.2f", saleItem.getPrice()));
        image.setImageURI(new Uri.Builder().scheme(UriUtil.LOCAL_FILE_SCHEME).path(saleItem.getImagePath()).build());

    }

    @OptionsItem
    void save() {
        if (name.getText().toString().equals("") ||
                description.getText().toString().equals("") ||
                price.getText().toString().equals("")) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_LONG).show();
            return;
        }

        saleItem.setName(name.getText().toString());
        saleItem.setDescription(description.getText().toString());
        saleItem.setPrice(Double.parseDouble(price.getText().toString()));
        saleItem.setImagePath(currentPhotoPath);

        if (!currentPhotoPath.equals(saleItem.getImagePath())) {
            fileUtils.deleteImage(saleItem.getImagePath());
        }
        saleItem.setImagePath(currentPhotoPath);
        saleItem = saleItemDAORepository.update(saleItem);
        userDAORepository.refresh(saleItem.getUser());

        Intent intent = new Intent();
        intent.putExtra("saleItem", gson.toJson(saleItem));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Click
    void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                // gets a file for the photo to be stored in
                final File photoFile = fileUtils.createImageFile();

                // we will need its path for later.
                currentPhotoPath = photoFile.getAbsolutePath();
                Log.d(TAG, currentPhotoPath);

                // tell the system what file to write to if the picture is taken.
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                // this starts up the camera.
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    @OnActivityResult(REQUEST_IMAGE_CAPTURE)
    void onOpenCamera(int resultCode, Intent data) {
        // do stuff if the photo has been taken.
        if (resultCode == RESULT_OK) {
            Log.d(TAG, currentPhotoPath);
            // the path to the image is in our variable, but we need the uri to load it.
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
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @OnActivityResult(PICK_IMAGE)
    void onPickImage(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            currentPhotoPath = data.getData().getPath();
            image.setImageURI(new Uri.Builder().scheme(UriUtil.LOCAL_FILE_SCHEME).path(data.getData().getPath()).build());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (currentPhotoPath != null && !currentPhotoPath.equals(saleItem.getImagePath())) {
            fileUtils.deleteImage(saleItem.getImagePath());
        }
        setResult(RESULT_CANCELED);
        finish();
    }
}
