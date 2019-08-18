package com.example.chauffeurondemand;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.chauffeurondemand.com.dbtask.dconstant;
import com.example.chauffeurondemand.com.dbtask.dmanager;

public class AddDriverf extends Fragment {
    EditText txtdid,txtdname,txtmail,txtdphno,txtdage,txtdgender,txtaddress;
    Button btnsubmit,btnback;
    SQLiteDatabase sq;
    dmanager manager;
    RadioButton rb;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_driverf,null,false);
        manager = new dmanager(getActivity());
        sq = manager.OpenDb();

        txtdid = view.findViewById(R.id.txtdriverid);
        txtdname = view.findViewById(R.id.txtdrivername);
        txtmail = view.findViewById(R.id.txtdrivermail);
        txtdphno=view.findViewById(R.id.txtdriverphone);
        txtdage=view.findViewById(R.id.txtdriverage);
        txtaddress=view.findViewById(R.id.txtdriveraddress);

        btnsubmit = view.findViewById(R.id.btnsubmit);
        btnback = view.findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new DriverDetailsf()).commit();
            }
        });



        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {
                String id =txtdid.getText().toString().trim();
                String name = txtdname.getText().toString().trim();
                String email = txtmail.getText().toString().trim();
                String phone = txtdphno.getText().toString().trim();

                String age = txtdage.getText().toString().trim();
                String address = txtaddress.getText().toString().trim();
                if(TextUtils.isEmpty(id)||TextUtils.isEmpty(name)||TextUtils.isEmpty(email)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(age)||TextUtils.isEmpty(address))
                {
                    Toast.makeText(getActivity(), "All fields are mandatory!!", Toast.LENGTH_SHORT).show();
                }


                ContentValues contentValues = new ContentValues();
                contentValues.put(dconstant.DID,id);

                contentValues.put(dconstant.DNAME,name);
                contentValues.put(dconstant.DEMAIL,email);
                contentValues.put(dconstant.DPHONE,phone);
                contentValues.put(dconstant.DAGE,age);
                contentValues.put(dconstant.DGENDER,"male");
                contentValues.put(dconstant.DADDRESS,address);
                long row = sq.insert(dconstant.TABLE_NAME,null,contentValues);
                if(row > 0)
                {
                    Toast.makeText(getActivity(), "Driver details added successfully!!" , Toast.LENGTH_SHORT).show();
                }
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,new DriverDetailsf()).commit();



            }
        });

        return view;

    }

}
