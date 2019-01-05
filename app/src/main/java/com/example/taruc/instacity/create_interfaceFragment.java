package com.example.taruc.instacity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link create_interfaceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link create_interfaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class create_interfaceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    ViewPager viewPager;
    createPagerAdapter adapter;
    private TabLayout tabLayout;
    private OnFragmentInteractionListener mListener;

    public create_interfaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment create_interfaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static create_interfaceFragment newInstance() {
        create_interfaceFragment fragment = new create_interfaceFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    private void swtichChildFragment(int position){
        createFragment createPostFragment = new createFragment();
        create_eventFragment createEventFragment = new create_eventFragment();
        if(position==0){
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.createFragment,createPostFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.createFragment,createEventFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("testing","create Interface created");
        View view = inflater.inflate(R.layout.fragment_create_interface, container, false);
        FrameLayout frag = view.findViewById(R.id.createFragment);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.createFragment,new createFragment());
        transaction.addToBackStack(null);
        transaction.commit();
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.feed_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.event_tab));

            // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        // Use PagerAdapter to manage page views in fragments.
        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
      /*   viewPager = view.findViewById(R.id.createPager);

      adapter = new createPagerAdapter
                ((FragmentManager) getActivity().getSupportFragmentManager(),tabLayout.getTabCount());


            // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));*/

        tabLayout.addOnTabSelectedListener(new
                                                   TabLayout.OnTabSelectedListener() {
                                                       @Override
                                                       public void onTabSelected(TabLayout.Tab tab) {
                                                           swtichChildFragment(tab.getPosition());
                                                           Toast.makeText(getContext(), "Hello1"+tab.getPosition(), Toast.LENGTH_SHORT).show();
                                                       }

                                                       @Override
                                                       public void onTabUnselected(TabLayout.Tab tab) {
                                                       }

                                                       @Override
                                                       public void onTabReselected(TabLayout.Tab tab) {

                                                       }
                                                   });

        // Inflate the layout for this fragment
//        Toast.makeText(getContext(), "Hello1"+adapter.getItem(0), Toast.LENGTH_SHORT).show();
        //viewPager.setAdapter(adapter);*/
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


   /* public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/




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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
