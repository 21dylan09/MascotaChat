package com.example.mascotachat;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChatCuidador extends AppCompatActivity {

    private TextView messageHistory;
    private EditText responseInput;
    private Button sendBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care);

        messageHistory = findViewById(R.id.messageHistory);
        responseInput = findViewById(R.id.responseInput);
        sendBackButton = findViewById(R.id.sendBackButton);

        // Obtener el historial de mensajes desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("chatHistory", MODE_PRIVATE);
        String chatHistory = sharedPreferences.getString("history", "");  // Cargar el historial guardado
        messageHistory.setText(chatHistory);  // Mostrar el historial en el TextView

        // Recibir el mensaje del propietario solo la primera vez
        Intent intent = getIntent();
        String messageFromOwner = intent.getStringExtra("message");

        // Verificar si el mensaje de propietario ya está en el historial, si no, agregarlo
        if (messageFromOwner != null && !chatHistory.contains("Propietario: " + messageFromOwner)) {
            messageHistory.append("\nPropietario: " + messageFromOwner);  // Mostrar el mensaje del propietario
        }

        sendBackButton.setOnClickListener(v -> {
            // Obtener la respuesta del cuidador
            String responseMessage = responseInput.getText().toString();
            if (!responseMessage.isEmpty()) {
                // Modificar el historial antes de actualizarlo
                String updatedHistory = chatHistory + "\nCuidador: " + responseMessage;

                // Actualizar el historial en el TextView
                messageHistory.setText(updatedHistory);

                // Guardar el historial actualizado en SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("history", updatedHistory);  // Guardar el historial actualizado
                editor.apply();

                // Enviar la respuesta de vuelta al propietario
                Intent intentBack = new Intent(ChatCuidador.this, ChatUsuario.class);
                intentBack.putExtra("responseMessage", responseMessage);  // Enviar la respuesta al propietario
                startActivity(intentBack);
            } else {
                Toast.makeText(ChatCuidador.this, "Por favor, escribe una respuesta.", Toast.LENGTH_SHORT).show();
            }
   });
}
}