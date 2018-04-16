package xyz.yeyjho.vdtalkingtom;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import xyz.yeyjho.Objetos.*;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;
    Button btnRegistrar;
    EditText etNombre;
    EditText etDocumento;
    EditText etId;
    EditText etEdad;
    Cliente clientRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etDocumento = (EditText) findViewById(R.id.etDocumento);
        etId = (EditText) findViewById(R.id.etId);
        etEdad = (EditText) findViewById(R.id.etEdad);

        btnRegistrar.setOnClickListener(new View.OnClickListener() { //button4 corresponde al id del botón
            public void onClick(View view) {
                Log.d("MyApp","Intentando grabar...");
                writeCliente(etId.getText().toString(),
                        etNombre.getText().toString(),
                        etDocumento.getText().toString(),
                        Integer.parseInt(etEdad.getText().toString()));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        Log.d("MyApp","Intentado loguearse");
        login("jhersonjhochoa@gmail.com","$1$2$3");
        mDatabase = FirebaseFirestore.getInstance();
    }

    public void writeCliente(final String userId, String nombre, String documento, int edad) {
        final Cliente user = new Cliente(nombre, documento, edad);
        mDatabase.collection("Clientes").document(userId).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("MyApp", "DocumentSnapshot added with ID: " +userId);
                Toast.makeText(MainActivity.this, "Todo good", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("MyApp", "Error adding document", e);
                Toast.makeText(MainActivity.this, "Falló la grabación", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Cliente recuperarCliente(String docId){
        DocumentReference docRef = mDatabase.collection("Clientes").document(docId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Toast.makeText(MainActivity.this, "Cliente Recuperado", Toast.LENGTH_SHORT).show();
                clientRef = documentSnapshot.toObject(Cliente.class);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                clientRef=null;
            }
        });
        return clientRef;
    }

    private void login(String user, String pass){
        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Falló la autenticación", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(MainActivity.this, "autenticación correcta", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Log.d("MyApp","Login correcto");
        } else {
            //login("jhersonjhochoa@gmail.com","$1$2$3");
            Log.d("MyApp","Login incorrecto");
        }
    }
}
