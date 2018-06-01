package com.example.vitalyyurenya.appetizer;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FeedActivity extends AppCompatActivity {
    private TextView feedTextView;
    private Button logoutButton;

    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        feedTextView = findViewById(R.id.feed_tv);
        logoutButton = findViewById(R.id.feed_logout_btn);

        accountManager = AccountManager.get(getApplicationContext());

        final Account[] account = accountManager.getAccountsByType("com.vityur.appetizer.account");

        String username = account[0].name;
        String authToken = accountManager.peekAuthToken(account[0], "com.vityur.appetizer.account.token");

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAccount(account[0], FeedActivity.this, new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
                        try {
                            Bundle result = accountManagerFuture.getResult();
                            boolean success = result.getBoolean(AccountManager.KEY_BOOLEAN_RESULT);

                            if (success) {
                                Intent redirIntent = new Intent(FeedActivity.this, LoginActivity.class);
                                Log.i("lol", "success");
                                startActivity(redirIntent);
                                finish();
                            } else {
                                Log.i("lol", "Not an exception, but still a failure.");
                            }
                        } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                            Log.e("lol", e.getMessage());
                        }
                    }
                }, null);
            }
        });
    }

    public void removeAccount(
            @NonNull Account account,
            @NonNull Activity activity,
            @Nullable final AccountManagerCallback<Bundle> callback,
            @Nullable Handler handler
    ) {
        AccountManager accountManager = AccountManager.get(activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            accountManager.removeAccount(account, activity, callback, handler);
        } else {
            accountManager.removeAccount(
                    account,
                    new AccountManagerCallback<Boolean>() {
                        @Override
                        public void run(final AccountManagerFuture<Boolean> accountManagerFuture) {
                            if (callback == null)
                                return;

                            callback.run(new AccountManagerFuture<Bundle>() {
                                @Override
                                public boolean cancel(boolean mayInterruptIfRunning) {
                                    return accountManagerFuture.cancel(mayInterruptIfRunning);
                                }

                                @Override
                                public boolean isCancelled() {
                                    return accountManagerFuture.isCancelled();
                                }

                                @Override
                                public boolean isDone() {
                                    return accountManagerFuture.isDone();
                                }

                                @Override
                                public Bundle getResult() throws OperationCanceledException, IOException, AuthenticatorException {
                                    Bundle result = new Bundle();
                                    result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, accountManagerFuture.getResult());
                                    return result;
                                }

                                @Override
                                public Bundle getResult(long timeout, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
                                    Bundle result = new Bundle();
                                    result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, accountManagerFuture.getResult(timeout, timeUnit));
                                    return result;
                                }
                            });
                        }
                    },
                    handler
            );
        }
    }
}
