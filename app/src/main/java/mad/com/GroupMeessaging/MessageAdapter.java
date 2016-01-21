package mad.com.GroupMeessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;
/* This class sets the content for MessageAdapter

 */
public class MessageAdapter extends ArrayAdapter<ParseObject> {
    List<ParseObject> mbjects;
    Context mcontext;
    int res;
    public ReLoad act;
    public MessageAdapter(ReLoad re,Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects);
        mbjects=objects;
        mcontext=context;
        res=resource;
        act=re;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflate= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflate.inflate(res,parent,false);
        }
        final ParseObject c=mbjects.get(position);
        TextView txtUserName=(TextView) convertView.findViewById(R.id.txtUserName);
        ParseUser user= (ParseUser) c.get("createdBy");
        txtUserName.setText(user.getString("userName"));
        TextView txtMessage=(TextView) convertView.findViewById(R.id.txtMessage);
        txtMessage.setText(c.getString("msg"));
        TextView txtDateTime=(TextView) convertView.findViewById(R.id.txtDateTime);
        txtDateTime.setText(c.getCreatedAt().toString());
        ImageView img=(ImageView) convertView.findViewById(R.id.imgDelete);
        final View finalConvertView = convertView;
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.deleteInBackground();
                act.refresh();
            }
        });
        if(user==ParseUser.getCurrentUser()){
            img.setVisibility(View.VISIBLE);
        }
        else
            img.setVisibility(View.GONE);

        return convertView;
    }
    public interface ReLoad
    {
        public void refresh();
    }
}
