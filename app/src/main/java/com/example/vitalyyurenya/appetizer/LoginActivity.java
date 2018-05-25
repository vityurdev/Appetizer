package com.example.vitalyyurenya.appetizer;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vitalyyurenya.appetizer.api.AuthApi;
import com.example.vitalyyurenya.appetizer.api.AuthResponse;
import com.example.vitalyyurenya.appetizer.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText usernameET;
    private EditText passwordET;

    private Button loginButton;
    private Button redirRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.login_username_et);
        passwordET = findViewById(R.id.login_psw_et);

        loginButton = findViewById(R.id.login_btn);
        redirRegisterButton = findViewById(R.id.redir_register_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User user = new User(
                        usernameET.getText().toString(),
                        null,
                        passwordET.getText().toString()
                );

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AuthApi.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AuthApi authApi = retrofit.create(AuthApi.class);

                Call<AuthResponse> authCall = authApi.login(user);

                authCall.enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        if (response.code() == 500) {
                            Toast.makeText(LoginActivity.this, "Server error while logging in.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (response.code() == 404) {
                            Toast.makeText(LoginActivity.this, "No user found.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (response.code() == 401) {
                            Toast.makeText(LoginActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (response.code() == 200) {
                            String authToken = response.body().getToken();

                            AccountManager accountManager = AccountManager.get(getApplicationContext());

                            Account account = new Account(user.getUsername(), "com.vityur.appetizer.account");
                            accountManager.addAccountExplicitly(account, user.getUsername(), null);
                            accountManager.setAuthToken(account, "com.vityur.appetizer.account.token", authToken);

                            Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
            }
        });









        redirRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(redirIntent);
                finish();
            }
        });

    }
}
