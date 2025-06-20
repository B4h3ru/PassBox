package com.example.PassBox.HomeFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.PassBox.Database.PassBoxDb;
import com.example.PassBox.PassList;
import com.example.PassBox.PassListAdapter;
import com.example.PassBox.R;
import com.example.PassBox.DetailFragment.ShowPasswordFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerview;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerview= view.findViewById(R.id.passwordRecyclerView);

        PassBoxDb db = new PassBoxDb(getContext()); //data fetching

        PassListAdapter passlistAdpter= new PassListAdapter(db.getPassword(), new PassListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PassList list) {
//                Toast.makeText(getContext(), "ClickedId id is : " + list.getId(), Toast.LENGTH_SHORT).show();
                try {
                    ShowPasswordFragment fragment = new ShowPasswordFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", list.getId()); // transferring Id from this fragment to ShowPasswordFragment

                    fragment.setArguments(bundle);
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error  : " + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(passlistAdpter);  //populated list


        return view;
    }


}