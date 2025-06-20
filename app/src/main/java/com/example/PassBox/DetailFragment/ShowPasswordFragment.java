package com.example.PassBox.DetailFragment;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.PassBox.AddFragment.AddFragment;
import com.example.PassBox.Database.PassBoxDb;
import com.example.PassBox.PassList;
import com.example.PassBox.R;

import java.util.List;


public class ShowPasswordFragment extends Fragment {
    private List<PassList> listData;
    private String id;
    private TextView site,domain,username,password,note;
    private Button edit,delete;
    private PassBoxDb db;

    public ShowPasswordFragment() {
        // Required empty public constructor
    }

    public static ShowPasswordFragment newInstance(String param1, String param2) {
        ShowPasswordFragment fragment = new ShowPasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
             id = getArguments().getString("id");
//            Log.d("get","geting id is :"+id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_show_password, container, false);

        site = view.findViewById(R.id.website_url);
        domain =  view.findViewById(R.id.website_sub_url);
        username  = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        note = view.findViewById(R.id.add_note);

        //button
        edit = view.findViewById(R.id.edit_button);
        delete = view.findViewById(R.id.delete_button);

        try {
            db = new PassBoxDb(getContext());
            listData = db.getSelectedPassword(id);
            AsyncDb res = new AsyncDb();
            res.execute(id);

//                Toast.makeText(getContext(), "it works fine : ", Toast.LENGTH_SHORT).show();
                PassList list = listData.get(0);
                if(list.getId().equals(id)){
                    site.setText(listData.get(0).getSite());
                    String url="www.https//"+listData.get(0).getSite();
                    if(url.contains(".com")) url = url;
                    else url = url+".com";
                    domain.setText(url);
                    username.setText(listData.get(0).getUsername());
                    password.setText(listData.get(0).getPassword());
                    note.setText(listData.get(0).getNote());
//                    Toast.makeText(getContext(), "it works fine ", Toast.LENGTH_SHORT).show();
                }

        }catch (Exception e){
            Toast.makeText(getContext(), "Error : "+e, Toast.LENGTH_SHORT).show();
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(db.deletePassword(id)){
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete")
                        .setMessage("Are you sure do you want to delete this password?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (db.deletePassword(id)) {
                                    Toast.makeText(getContext(), "The password is successfully deleted ", Toast.LENGTH_SHORT).show();
                                    requireActivity().getSupportFragmentManager().popBackStack();

                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }

        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFragment fragment = new AddFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", id); // example
                fragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        return view;
    }

    private class AsyncDb extends AsyncTask<String, String, List<PassList> >{
        @Override
        protected List<PassList> doInBackground(String... strings) {

            PassBoxDb db = new PassBoxDb(getContext());
            listData = db.getSelectedPassword(id);
//            Log.d("AsyncTask","asynctaski is working");
            return listData;
        }
    }
}