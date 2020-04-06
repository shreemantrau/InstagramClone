package com.example.instagramclone;
import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("QBFbAUoIWsD5fKXmCFcXp8VVHQUE4TYSp0NZ66sm")
                // if defined
                .clientKey("X6ThlUaqJQSifSADm5t7JgeUTWCqEhqF86SFm68D")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
