package jajabor.in.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

        DatabaseReference mDatabaseReference;
        MaterialSearchView searchView;
        ListView lstView;
        List<String>pname;
    ArrayAdapter adapter;
    String[] suggestion;
    String[] lstSource = {

                "Harry",
                "Ron",
                "Hermione",
                "Snape",
                "Malfoy",
                "One",
                "Two",
                "Three",
                "Four",
                "Five",
                "Six",
                "Seven",
                "Eight",
                "Nine",
                "Ten"
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            lstView =findViewById(R.id.lstView2);
            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            pname = new ArrayList<>();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference();
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            if(dataSnapshot1.getKey().equals("Name")){
                                pname.add(dataSnapshot1.getValue().toString());
                                Log.d("DARA1",dataSnapshot1.getValue().toString());
                            }
                        }
                    }
                    suggestion = new String[pname.size()];
                    for(int i=0;i<pname.size();i++){
                        suggestion[i] = pname.get(i);
                        Log.d("DARA",suggestion[i]);
                    }
                    adapter = new ArrayAdapter(SearchActivity.this,android.R.layout.simple_list_item_1,suggestion);
                    lstView.setAdapter(adapter);
                    searchView.setSuggestions(suggestion);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            searchView = (MaterialSearchView)findViewById(R.id.search_view);

            searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("ITEM",suggestion[position]);
                }
            });
            searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
                @Override
                public void onSearchViewShown() {
                    searchView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSearchViewClosed() {

                    //If closed Search View , lstView will return default
                    lstView = (ListView)findViewById(R.id.lstView2);
                     adapter = new ArrayAdapter(SearchActivity.this,android.R.layout.simple_list_item_1,suggestion);
                    lstView.setAdapter(adapter);

                }
            });

            searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(newText != null && !newText.isEmpty()){
                        List<String> lstFound = new ArrayList<String>();
                        for(String item:suggestion){
                            if(item.contains(newText))
                                lstFound.add(item);
                        }

                         adapter = new ArrayAdapter(SearchActivity.this,android.R.layout.simple_list_item_1,lstFound);
                        lstView.setAdapter(adapter);
                    }
                    else{
                        //if search text is null
                        //return default
                         adapter = new ArrayAdapter(SearchActivity.this,android.R.layout.simple_list_item_1,suggestion);
                        lstView.setAdapter(adapter);
                    }
                    return true;
                }


            });



        }
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_item,menu);
            MenuItem item = menu.findItem(R.id.action_search);
            searchView.setMenuItem(item);
            return true;
        }

}