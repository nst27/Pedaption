package com.example.pedaption;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

public class Post extends AppCompatActivity {
    Button btn,uploadBtn,feeds;
    ImageView imgView;
    String phone,fullName;
    View bottomNavigationView;
    Context context;
    TextInputLayout petName,age,gender,description,address,type;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference;
    StorageReference reference;
    SharedPreferences sharedPreferences;
    private static final int PICK_IMAGE_REQUEST =1;
    Uri uriImage;
    ActivityResultLauncher<String> mGetContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
         phone = intent.getStringExtra("emergencyphone");
         fullName = intent.getStringExtra("fullName");
         this.context = context;
        setContentView(R.layout.activity_post);
        btn = findViewById(R.id.fetchBtn);
        imgView = findViewById(R.id.pickedImage);
        petName = findViewById(R.id.petName);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        description = findViewById(R.id.description);
        feeds = findViewById(R.id.feeds);
        type = findViewById(R.id.type);
        address = findViewById(R.id.address);
        uploadBtn = findViewById(R.id.upload);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        sharedPreferences = getSharedPreferences("login",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.contains("name")){
            Log.d("MILA KYA NAAM",""+sharedPreferences.getString("name",""));
        }else{
            Log.d("","NHI MILA");
        }

        feeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Post.this,ShowActivity.class);
                startActivity(intent);
            }
        });



    uploadBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        rootNode = FirebaseDatabase.getInstance();
        databaseReference = rootNode.getReference("Post");
        reference = FirebaseStorage.getInstance().getReference();
        if(uriImage != null){
            uploadToFirebase(uriImage);
        }else{
            Toast.makeText(getApplicationContext(),"Please Select Image",Toast.LENGTH_SHORT).show();
        }
    }
});

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE_REQUEST);
            }
        });



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriImage = data.getData();
            imgView.setImageURI(uriImage);
        }
    }

    public void uploadToFirebase(Uri uri){
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtenstion(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String name = petName.getEditText().getText().toString();
                        String petAge = age.getEditText().getText().toString();
                        String petGender = gender.getEditText().getText().toString();
                        String petAddress = address.getEditText().getText().toString();
                        String petDescription = description.getEditText().getText().toString();
                        String petType = type.getEditText().getText().toString();
                        Model model = new Model(name,petAge,petAddress,petGender,petDescription,petType,fullName,phone,uri.toString());
                        String modelID = databaseReference.push().getKey();
                        databaseReference.child(modelID).setValue(model);
                        Toast.makeText(getApplicationContext(),"Upload Successful",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                Toast.makeText(getApplicationContext(),"Please wait uploading...",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Uploading Failed",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String getFileExtenstion(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(mUri));
    }

}