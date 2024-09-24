package com.example.tugas1kalkulatorv2

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugas1kalkulatorv2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var input = ""
    private var operator = ""
    private var firstOperand = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val numberButtons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9
        )

        val operatorButtons = listOf(
            binding.btnAdd, binding.btnSubtract, binding.btnMultiply, binding.btnDivide
        )

        numberButtons.forEach { button ->
            button.setOnClickListener { appendText((it as Button).text.toString()) }
        }

        operatorButtons.forEach { button ->
            button.setOnClickListener { setOperator((it as Button).text.toString()) }
        }

        binding.btnClear.setOnClickListener { clear() }
        binding.btnDelete.setOnClickListener { delete() }
        binding.btnEqual.setOnClickListener { calculateResult() }
    }

    private fun appendText(value: String) {
        input += value
        binding.tvCalculation.text = input //menampilkan input angka pertama
    }

    private fun setOperator(selectedOperator: String) {
        if (input.isNotEmpty() && operator.isEmpty()) {
            firstOperand = input.toDoubleOrNull() ?: 0.0
            operator = selectedOperator
            input += " $operator "
            binding.tvCalculation.text = input //menampilkan angka pertama dan operator
        }
    }

    private fun clear() {  //menghapus seluruhnya (angka ataupun operator)
        input = ""
        operator = ""
        firstOperand = 0.0
        binding.tvCalculation.text = ""
        binding.tvResult.text = ""
    }

    private fun delete() { //menghapus karakter terakhir input jika tidak kosong
        if (input.isNotEmpty()) {
            input = input.dropLast(1)
            binding.tvCalculation.text = input
        }
    }

    private fun calculateResult() { //logic perhitungann
        if (input.isNotEmpty() && operator.isNotEmpty()) {
            val operands = input.split(" $operator ")
            if (operands.size == 2) {
                val secondOperand = operands[1].toDoubleOrNull() ?: 0.0
                val result = when (operator) {
                    "+" -> firstOperand + secondOperand
                    "-" -> firstOperand - secondOperand
                    "*" -> firstOperand * secondOperand
                    "/" -> if (secondOperand != 0.0) firstOperand / secondOperand else Double.NaN
                    else -> 0.0
                }

                binding.tvResult.text = result.toString()
                Toast.makeText(this, "Result: $result", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
