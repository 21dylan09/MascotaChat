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

    private EditText entradaMensaje;
    private TextView historialMensajes;
    private Button botonEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entradaMensaje = findViewById(R.id.messageInput);
        historialMensajes = findViewById(R.id.messageHistory);
        botonEnviar = findViewById(R.id.sendButton);

        // Obtener el historial de mensajes desde SharedPreferences
        SharedPreferences preferenciasCompartidas = getSharedPreferences("chatHistory", MODE_PRIVATE);
        String historial = preferenciasCompartidas.getString("history", "");  // Cargar el historial guardado
        historialMensajes.setText(historial);  // Mostrar el historial en el TextView

        botonEnviar.setOnClickListener(v -> {
            String mensaje = entradaMensaje.getText().toString();

            // Verificar si el mensaje no está vacío
            if (!mensaje.isEmpty()) {
                // Modificar el historial antes de actualizarlo
                String historialActualizado = historial + "\nPropietario: " + mensaje;

                // Actualizar el historial en el TextView
                historialMensajes.setText(historialActualizado);

                // Guardar el historial actualizado en SharedPreferences
                SharedPreferences.Editor editor = preferenciasCompartidas.edit();
                editor.putString("history", historialActualizado);  // Guardar el historial actualizado
                editor.apply();

                // Enviar el mensaje al cuidador
                Intent intentAlCuidador = new Intent(ChatUsuario.this, ChatCuidador.class);
                intentAlCuidador.putExtra("message", mensaje);  // Enviar el mensaje al cuidador
                startActivity(intentAlCuidador);
            } else {
                Toast.makeText(ChatUsuario.this, "Por favor, escribe un mensaje.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
