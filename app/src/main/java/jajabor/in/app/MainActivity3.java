package jajabor.in.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {
    int id;
    List<String>sizes;
    List<String>colour;
EditText name,shortdesc,desc,image,categ,tags,price;
Button catup,tagup,save;
    List<String>catg;
    List<String>tag;
    DatabaseReference databaseReference;
    FirebaseFirestore db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        db = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        id=1;
        catg = new ArrayList<>();
        tag = new ArrayList<>();
        sizes = new ArrayList<>();
        sizes.add("S");
        sizes.add("M");
        sizes.add("L");
        sizes.add("XL");
        sizes.add("XXL");

        colour = new ArrayList<>();
        colour.add("White");
        colour.add("Yellow");
        colour.add("Maroon");
        colour.add("Grey");
        colour.add("Black");
        colour.add("Red");

        name = findViewById(R.id.nameinput);
        shortdesc = findViewById(R.id.shortinput);
        desc = findViewById(R.id.descinput);
        image = findViewById(R.id.imageinput);
        categ = findViewById(R.id.cateinput);
        tags = findViewById(R.id.taginput);
        price = findViewById(R.id.priceinput);

        catup = findViewById(R.id.catupdate);
        tagup = findViewById(R.id.tagupdate);
        save = findViewById(R.id.savebtn1);

        catup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catg.add(categ.getText().toString());
                categ.setText("");
            }
        });
        tagup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag.add(tags.getText().toString());
                tags.setText("");
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> note = new HashMap<>();
                note.put("name",name.getText().toString());
                note.put("shortdesc",shortdesc.getText());
                note.put("desc",desc.getText().toString());
                note.put("image",image.getText().toString());
                note.put("categ",catg);
                note.put("tag",tag);
                note.put("sizes",sizes);
                note.put("colour",colour);
                note.put("price",price.getText().toString());
                db.collection("Tshirts").document(id+"").set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity3.this,"success",Toast.LENGTH_SHORT).show();
                        id++;
                        name.setText("");
                        shortdesc.setText("");
                        desc.setText("");
                        image.setText("");
                        price.setText("");
                        catg.clear();
                        tag.clear();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity3.this,"fail",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}