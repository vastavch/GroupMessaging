package mad.com.GroupMeessaging;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity implements Login.OnFragmentInteractionListener,
        SignUp.OnFragmentInteractionListener,
        Message.OnFragmentInteractionListener,
        Compose.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);showLogin();
    }
    //Loads Message fragment
    @Override
    public void goToMessageFragment() {
        getActionBar().setTitle("Messages");
        getFragmentManager().beginTransaction().replace(R.id.wrapperLayout, new Message(), "Message").addToBackStack(null).commit();
    }
    /* Used by the corresponding fragments to toast a message*/
    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }
    }
    @Override
    public void popStack() {
        if(getFragmentManager().getBackStackEntryCount()>0){
          getFragmentManager().popBackStack();
        }
    }
    //Loads SignUp fragment
    @Override
    public void goToSignUpFragment() {

        getActionBar().setTitle("GroupMessaging (Sign UP)");
        getFragmentManager().beginTransaction().replace(R.id.wrapperLayout,new SignUp(),"Sign Up").addToBackStack(null).commit();
    }

    //Loads Compose Fragment
    @Override
    public void showCompose() {
        getActionBar().setTitle("Compose Message");
        getFragmentManager().beginTransaction().replace(R.id.wrapperLayout, new Compose(), "Compose").addToBackStack(null).commit();
    }

    // Loads SignUp fragment
    @Override
    public void showLogin() {
        getActionBar().setTitle("GroupMessaging (Login)");
        getFragmentManager().beginTransaction().replace(R.id.wrapperLayout,new Login(),"Login").addToBackStack(null).commit();
    }

}
