package com.example.teste_fumator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity {
    private EditText camponome, campoemail, campoconfirmar, camposenha;
    private Button cadastrar;
    String usuarioID;
    String[] mensagem = {"Preencha todos os campos!", "Cadastro realizado com sucesso!", "Senha não confirmada!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().hide();
        Componentes();

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = camponome.getText().toString();
                String email = campoemail.getText().toString();
                String senha = camposenha.getText().toString();
                String confirmar = campoconfirmar.getText().toString();
                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmar.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v, mensagem[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else if(nome.equals(confirmar)){
                    Snackbar snackbar = Snackbar.make(v, mensagem[2], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
                else {
                    Cadastrar_usuario(v);
                }
            }
        });
    }

        private void Cadastrar_usuario(View v){
            String email = campoemail.getText().toString();
            String senha = camposenha.getText().toString();
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Salvar_dados();
                        Snackbar snackbar = Snackbar.make(v, mensagem[1], Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                        Intent l = new Intent(Cadastro.this, Informacoes.class);
                        startActivity(l);
                    } else{
                        String erro;
                        try {
                            throw  task.getException();
                        } catch (FirebaseAuthWeakPasswordException e){
                            erro = "Digite uma senha com no mínimo 6 caracteres!";
                        } catch (FirebaseAuthUserCollisionException e){
                            erro = "Essa conta já foi cadastrada!";
                        } catch (FirebaseAuthInvalidCredentialsException e){
                            erro = "E-mail inválido!";
                        }
                        catch (Exception e) {
                            erro = "Erro ao cadastrar usuário!";
                        }
                        Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                    }
                }
            });
        }

        private void Componentes(){
            camponome = findViewById(R.id.nome_cadastro);
            campoemail = findViewById(R.id.email_cadastro);
            camposenha = findViewById(R.id.senha_cadastro);
            campoconfirmar = findViewById(R.id.csenhacadastro);
            cadastrar = findViewById(R.id.cadastrar);
        }

        private void Salvar_dados(){
            String nome = camponome.getText().toString();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> usuarios = new HashMap<>();
            usuarios.put("nome", nome);

            usuarioID = FirebaseAuth.getInstance().getUid();
            DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
            documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("db", "Sucesso ao salvar os dados");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("erro", "Erro ao salvar os dados" + e.toString());
                }
            });
        }

    public void login(View v){
        Intent l = new Intent(this, Login.class);
        startActivity(l);
    }
}


