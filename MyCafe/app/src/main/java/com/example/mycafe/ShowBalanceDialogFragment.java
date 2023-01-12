package com.example.mycafe;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ShowBalanceDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

//        String balance = String.valueOf( super.getContext().getSharedPreferences("USER", MODE_PRIVATE).getInt("balance", 0) );
        String balance = String.valueOf(((MainActivity) getActivity()).getBalance());
        String message = "account balance: "+balance;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // ok
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}