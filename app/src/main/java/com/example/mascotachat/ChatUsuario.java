package com.example.mascotachat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChatUsuario extends AppCompatActivity {

    private EditText messageInput;
    private TextView messageHistory;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageInput = findViewById(R.id.messageInput);
        messageHistory = findViewById(R.id.messageHistory);
        sendButton = findViewById(R.id.sendButton);

        // Obtener el historial de mensajes desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("chatHistory", MODE_PRIVATE);
        String chatHistory = sharedPreferences.getString("history", "");  // Cargar el historial guardado
        messageHistory.setText(chatHistory);  // Mostrar el historial en el TextView

        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString();

            // Verificar si el mensaje no está vacío
            if (!message.isEmpty()) {
                // Modificar el historial antes de actualizarlo
                String updatedHistory = chatHistory + "\nPropietario: " + message;

                // Actualizar el historial en el TextView
                messageHistory.setText(updatedHistory);

                // Guardar el historial actualizado en SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("history", updatedHistory);  // Guardar el historial actualizado
                editor.apply();

                // Enviar el mensaje al cuidador
                Intent intentToCaregiver = new Intent(ChatUsuario.this, ChatCuidador.class);
                intentToCaregiver.putExtra("message", message);  // Enviar el mensaje al cuidador
                startActivity(intentToCaregiver);
            } else {
                Toast.makeText(ChatUsuario.this, "Por favor, escribe un mensaje.", Toast.LENGTH_SHORT).show();
            }
   });
}
}
