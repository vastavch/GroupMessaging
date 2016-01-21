package mad.com.GroupMeessaging;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Message.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Message extends Fragment implements MessageAdapter.ReLoad {
    ListView lv;
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public Message() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Refresh) {
            loadData();
        }
        else if(id==R.id.Compose){
           mListener.showCompose();

        }
        else if(id==R.id.Logout){
            ParseUser.logOut();
            mListener.showLogin();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
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
        loadData();
    }
/*    Fetches all the data  from "Messages" table from parse.com
      Sets the MessageAdapter to ListView 'lv'
 */
    public  void loadData() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Messages");
        query.include("createdBy");
        lv=(ListView) getActivity().findViewById(R.id.listView);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                MessageAdapter adapter = new MessageAdapter(Message.this,getActivity(), R.layout.fragment_list_adapter, objects);
                lv.setAdapter(adapter);
            }
        });
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /*caleed when "refresh" from menu item is clicked.
     Refreshes the inbox
     */
    @Override
    public void refresh() {
        loadData();
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
        public void showCompose();
        public void showLogin();
    }

}
