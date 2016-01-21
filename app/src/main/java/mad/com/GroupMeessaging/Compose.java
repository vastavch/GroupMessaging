package mad.com.GroupMeessaging;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Compose.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Compose extends Fragment {
    EditText txtMessage;

    private OnFragmentInteractionListener mListener;

    public Compose() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
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
        txtMessage=(EditText) getActivity().findViewById(R.id.txtMessage);
/* when the user clicks send button  this function is called.
   It ensures that message cntent is non empty and saves in the "Messages" table of database,stored in parse.com and displays "Message sent successfully"
   If the message content is empty,then it displays an error message "Please enter a message"
 */
        getActivity().findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msgText = txtMessage.getText().toString();
                if (msgText.equals(null) || msgText.equals("")) {
                    mListener.showErrorMessage("Please enter a message");

                } else {
                    ParseObject msg = new ParseObject("Messages");
                    msg.put("msg", msgText);
                    msg.put("createdBy", ParseUser.getCurrentUser());
                    msg.saveInBackground();
                    mListener.showErrorMessage("Message sent successfully");
                    mListener.popStack();
                }
            }
        });
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
        public void showErrorMessage(String msg);
        public void popStack();
    }

}
