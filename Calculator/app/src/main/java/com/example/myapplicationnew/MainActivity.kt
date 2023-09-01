package com.example.myapplicationnew
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationnew.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun onOperatorClick(view: View) {

        if (!stateError && lastNumeric) {
            binding.dataTV.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            onEqual()
        }
    }

    fun onDigitClick(view: View) {

        if (stateError) {
            binding.dataTV.text = (view as Button).text
            stateError = false
        } else {
            binding.dataTV.append((view as Button).text)
        }

        lastNumeric = true
    }

    fun onEqual() {
        if (lastNumeric && !stateError) {
            val txt = binding.dataTV.text.toString()
            expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()
                binding.resultTV.visibility = View.VISIBLE
                binding.resultTV.text = "="+result.toString()
            } catch (ex: ArithmeticException) {
                Log.e("evaluate error", ex.toString())
                binding.resultTV.text = "Error"
                stateError = true
                lastNumeric = false

            }
        }
    }

    fun onAllClearClick(view: View) {

        binding.dataTV.text = ""
        binding.resultTV.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
        binding.resultTV.visibility = View.GONE
    }

    fun onBackClick(view: View) {
        binding.dataTV.text = binding.dataTV.text.toString().dropLast(1)

        try {
            val lastChar = binding.dataTV.text.toString().last()
            if (lastChar.isDigit()) {
            }
        } catch (e: Exception) {
            binding.resultTV.text = ""
            binding.resultTV.visibility = View.GONE
            Log.e("last char error", e.toString())
        }
    }

    fun onEqualClick(view: View) {
        onEqual()
        binding.dataTV.text = binding.resultTV.text.toString().drop(1)
    }
}
