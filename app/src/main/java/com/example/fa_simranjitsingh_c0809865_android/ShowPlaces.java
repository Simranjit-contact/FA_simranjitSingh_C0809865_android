package com.example.fa_simranjitsingh_c0809865_android;
//Created by Simranjit Singh C0809865 on 16-02-2022

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowPlaces extends AppCompatActivity {

    DbHelper dbHelper;
    List<AddPlaceModel> allPlaces;
    RecyclerView mRecyclerView;
    PlacesAdapter placesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_places);

        mRecyclerView=findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ShowPlaces.this));



        dbHelper=new DbHelper(ShowPlaces.this);

        getAllPalces();


    }

    private void getAllPalces() {
        allPlaces = dbHelper.getAll();
        if (allPlaces.size() == 0) {
            Toast.makeText(ShowPlaces.this, "No Data", Toast.LENGTH_SHORT).show();

        } else {
            placesAdapter = new PlacesAdapter(ShowPlaces.this, allPlaces);
            mRecyclerView.setAdapter(placesAdapter);
            placesAdapter.notifyDataSetChanged();
        }

    }


    public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.MyViewHolder> {

        Context context;
        List<AddPlaceModel> childFeedList;


        public PlacesAdapter(Context context, List<AddPlaceModel> childFeedList) {
            this.context = context;
            this.childFeedList = childFeedList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
             AddPlaceModel model = childFeedList.get(position);
            holder.name.setText(model.getName());
            if(model.getCompleted().equalsIgnoreCase("false")){
                holder.status.setText("Not Visited");

            }else{
                holder.status.setText("Visited!");
            }

            holder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(model.getCompleted().equalsIgnoreCase("false")){
                        dbHelper.update(model,model.getName(),model.getLat(),model.getLng(),"true");
                        getAllPalces();

                    }else{
                        Toast.makeText(ShowPlaces.this, "Already visited", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dbHelper.delete(model);
                    getAllPalces();

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ShowPlaces.this,EditActiivty.class);
                    intent.putExtra("ID",model.getId());
                    intent.putExtra("LAT",model.getLat());
                    intent.putExtra("LNG",model.getLng());
                    intent.putExtra("OBJ",model);
                    startActivity(intent);
                    finish();
                }
            });




        }




        @Override
        public int getItemCount() {
            return childFeedList.size();
        }



        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name,status;
            ImageView delete;

            public MyViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.mName);
                status = itemView.findViewById(R.id.status);
                delete = itemView.findViewById(R.id.delete);
            }
        }

        //Swipe to delete function
        /*ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(ShowPlaces.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(ShowPlaces.this, "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                //dbHelper.delete(position);
                placesAdapter.notifyDataSetChanged();

            }
        };*/

    }
}