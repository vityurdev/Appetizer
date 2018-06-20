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
import com.example.vitalyyurenya.appetizer.api.responses.AuthResponse;
import com.example.vitalyyurenya.appetizer.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private EditText usernameET;
    private EditText emailET;
    private EditText passwordET;
    private EditText passwordET2;

    private Button registerButton;
    private Button redirLoginButton;

    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameET = findViewById(R.id.reg_username_et);
        emailET = findViewById(R.id.reg_email_et);
        passwordET = findViewById(R.id.reg_psw_et);
        passwordET2 = findViewById(R.id.reg_psw_et2);

        registerButton = findViewById(R.id.register_btn);
        redirLoginButton = findViewById(R.id.redir_login_btn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!passwordET.getText().toString().equals(passwordET2.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Password mismatch", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String username = usernameET.getText().toString();
                final String email = emailET.getText().toString();
                final String password = passwordET.getText().toString();

                final User user = new User(null,
                        username,
                        email,
                        password,
                        null,
                        null,
                        null,
                        null
                );

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AuthApi.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AuthApi authApi = retrofit.create(AuthApi.class);

                Call<AuthResponse> authCall = authApi.register(user);

                authCall.enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        if (response.code() == 500) {
                            Log.i("lol", "Almost success");
                            Toast.makeText(getApplicationContext(), "Server error while registering user.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (response.code() == 200) {
                            Log.i("lol", "success");

                            String authToken = response.body().getToken();
                            String userId = response.body().getUserId();

                            AccountManager accountManager = AccountManager.get(getApplicationContext());

                            Account account = new Account(username, "com.vityur.appetizer.account");

                            Bundle userdata = new Bundle();
                            userdata.putString("USER_ID", userId);
                            accountManager.addAccountExplicitly(account, password, userdata);

                            accountManager.setAuthToken(account, "com.vityur.appetizer.account.token", authToken);

                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Log.i("lol", "Failure");
                        Log.i("lol", t.toString());
                    }
                });
            }
        });

        redirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
