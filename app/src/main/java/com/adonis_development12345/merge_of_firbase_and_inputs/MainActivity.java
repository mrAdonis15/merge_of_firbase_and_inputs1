package com.adonis_development12345.merge_of_firbase_and_inputs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView txtAddressLabel, txtMunicipalityLabel, txtLongitudeLabel, txtLatitudeLabel;
    private EditText editTextAddress, editTextMunicipality, editTextLongitude, editTextLatitude;
    private Button addButton, viewButton, view_data;

    private List<String> addressesList = new ArrayList<>();
    private List<String> municipalitiesList = new ArrayList<>();
    private List<Double> longitudesList = new ArrayList<>();
    private List<Double> latitudesList = new ArrayList<>();

    DatabaseReference coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        txtAddressLabel = findViewById(R.id.txtAddressLabel);
        txtMunicipalityLabel = findViewById(R.id.txtMunicipalityLabel);
        txtLongitudeLabel = findViewById(R.id.txtLongitudeLabel);
        txtLatitudeLabel = findViewById(R.id.txtLatitudeLabel);

        editTextAddress = findViewById(R.id.editTextAddress);
        editTextMunicipality = findViewById(R.id.editTextMunicipality);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        editTextLatitude = findViewById(R.id.editTextLatitude);

        addButton = findViewById(R.id.addButton);
        viewButton = findViewById(R.id.viewButton);
        view_data = findViewById(R.id.ViewAllData);

        coordinates = FirebaseDatabase.getInstance().getReference("Coordinates");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCoordinates();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCoordinates();
            }
        });


        view_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_all_data();
            }
        });
    }

    private void addCoordinates() {
        String address = editTextAddress.getText().toString().trim();
        String municipality = editTextMunicipality.getText().toString().trim();
        String longitudeString = editTextLongitude.getText().toString().trim();
        String latitudeString = editTextLatitude.getText().toString().trim();

        // check for missing data
        if (TextUtils.isEmpty(address) || TextUtils.isEmpty(municipality) || TextUtils.isEmpty(longitudeString) || TextUtils.isEmpty(latitudeString)) {
            Toast.makeText(MainActivity.this, "Please fill all the blanks", Toast.LENGTH_SHORT).show();
            return;
        }

        double longitude = Double.parseDouble(longitudeString);
        double latitude = Double.parseDouble(latitudeString);

        // check if the data already exists
        if (addressesList.contains(address) && municipalitiesList.contains(municipality) && longitudesList.contains(longitude) && latitudesList.contains(latitude)) {
            Toast.makeText(MainActivity.this, "Already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // add the coordinates
        String id = coordinates.push().getKey();
        Coordinates coordinates1 = new Coordinates(id, address, municipality, longitude, latitude);
        assert id != null;
        coordinates.child(id).setValue(coordinates1);
        Toast.makeText(MainActivity.this, "Your data is saved", Toast.LENGTH_SHORT).show();

        // add the data to the lists
        addressesList.add(address);
        municipalitiesList.add(municipality);
        longitudesList.add(longitude);
        latitudesList.add(latitude);
    }

    private void viewCoordinates(){

        viewButton.setOnClickListener(v -> {
            StringBuilder sb = new StringBuilder();
            if(addressesList.size() == 0 || municipalitiesList.size() == 0 || longitudesList.size() == 0 || latitudesList.size() == 0) {
                sb.append("There's no data");
            } else {
                for (int i = 0; i < addressesList.size(); i++) {
                    sb.append("Address: " + addressesList.get(i) + "\n");
                    sb.append("Municipality: " + municipalitiesList.get(i) + "\n");
                    sb.append("Longitude: " + longitudesList.get(i) + "\n");
                    sb.append("Longitude: " + longitudesList.get(i) + "\n");
                    sb.append("Latitude: " + latitudesList.get(i) + "\n\n");
                }
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(sb.toString()).setPositiveButton("OK", null).show();
        });
    }

    private void view_all_data() {
        coordinates.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuilder sb = new StringBuilder();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Coordinates coordinates1 = dataSnapshot.getValue(Coordinates.class);
                    sb.append("Address: " + coordinates1.getAddressesList() + "\n");
                    sb.append("Municipality: " + coordinates1.getMunicipalitiesList() + "\n");
                    sb.append("Longitude: " + coordinates1.getLongitudesList() + "\n");
                    sb.append("Latitude: " + coordinates1.getLatitudesList() + "\n\n");
                }

                if (sb.length() == 0) {
                    sb.append("There's no data");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(sb.toString()).setPositiveButton("OK", null).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
