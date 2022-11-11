package com.example.belajarintentv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class intentImplicit extends AppCompatActivity {
    Intent intent = null;
    //Memanggil edit text & imageview (variable)
    EditText input_location, input_web, input_dial, input_share_text;
    ImageView image_selected_view, image_camera_view;

    //Membuat variable
    private static final String TAG = intentImplicit.class.getCanonicalName();
    private static final  int GALLERY_REQUEST_CODE = 1;
    private static final  int CAMERA_REQUES = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_implicit);
        getSupportActionBar().setTitle("Intent Implicit");

        //memasukkan nama yang sudah di buat di atas dengan nama id yang sudah di buat di tampilan
        input_web = (EditText) findViewById(R.id.input_web);
        input_share_text = (EditText) findViewById(R.id.input_share_text);
        input_location = (EditText) findViewById(R.id.input_location);
        input_dial = (EditText) findViewById(R.id.input_dial);
        image_camera_view = (ImageView) findViewById(R.id.image_camera_view);
        image_selected_view = (ImageView) findViewById(R.id.image_selected_view);

    }

    // berfungsi sebagai jika jika klik tombol btnweb maka kita akan di arahkan ke suatu url yang
    // sudah kita tulis
    public void handlerBtnWeb(View view) {
        String url= "http://" + input_web.getText().toString();
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    // berfungsi jika kita klik tombol lokasi maka kita akan di arahkan ke google maps
    public void handlerBtnLocation(View view) {
        String location= input_location.getText().toString();
        Uri addres = Uri.parse("geo:0,0?q=" + location);
        intent = new Intent(Intent.ACTION_VIEW, addres);
        startActivity(intent);
    }

    // berfungsi jika kita klik tombol share maka akan menampilkan berbagai pilihan share
    public void handlerBtnShareText(View view) {
        String input_text = input_share_text.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, input_text);
        startActivity(intent);
    }

    // membuat fungsi jika klik tombol dial maka akan langsung menulis nomernya dan di pindahkan ke
    // telepon
    public void handlerBtnDial(View view) {
        String no_hp = input_dial.getText().toString();
        intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + no_hp));
        startActivity(intent);
    }

    // menampilkan kontak yang ada
    public void handlerBtnContact(View view) {
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contact/people"));
        startActivity(intent);
    }

    // jika tombol di klik maka akan di arahkan ke kamera
    public void handlerBtnCameraView(View view) {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUES);
    }

    // jika tombol di klik maka akan di arahkan ke galeri
    public void handlerBtnSelectedImaage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_CANCELED){
            return;
        }

        // agar bisa menampilkan galeri
        if (requestCode == CAMERA_REQUES && resultCode == intentImplicit.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image_camera_view.setImageBitmap(photo);
        }

        // mengatasi jika code gagal
        if (requestCode == GALLERY_REQUEST_CODE){
            if(data != null){
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    image_selected_view.setImageBitmap(bitmap);
                } catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}

