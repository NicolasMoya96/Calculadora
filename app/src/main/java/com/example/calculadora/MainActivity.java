package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declarar componentes
    private TextView tvResultado, tvOperacion;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Button btnSumar, btnRestar, btnMultiplicar, btnDividir;
    private Button btnIgual, btnLimpiar, btnBorrar, btnPunto;

    // Variables para los cálculos
    private String numeroActual = "";
    private String operacionActual = "";
    private double primerNumero = 0;
    private boolean nuevaOperacion = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazar componentes con el XML
        tvResultado = findViewById(R.id.tvResultado);
        tvOperacion = findViewById(R.id.tvOperacion);

        // Botones numéricos
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        // Botones de operaciones
        btnSumar = findViewById(R.id.btnSumar);
        btnRestar = findViewById(R.id.btnRestar);
        btnMultiplicar = findViewById(R.id.btnMultiplicar);
        btnDividir = findViewById(R.id.btnDividir);

        // Botones especiales
        btnIgual = findViewById(R.id.btnIgual);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnPunto = findViewById(R.id.btnPunto);

        // Configurar listeners para botones numéricos
        configurarBotonNumerico(btn0, "0");
        configurarBotonNumerico(btn1, "1");
        configurarBotonNumerico(btn2, "2");
        configurarBotonNumerico(btn3, "3");
        configurarBotonNumerico(btn4, "4");
        configurarBotonNumerico(btn5, "5");
        configurarBotonNumerico(btn6, "6");
        configurarBotonNumerico(btn7, "7");
        configurarBotonNumerico(btn8, "8");
        configurarBotonNumerico(btn9, "9");

        // Listener para el punto decimal
        btnPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!numeroActual.contains(".")) {
                    if (numeroActual.isEmpty()) {
                        numeroActual = "0.";
                    } else {
                        numeroActual += ".";
                    }
                    tvResultado.setText(numeroActual);
                }
            }
        });

        // Listeners para operaciones
        btnSumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarOperacion("+");
            }
        });

        btnRestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarOperacion("-");
            }
        });

        btnMultiplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarOperacion("×");
            }
        });

        btnDividir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarOperacion("÷");
            }
        });

        // Listener para el botón igual
        btnIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularResultado();
            }
        });

        // Listener para limpiar
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarTodo();
                Toast.makeText(MainActivity.this, "🗑️ Calculadora limpiada", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener para borrar último dígito
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!numeroActual.isEmpty()) {
                    numeroActual = numeroActual.substring(0, numeroActual.length() - 1);
                    if (numeroActual.isEmpty()) {
                        tvResultado.setText("0");
                    } else {
                        tvResultado.setText(numeroActual);
                    }
                }
            }
        });
    }

    // Método para configurar los botones numéricos
    private void configurarBotonNumerico(Button boton, final String numero) {
        boton.setOnClickListener(v -> {
            if (nuevaOperacion) {
                numeroActual = "";
                nuevaOperacion = false;
            }

            // Evitar múltiples ceros al inicio
            if (numeroActual.equals("0") && numero.equals("0")) {
                return;
            }

            // Reemplazar el cero inicial
            if (numeroActual.equals("0") && !numero.equals(".")) {
                numeroActual = numero;
            } else {
                numeroActual += numero;
            }

            tvResultado.setText(numeroActual);
        });
    }

    // Método para configurar una operación
    private void configurarOperacion(String operacion) {
        if (!numeroActual.isEmpty()) {
            if (!operacionActual.isEmpty()) {
                calcularResultado();
            } else {
                primerNumero = Double.parseDouble(numeroActual);
            }

            operacionActual = operacion;
            // Formatear el número sin .0 innecesario
            String numeroFormateado = formatearNumero(primerNumero);
            tvOperacion.setText(numeroFormateado + " " + operacion);
            numeroActual = ""; // Limpiar el número actual para que desaparezca
            tvResultado.setText("0"); // Mostrar 0 en el display
            nuevaOperacion = true;

            // Toast con emoji según la operación
            String emoji = "";
            switch (operacion) {
                case "+": emoji = "➕"; break;
                case "-": emoji = "➖"; break;
                case "×": emoji = "✖️"; break;
                case "÷": emoji = "➗"; break;
            }
            Toast.makeText(this, emoji + " Operación: " + operacion, Toast.LENGTH_SHORT).show();
        }
    }

    // Método para calcular el resultado
    private void calcularResultado() {
        if (!operacionActual.isEmpty() && !numeroActual.isEmpty()) {
            double segundoNumero = Double.parseDouble(numeroActual);
            double resultado = 0;
            boolean operacionValida = true;

            switch (operacionActual) {
                case "+":
                    resultado = primerNumero + segundoNumero;
                    break;
                case "-":
                    resultado = primerNumero - segundoNumero;
                    break;
                case "×":
                    resultado = primerNumero * segundoNumero;
                    break;
                case "÷":
                    if (segundoNumero == 0) {
                        Toast.makeText(this, "❌ Error: No se puede dividir por cero", Toast.LENGTH_LONG).show();
                        operacionValida = false;
                    } else {
                        resultado = primerNumero / segundoNumero;
                    }
                    break;
            }

            if (operacionValida) {
                // Mostrar la operación completa sin .0 innecesarios
                String num1Formateado = formatearNumero(primerNumero);
                String num2Formateado = formatearNumero(segundoNumero);
                tvOperacion.setText(num1Formateado + " " + operacionActual + " " + num2Formateado + " =");

                // Formatear el resultado (eliminar decimales innecesarios)
                String resultadoFormateado = formatearNumero(resultado);
                tvResultado.setText(resultadoFormateado);
                numeroActual = resultadoFormateado;

                primerNumero = resultado;
                operacionActual = "";
                nuevaOperacion = true;

                Toast.makeText(this, "✅ Resultado calculado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método para limpiar todo
    private void limpiarTodo() {
        numeroActual = "";
        operacionActual = "";
        primerNumero = 0;
        nuevaOperacion = true;
        tvResultado.setText("0");
        tvOperacion.setText("");
    }

    // Método para formatear números sin decimales innecesarios
    private String formatearNumero(double numero) {
        // Si es un número entero, mostrar sin decimales
        if (numero == (long) numero) {
            return String.format("%d", (long) numero);
        } else {
            // Si tiene decimales, eliminar ceros innecesarios al final
            String numeroStr = String.valueOf(numero);
            // Eliminar ceros al final después del punto decimal
            if (numeroStr.contains(".")) {
                numeroStr = numeroStr.replaceAll("0*$", "");
                numeroStr = numeroStr.replaceAll("\\.$", "");
            }
            return numeroStr;
        }
    }
}