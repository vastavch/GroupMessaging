package mad.com.GroupMeessaging;

import android.app.Application;

import com.parse.Parse;

/* This class is used to link the application to  database in Parse.com

  */
public class Launcher extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, "APPLICATION ID", "CLIENT KEY");
    }
}

