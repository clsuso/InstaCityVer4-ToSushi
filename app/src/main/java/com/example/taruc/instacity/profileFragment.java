package com.example.taruc.instacity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class profileFragment extends Fragment {
    private RecyclerView userpost;
    private TextView userProfName;
    private ImageView userProf;
    private Button editProf;
    private FirebaseAuth mAuth;
    private DatabaseReference postsRef;
    private DatabaseReference usersRef;
    private DatabaseReference namesRef;
    private FirebaseRecyclerAdapter<ProfileClass,profileViewHolder> adapter;

    private OnFragmentInteractionListener mListener;
    String current_user_id;
    public profileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final ImageView userProf = (ImageView) view.findViewById((R.id.user_prof));
        userProfName = (TextView) view.findViewById(R.id.prof_username);
        String imgPath="";
        userpost = (RecyclerView)view.findViewById(R.id.recycleview_id);
        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();
        usersRef =FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child("profileImage");
        namesRef =FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).child("userName");

        //edit profile button
        editProf = (Button) view.findViewById(R.id.edit_profile);
        editProf.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getContext(),"watever",Toast.LENGTH_SHORT);
                Intent editIntent = new Intent(getActivity(),editProfileOptionActivity.class);
                startActivity(editIntent);
            }
        });

        //get user profile picture
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("grod",dataSnapshot.getValue()+"");
                Picasso.with(getContext()).load(dataSnapshot.getValue().toString()).into(userProf);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            // get username
        namesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("aaa",dataSnapshot.getValue()+"");
                userProfName.setText(dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

            postsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
            Query query = postsRef.orderByKey();


            userpost.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            userpost.setLayoutManager(linearLayoutManager);

            FirebaseRecyclerOptions<ProfileClass> options = new
                    FirebaseRecyclerOptions.Builder<ProfileClass>()
                    .setQuery(query, ProfileClass.class).build();

            adapter = new FirebaseRecyclerAdapter<ProfileClass, profileViewHolder>
                    (options) {
                @Override
                protected void onBindViewHolder(profileViewHolder holder, int position, ProfileClass model) {
                    final String PostKey = getRef(position).getKey();
                    holder.setPostImage(getContext(), model.getPostImage());
                    holder.setCaption( model.getCaption());
                    holder.setDate(model.getDate());
                    holder.setTime(model.getTime());
                    holder.postComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent commentIntent=new Intent(getActivity(),feedCommentActivity.class);
                            commentIntent.putExtra("PostKey",PostKey);
                            startActivity(commentIntent);
                        }
                    });
                }

                @Override
                public profileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    Log.d("jack", "test");
                    View view = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.profile_cardview_layout, viewGroup, false);
                    return new profileViewHolder(view);
                }


            };

            userpost.setAdapter(adapter);
            Log.d("jack", adapter.getItemCount() + "");
            return view;

    }
    //move to edit profile page
//    private void SendUserToEditProfile(){
//        Intent editIntent = new Intent(getActivity(),EditProfileActivity.class);
//        startActivity(editIntent);
//    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    public static class profileViewHolder extends RecyclerView.ViewHolder{
        ImageView profile;
        View mView;
        ImageButton postComment;

        public profileViewHolder(View itemView){
            super(itemView);
            mView=itemView;
            postComment = (ImageButton)itemView.findViewById(R.id.post_comment_button);
        }

        public void setPostImage(Context ctx1,String postImage){
            profile = (ImageView) itemView.findViewById(R.id.profile_post);
            Picasso.with(ctx1).load(postImage).into(profile);
        }

        public void setTime(String time){
            TextView t = (TextView)mView.findViewById(R.id.postTime);
            t.setText(" "+time);
        }
        public void setDate(String date){
            TextView d = (TextView)mView.findViewById(R.id.postDate);
            d.setText("  "+date);
        }
        public void setCaption(String caption){
            TextView cap = (TextView)mView.findViewById(R.id.postDescription);
            cap.setText(caption);
        }

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
