package com.example.calculator

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable


class CalculatorViewModel :ViewModel(){

    private val _equationTexT = MutableLiveData("")
    val equationText: LiveData<String> = _equationTexT

    private val _resultText = MutableLiveData("0")
    val resultText : LiveData<String> = _resultText

    fun onButtonClick(btn : String){
        Log.i("Clicked Button", btn)

        _equationTexT.value?.let {
            if(btn == "AC"){
                _equationTexT.value = ""
                _resultText.value = "0"
                return
            }

            if(btn == "C"){
                if(it.isNotEmpty()){
                    _equationTexT.value = it.substring(0, it.length - 1)
                }
                return
            }

            if(btn == "="){
                _equationTexT.value = _resultText.value
                return
            }

            _equationTexT.value = it+btn

            //Calcular el resultado
            try  {
                _resultText.value = calculateResult(_equationTexT.value.toString())
            }catch (_ : Exception){

            }

        }
    }

    fun calculateResult(equation: String): String {
        val context : Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable : Scriptable = context.initStandardObjects()
        var finalResult = context.evaluateString(scriptable, equation, "JavaScript", 1, null).toString()
        if (finalResult.endsWith(".0")) {
            finalResult = finalResult.replace(".0", "")

        }
        return finalResult
    }

}