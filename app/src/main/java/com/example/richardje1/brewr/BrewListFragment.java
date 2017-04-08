package com.example.richardje1.brewr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by richardje1 on 3/6/17.
 */

public class BrewListFragment extends Fragment {

    private RecyclerView mBrewRecyclerView;
    private BrewAdapter mAdapter;
    private ArrayList<Brew> mBrews;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.brew_title);
        mBrews = BrewLab.get(getActivity()).getBrews();
        //setHasOptionsMenu(true);

        //ArrayAdapter<Brew> adapter = new ArrayAdapter<Brew>(getActivity(),
        //                                    android.R.layout.simple_list_item_1,
        //                                    mBrews);
        //setListAdapter(adapter);
    }
    private class BrewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;

        private Brew mBrew;

        public BrewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_brew, parent, false));

            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.brew_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.brew_date);
        }

        public void bind(Brew brew){
            mBrew = brew;
            mTitleTextView.setText(mBrew.getTitle());
            mDateTextView.setText(mBrew.getDate().toString());
        }

        @Override
        public void onClick(View view){
            Intent intent = BrewActivity.newIntent(getActivity(), mBrew.getId());
            startActivity(intent);

            //Toast.makeText(getActivity(),
              //      mBrew.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                //    .show();
        }
    }

    private class BrewAdapter extends RecyclerView.Adapter<BrewHolder> {
        private List<Brew> mBrews;

        public BrewAdapter(List<Brew> brews){
            mBrews = brews;
        }

        @Override
        public BrewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new BrewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(BrewHolder holder, int position) {
            Brew brew = mBrews.get(position);
            holder.bind(brew);
        }

        @Override
        public int getItemCount() {
            return mBrews.size();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brew_list, container, false);

        mBrewRecyclerView = (RecyclerView) view
                .findViewById(R.id.brew_recycler_view);
        mBrewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        BrewLab brewLab = BrewLab.get(getActivity());
        List<Brew> brews = brewLab.getBrews();

        mAdapter = new BrewAdapter(brews);
        mBrewRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_brew:
                Brew brew = new Brew();
                BrewLab.get(getActivity()).addBrew(brew);
                Intent intent = BrewPagerActivity
                        .newIntent(getActivity(), brew.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected();
        }
    }



}
