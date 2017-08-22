package com.pyapps.practice.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pyapps.practice.Models.Post;
import com.pyapps.practice.R;
import com.pyapps.practice.RecyclerViewAdpater;
import com.pyapps.practice.Services.TempService;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public PostsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostsFragment newInstance(String param1, String param2) {
        PostsFragment fragment = new PostsFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));
        posts.add(new Post("",""));

        //TempService.increaseSize(posts);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        RecyclerViewAdpater adapter = new RecyclerViewAdpater(posts,getActivity());
        mParam1 = getArguments().getString(ARG_PARAM1);
        RecyclerView.LayoutManager  mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        switch (mParam1){
            case "0":{
                mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                break;
            }
            case "1":{
                mLayoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
                break;
            }
            case "2":{
                mLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                break;
            }
        }
        mLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
