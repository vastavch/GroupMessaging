package mad.com.GroupMeessaging;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUp.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
/* handles the signup activity */
public class SignUp extends Fragment {
    EditText txtName,txtEmail,txtPassword,txtConfirm;
    boolean hasEmail;
    private OnFragmentInteractionListener mListener;

    public SignUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtName= (EditText)getActivity().findViewById(R.id.txtUserName);
        txtEmail=(EditText) getActivity().findViewById(R.id.txtEmail);
        txtPassword=(EditText) getActivity().findViewById(R.id.txtPassword);
        txtConfirm=(EditText) getActivity().findViewById(R.id.txtConfirm);
        /*
        ensures whether the entered details are valid and email address doesnt exist already.
        If yes,enters the details in Parse.com database and navigates the user to his inbox
                     else shows the corresponding error message
        */
        getActivity().findViewById(R.id.btnSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateUserInfo()) {
                    ParseUser user = new ParseUser();
                    user.setUsername(txtEmail.getText().toString());
                    user.setEmail(txtEmail.getText().toString());
                    user.setPassword(txtPassword.getText().toString());
                    user.put("userName", txtName.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                               mListener.goToMessageFragment();
                            } else {
                               mListener.showErrorMessage(e.getMessage());
                            }
                        }
                    });
                }
            }
        });
        getActivity().findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.popStack();
            }
        });
    }
   /* returns true if,the entered details are valid and email is not already taken ,
                    else,returns false
     */
    private boolean validateUserInfo() {
        String username,email,password,confirmpass;
        username=txtName.getText().toString();
        email=txtEmail.getText().toString();
        password=txtPassword.getText().toString();
        confirmpass=txtConfirm.getText().toString();
        if(!username.equals(null) && !username.equals("") && !email.equals(null) && !email.equals("") && !password.equals("") && !password.equals(null) && !confirmpass.equals(null) && !confirmpass.equals(""))
        {
            if(password.equals(confirmpass)) {
                hasEmail = false;
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("Email", email);
                query.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null) {
                            if (objects.size() > 1) {
                                hasEmail = true;
                            }
                        } else {
                            mListener.showErrorMessage("Error in signup!Please try again!");
                        }
                    }
                });
                if (hasEmail) {
                     mListener.showErrorMessage("Email id already exists");
                    return false;
                }

            }
            else {
                mListener.showErrorMessage("Passwords do not match");
                return false;
            }
        }
        else {
            mListener.showErrorMessage("Please enter all the fields");
            return false;
        }
        return true;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void goToMessageFragment();
        public void showErrorMessage(String msg);
        public void popStack();
    }

}
