package com.example.mycafe.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mycafe.R;
import com.example.mycafe.databinding.FragmentMenuBinding;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;

    Spinner dropdown;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        dropdown = root.findViewById(R.id.menu_spinner);
        initspinnerfooter(root);

        return root;
    }

    private void initspinnerfooter(View root) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.menu_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ImageView img = (ImageView) root.findViewById(R.id.menu_imageView);
                TextView text = (TextView) root.findViewById(R.id.menu_text);
                switch(pos) {
                    case 0:
                        img.setImageResource(R.drawable.caffe_americano);
                        text.setText("Espresso shots topped with hot water create a light layer of crema culminating in this wonderfully rich cup with depth and nuance.");
                        break;
                    case 1:
                        img.setImageResource(R.drawable.cappuccino);
                        text.setText("Dark, rich espresso lies in wait under a smoothed and stretched layer of thick milk foam. An alchemy of barista artistry and craft.");
                        break;
                    case 2:
                        img.setImageResource(R.drawable.caramel_macchiato);
                        text.setText("Freshly steamed milk with vanilla-flavored syrup marked with espresso and topped with a caramel drizzle for an oh-so-sweet finish.");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}