package com.example.mycafe.ui.qa;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycafe.AboutActivity;
import com.example.mycafe.ChatMessage;
import com.example.mycafe.Post;
import com.example.mycafe.R;
import com.example.mycafe.databinding.FragmentQaBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QaFragment extends Fragment {

    private FragmentQaBinding binding;

    private DatabaseReference mDatabase;


    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QaViewModel qaViewModel =
                new ViewModelProvider(this).get(QaViewModel.class);

        binding = FragmentQaBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_qa, container, false);

        btn1 = (Button) root.findViewById(R.id.qa_button1);
        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickQa(root, 1);
            }
        });
        btn2 = (Button) root.findViewById(R.id.qa_button2);
        btn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickQa(root, 2);
            }
        });
        btn3 = (Button) root.findViewById(R.id.qa_button3);
        btn3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickQa(root, 3);
            }
        });
        btn4 = (Button) root.findViewById(R.id.qa_button4);
        btn4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickQa(root, 4);
            }
        });
        btn5 = (Button) root.findViewById(R.id.qa_button5);
        btn5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickQa(root, 5);
            }
        });




        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });





        mDatabase = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Post post = dataSnapshot.getValue(Post.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
//        mPostReference.addValueEventListener(postListener);


        mDatabase.child("message").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });






        return root;
    }

    public void addMessage(String messageText, String messageUser) {
        ChatMessage message = new ChatMessage(messageText, messageUser);
        mDatabase.child("message").setValue(message)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                    }
                });;
    }

    public void onClickQa(View view, int num) {
//        addMessage("test", "user");
        TextView text = (TextView) view.findViewById(R.id.qa_text);
        switch (num) {
            case 1:
                text.setText("Answer 1");
                break;
            case 2:
                text.setText("Answer 2");
                break;
            case 3:
                text.setText("Answer 3");
                break;
            case 4:
                text.setText("Answer 4");
                break;
            case 5:
                text.setText("Answer 5");
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}