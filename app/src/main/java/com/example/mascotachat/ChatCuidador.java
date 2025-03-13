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

    private TextView historialMensajes;
    private EditText entradaRespuesta;
    private Button botonEnviarRespuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care);

        historialMensajes = findViewById(R.id.messageHistory);
        entradaRespuesta = findViewById(R.id.responseInput);
        botonEnviarRespuesta = findViewById(R.id.sendBackButton);

        // Obtener el historial de mensajes desde SharedPreferences
        SharedPreferences preferenciasCompartidas = getSharedPreferences("chatHistory", MODE_PRIVATE);
        String historial = preferenciasCompartidas.getString("history", "");  // Cargar el historial guardado
        historialMensajes.setText(historial);  // Mostrar el historial en el TextView

        // Recibir el mensaje del propietario solo la primera vez
        Intent intent = getIntent();
        String mensajeDelPropietario = intent.getStringExtra("message");

        // Verificar si el mensaje de propietario ya está en el historial, si no, agregarlo
        if (mensajeDelPropietario != null && !historial.contains("Propietario: " + mensajeDelPropietario)) {
            historialMensajes.append("\nPropietario: " + mensajeDelPropietario);  // Mostrar el mensaje del propietario
        }

        botonEnviarRespuesta.setOnClickListener(v -> {
            // Obtener la respuesta del cuidador
            String mensajeRespuesta = entradaRespuesta.getText().toString();
            if (!mensajeRespuesta.isEmpty()) {
                // Modificar el historial antes de actualizarlo
                String historialActualizado = historial + "\nCuidador: " + mensajeRespuesta;

                // Actualizar el historial en el TextView
                historialMensajes.setText(historialActualizado);

                // Guardar el historial actualizado en SharedPreferences
                SharedPreferences.Editor editor = preferenciasCompartidas.edit();
                editor.putString("history", historialActualizado);  // Guardar el historial actualizado
                editor.apply();

                // Enviar la respuesta de vuelta al propietario
                Intent intentRegreso = new Intent(ChatCuidador.this, ChatUsuario.class);
                intentRegreso.putExtra("responseMessage", mensajeRespuesta);  // Enviar la respuesta al propietario
                startActivity(intentRegreso);
            } else {
                Toast.makeText(ChatCuidador.this, "Por favor, escribe una respuesta.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
