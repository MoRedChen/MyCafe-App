package com.example.mycafe;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class TopUpDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View textenter = inflater.inflate(R.layout.dialog_topup, null);
        EditText userinput = (EditText) textenter.findViewById(R.id.topUpValue);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(textenter)
                .setTitle("Top Up");
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String inputvalue = userinput.getText().toString();
                int input = Integer.parseInt(inputvalue);
                ((MainActivity) getActivity()).addBalance(input);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                TopUpDialogFragment.this.getDialog().cancel();
            }
        });// Add action buttons
        return builder.create();
    }
}