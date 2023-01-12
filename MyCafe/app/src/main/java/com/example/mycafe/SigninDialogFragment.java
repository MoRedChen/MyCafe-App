package com.example.mycafe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SigninDialogFragment extends DialogFragment {
    EditText usernameInput;
    EditText passwordInput;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View textenter = inflater.inflate(R.layout.dialog_signin, null);
        usernameInput = (EditText) textenter.findViewById(R.id.username);
        passwordInput = (EditText) textenter.findViewById(R.id.password);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(textenter)
                .setTitle("Login");
        builder.setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }
    @Override
    public void onResume() {
        super.onResume();
        AlertDialog alertDialog = (AlertDialog) getDialog();
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                if (username.equals("user") && password.equals("12345678")) {
                    Toast.makeText(getContext(), "Welcome "+username+" !", Toast.LENGTH_LONG).show();
                    ((MainActivity) getActivity()).logIn();
                    dismiss();
                }
                else if (!password.equals("12345678")) {
                    Toast.makeText(getContext(), "password error", Toast.LENGTH_SHORT).show();
                    passwordInput.setText("");
                }
                else {
                    Toast.makeText(getContext(), "username and password error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Button neutralButton = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        neutralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameInput.setText("");
                passwordInput.setText("");
                Toast.makeText(getContext(), "Clear", Toast.LENGTH_SHORT).show();
            }
        });
    }
}