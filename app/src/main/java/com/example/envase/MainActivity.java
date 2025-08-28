package com.example.envase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private int plainTextCount = 0;
    private final int MAX_PLAIN_TEXTS = 30;

    private EditText editTextDatoAComparar;
    private TextView textViewSuma;
    private TextView textViewResta;
    private TextView textViewDivision66;
    private TextView textViewDivision77;
    private TextView textViewDivision84;
    private Button buttonLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.linearLayout);
        textViewSuma = findViewById(R.id.textView_suma);
        textViewResta = findViewById(R.id.textView_resta);
        textViewDivision66 = findViewById(R.id.textView_division66);
        textViewDivision77 = findViewById(R.id.textView_division77);
        textViewDivision84 = findViewById(R.id.textView_division84);
        buttonLimpiar = findViewById(R.id.button_limpiar);
        editTextDatoAComparar = findViewById(R.id.editText_dato_a_comparar);

        // Agrega los primeros 10 PlainTexts
        for (int i = 0; i < 10; i++) {
            addPlainText();
        }

        buttonLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampos();
            }
        });

        editTextDatoAComparar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calcularSuma();
                calcularResta();
                calcularDivisiones();
            }
        });
    }

    private void addPlainText() {
        if (plainTextCount < MAX_PLAIN_TEXTS) {
            EditText editText = new EditText(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            editText.setLayoutParams(params);
            editText.setHint("Pedido No. " + (plainTextCount + 1));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setId(View.generateViewId());

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().isEmpty()) {
                        calcularSuma();
                        calcularResta();
                        calcularDivisiones();
                    }
                    // Si es el último EditText y hay texto, añadir uno nuevo
                    if (editText.getId() == linearLayout.getChildAt(linearLayout.getChildCount() - 1).getId() && plainTextCount < MAX_PLAIN_TEXTS) {
                        addPlainText();
                    }
                }
            });

            linearLayout.addView(editText);
            plainTextCount++;
        }
    }

    private void calcularSuma() {
        int suma = 0;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                if (!editText.getText().toString().isEmpty()) {
                    try {
                        suma += Integer.parseInt(editText.getText().toString());
                    } catch (NumberFormatException e) {
                        // Manejar el error
                    }
                }
            }
        }
        textViewSuma.setText(String.valueOf(suma));
    }

    private void calcularResta() {
        String datoACompararStr = editTextDatoAComparar.getText().toString();
        if (!datoACompararStr.isEmpty()) {
            try {
                int datoAComparar = Integer.parseInt(datoACompararStr);
                int suma = Integer.parseInt(textViewSuma.getText().toString());
                int resta = datoAComparar - suma;
                textViewResta.setText(String.valueOf(resta));
            } catch (NumberFormatException e) {
                // Manejar el error
            }
        }
    }

    private void calcularDivisiones() {
        String restaStr = textViewResta.getText().toString();
        if (!restaStr.isEmpty()) {
            try {
                double resta = Double.parseDouble(restaStr);
                double division66 = resta / 66;
                double division77 = resta / 77;
                double division84 = resta / 84;

                // Si el resultado es NaN o Infinito, se establece el texto "0"
                textViewDivision66.setText(Double.isNaN(division66) || Double.isInfinite(division66) ? "0" : String.format("%.2f", division66));
                textViewDivision77.setText(Double.isNaN(division77) || Double.isInfinite(division77) ? "0" : String.format("%.2f", division77));
                textViewDivision84.setText(Double.isNaN(division84) || Double.isInfinite(division84) ? "0" : String.format("%.2f", division84));

            } catch (NumberFormatException e) {
                // Manejar el error
            }
        }
    }

    private void limpiarCampos() {
        // Eliminar todos los EditTexts del LinearLayout
        linearLayout.removeAllViews();

        // Reiniciar el contador de EditTexts dinámicos
        plainTextCount = 0;

        // Volver a agregar 10 EditTexts iniciales
        for (int i = 0; i < 10; i++) {
            addPlainText();
        }

        // Limpiar el EditText de "Dato a Comparar"
        editTextDatoAComparar.setText("");

        // Limpiar TextViews de resultados
        textViewSuma.setText("0");
        textViewResta.setText("0");
        textViewDivision66.setText("0");
        textViewDivision77.setText("0");
        textViewDivision84.setText("0");
    }
}