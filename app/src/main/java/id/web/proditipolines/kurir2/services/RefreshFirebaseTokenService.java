package id.web.proditipolines.kurir2.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

public class RefreshFirebaseTokenService extends IntentService {

    public static final String TAG = RefreshFirebaseTokenService.class.getSimpleName();

    public RefreshFirebaseTokenService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try
        {
            // Resets Instance ID and revokes all tokens.
            FirebaseInstanceId.getInstance().deleteInstanceId();

            // Now manually call onTokenRefresh()
            String refreshedToken = FirebaseInstanceId.getInstance().getToken("264165056348", "FCM");

            //Subscribe Topic
            SharedPreferences preferences = getSharedPreferences("SESSION",MODE_PRIVATE);
            FirebaseMessaging.getInstance().subscribeToTopic("kurir-" + preferences.getString("ID", "0"));

            //Save token and topic
            SharedPreferences.Editor editor = getSharedPreferences("SESSION", MODE_PRIVATE).edit();
            editor.putString("TOKEN", refreshedToken );
            editor.putString("TOPIC", "kurir-" + preferences.getString("ID", "0") );
            editor.apply();

            Log.d("Token", refreshedToken);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
