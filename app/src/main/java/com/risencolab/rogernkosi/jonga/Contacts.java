package com.risencolab.rogernkosi.jonga;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Contacts extends Fragment implements APIController.HomeCallBackListener, SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private List<ContactsModel> list =  new ArrayList<>();
    private ContactsAdapter adapter;
    private APIController controller;
    private FloatingActionButton actionButton;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new APIController(this);
        controller.fetchContacts();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.con_rview);
        actionButton = (FloatingActionButton)view.findViewById(R.id.fab);


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new AddContact());
                fragmentTransaction.commit();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        adapter = new ContactsAdapter(list);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(ContactsModel model) {
        adapter.populate(model);
    }

    @Override
    public void onFetchProgress(List<ContactsModel> modelList) {

    }

    @Override
    public void onFetchComplete() {

    }

    @Override
    public void onFetchFailed() {

    }

    @Override
    public void onRefresh() {

    }

    public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.Holder>{
        public String TAG = ContactsAdapter.class.getSimpleName();
        private List<ContactsModel> contactsModels;

        public ContactsAdapter(List<ContactsModel> contactsModels) {
            this.contactsModels = contactsModels;
        }

        public void populate(ContactsModel model){
            contactsModels.add(model);
            notifyDataSetChanged();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_row,parent, false);
            return new Holder(row);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            ContactsModel model = contactsModels.get(position);
            holder.cname.setText(model.cname);
            holder.cnumber.setText(model.cnumber);
            holder.parentView.setSelected(contactsModels.contains(position));
        }

        @Override
        public int getItemCount() {
            return contactsModels.size();
        }

        public class Holder extends RecyclerView.ViewHolder{
            public TextView cname, cnumber;
            public View parentView;

            public Holder(View itemView) {
                super(itemView);
                this.parentView = itemView;
                cname = (TextView)itemView.findViewById(R.id.c_name);
                cnumber = (TextView)itemView.findViewById(R.id.c_number);
            }
        }
    }
}