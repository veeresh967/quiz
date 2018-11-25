package com.project.quiz.quizapp26082017;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MyAlertDialog extends DialogFragment {

    //----this will display the hint as alert type-------
    String hint;
    @SuppressLint("ValidFragment")
    public MyAlertDialog(String hint) {
        // Required empty public constructor
        this.hint=hint;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = null;
        //WE WILL WRITE ACTUAL CODE FOR DISPLAYING ALERT DIALOG BOX
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        ab.setIcon(R.mipmap.ic_launcher);
        ab.setTitle("Hint");
        ab.setMessage(""+hint);
        ab.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
      //          Toast.makeText(getActivity(), "OK OK", Toast.LENGTH_SHORT).show();
            }
        });

        d = ab.create(); //MANDATORY STEP
        //END
        return d;
    }
}
