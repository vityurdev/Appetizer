package com.example.vitalyyurenya.appetizer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitalyyurenya.appetizer.api.RecipeApi;
import com.example.vitalyyurenya.appetizer.models.Recipe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddRecipeActivity extends AppCompatActivity {

    String filePath;
    String authToken;

    private EditText recipeTitleEditText;
    private EditText recipeURLEditText;
    private EditText recipeIngredientsEditText;
    private EditText recipeDirectionsEditText;

    private ImageView recipeImageView;

    private Button recipeAddImageButton;
    private Button recipeAddRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        authToken = getIntent().getStringExtra("AUTH_TOKEN");

        recipeTitleEditText = findViewById(R.id.add_recipe_title_edit_text);
        recipeURLEditText = findViewById(R.id.add_recipe_url);
        recipeIngredientsEditText = findViewById(R.id.add_recipe_ingredients_edit_text);
        recipeDirectionsEditText = findViewById(R.id.add_recipe_directions_edit_text);

        recipeImageView = findViewById(R.id.add_recipe_image_view);

        recipeAddImageButton = findViewById(R.id.add_recipe_add_image_button);
        recipeAddRecipeButton = findViewById(R.id.add_recipe_add_recipe_button);

        recipeAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        recipeAddRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(RecipeApi.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RecipeApi recipeApi = retrofit.create(RecipeApi.class);

                File file = new File(filePath);
                // RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                // MultipartBody.Part body = MultipartBody.Part.createFormData("recipePhoto", file.getName(), reqFile);
                // RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

                HashMap<String, String> headers = new HashMap<>();
                // headers.put("Content-Type", "multipart/form-data");
                headers.put("x-access-token", authToken);



                Recipe recipe = new Recipe(null, "lol", "lol", new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), "null", "lol", null, null, "s");
                Call<ResponseBody> req = recipeApi.uploadRecipe(
                        /*RequestBody.create(MediaType.parse("text/plain"), "title"),*/
                        MultipartBody.Part.createFormData(
                                "recipePhoto",
                                file.getName(),
                                RequestBody.create(MediaType.parse("image"), file)),
                        recipe,
                        headers
                );

                req.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.i("lol", "Posting recipe: success!");
                        Log.i("lol", Integer.toString(response.code()));
                        Log.i("lol", response.message());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (t instanceof IOException) {
                            Log.i("lol", "IOException");
                        } else {
                            Log.i("lol", t.getClass().toString());
                        }
                    }
                });

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();

                filePath = getPath(selectedImage);
                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                recipeAddImageButton.setText(filePath);

                try {
                    if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                        //FINE
                    } else {
                        //NOT IN REQUIRED FORMAT
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);

        return cursor.getString(column_index);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                //set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
                //set title
                .setTitle("Are you sure to exit?")
                //set message
                .setMessage("There are unsaved info in your activity!")
                //set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        finish();
                    }
                })
                //set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked

                    }
                })
                .show();



    }
}
