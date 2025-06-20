package com.example.PassBox.AddFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.PassBox.Database.PassBoxDb;
import com.example.PassBox.PassList;
import com.example.PassBox.R;

import java.util.List;

public class AddFragment extends Fragment {
    private TextView site,username,password,note,saveBtn;
    private String id,checkId;
    private String receivedSite="",receivedUsername="",receivedPassword="",receivedNote = "";
    private PassBoxDb db;
    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("id");
            checkId = id;
//            Log.d("get","geting id is :"+id);
        }else {
            checkId = "Add";
            id = "";
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        site = view.findViewById(R.id.editSite);
        username = view.findViewById(R.id.editUsername);
        password = view.findViewById(R.id.editPassword);
        note = view.findViewById(R.id.editNote);
        saveBtn = view.findViewById(R.id.btnSave);

        try {
            db = new PassBoxDb(getContext());
            if (id.equals(checkId)) {
                List<PassList> listData = db.getSelectedPassword(id);
                PassList list = listData.get(0);
                if (list.getId().equals(id)) {
                    Log.d("Tag", "site is " + listData.get(0).getSite());
                    site.setText(listData.get(0).getSite());
                    receivedSite =listData.get(0).getSite();
                    username.setText(listData.get(0).getUsername());
                    receivedUsername = listData.get(0).getUsername();
                    password.setText(listData.get(0).getPassword());
                    receivedPassword =listData.get(0).getPassword();
                    note.setText(listData.get(0).getNote());
                    receivedNote = listData.get(0).getNote();
                }
            }
        }catch (Exception e){
//            Toast.makeText(getContext(), "Error : "+e, Toast.LENGTH_SHORT).show();
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!site.getText().toString().isEmpty() && !username.getText().toString().isEmpty()  && !password.getText().toString().isEmpty() ){
                        try {
                            if(id.equals(checkId)){ // update data
                                if(!site.getText().toString().equals(receivedSite) && !username.getText().toString().equals(receivedUsername)
                                        && !password.getText().toString().equals(receivedPassword) && !note.getText().toString().equals(receivedNote)){
                                    //updating
                                    boolean isUpdated = db.updatePasword(id,site.getText().toString(),username.getText().toString(),password.getText().toString(),note.getText().toString());
                                    if(isUpdated){
                                        Toast.makeText(getContext(), "successfully Updated ", Toast.LENGTH_SHORT).show();
                                        requireActivity().getSupportFragmentManager().popBackStack();
                                    }
                                    else Toast.makeText(getContext(), "Not updated ", Toast.LENGTH_SHORT).show();

                                }else if(site.getText().toString().equals(receivedSite) && username.getText().toString().equals(receivedUsername)
                                        && !password.getText().toString().equals(receivedPassword) && note.getText().toString().equals(receivedNote)) {
                                    //updating
                                    boolean isUpdated = db.updatePasword(id,password.getText().toString());
                                    if(isUpdated){
                                        Toast.makeText(getContext(), "Password is successfully updated ", Toast.LENGTH_SHORT).show();
                                        requireActivity().getSupportFragmentManager().popBackStack(); //back to show password fragement
                                    }
                                    else Toast.makeText(getContext(), "The password is not updated ", Toast.LENGTH_SHORT).show();


                                }else if(site.getText().toString().equals(receivedSite) && username.getText().toString().equals(receivedUsername)
                                        && password.getText().toString().equals(receivedPassword) && !note.getText().toString().equals(receivedNote)){
                                    //updating
                                    boolean isUpdated =db.updatePasword(Integer.parseInt(id),note.getText().toString());
                                    if(isUpdated){
                                        Toast.makeText(getContext(), "Note is successfully updated ", Toast.LENGTH_SHORT).show();
                                        requireActivity().getSupportFragmentManager().popBackStack();
                                    }
                                    else Toast.makeText(getContext(), "Note is not updated ", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getContext(), "There is no any update ", Toast.LENGTH_SHORT).show();
                                }


                            }
                            else if(checkId.equals("Add")){  //Add new  account password
                                long result = db.addPassword(site.getText().toString(),username.getText().toString(),password.getText().toString(),note.getText().toString());
                                if(result!=-1){
                                    Toast.makeText(getContext(), "Password is successfully saved", Toast.LENGTH_SHORT).show();
                                    site.setText("");
                                    username.setText("");
                                    password.setText("");
                                    note.setText("");
                                }
                            }
                            else{
                                Toast.makeText(getContext(), "Neither update nor add ", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(getContext(), "Error : "+e, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Please fill all information", Toast.LENGTH_SHORT).show();
                    }

            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}