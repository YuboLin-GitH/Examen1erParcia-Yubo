package com.example.examen1erparcia_yubo

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examen1erparcia_yubo.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var mibinding: ActivityMainBinding
    lateinit var spinnerTipoProducto: Spinner
    var listaCompra : MutableList<Lista_Compra> = mutableListOf(

    )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        mibinding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(mibinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: Toolbar = mibinding.toolbar
        toolbar.title="LISTA DE LA COMPRA"
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white, theme))
        setSupportActionBar(toolbar)

        inicializarSpinner()
        inicializar_componentes()

        pruebaDato()

        avanzarProductos()


    }




    fun inicializar_componentes() {
        mibinding.etFechaCompra.setOnClickListener {
            datePicker()
            habilitaBotonAnadir()
        }

        mibinding.btAnadirProducto.setOnClickListener {
            var nuevoComprar: Lista_Compra? = null
            nuevoComprar=listaCompra.find {fe-> fe.fecha==mibinding.etFechaCompra.text }
            val formato = SimpleDateFormat("dd-MM-yyyy")
            nuevoComprar?.let {
                mibinding.etFechaCompra.setText(formato.format(it.fecha))

                val builder= AlertDialog.Builder(this)
                builder.setMessage("Existe una lista")
                builder.setTitle("Error")
                builder.setPositiveButton("Aceptar"){_,_->}
                builder.create().show()
            }

            /*
            if (nuevoComprar == null){

                val stringfecha = mibinding.etFechaCompra.text.toString()
                val fecha: Date = formato.parse(stringfecha)

                if (spinnerTipoProducto.selectedItem == "COMIDA"){

                }


            }
            */

            mibinding.switchFiltraImporte.isEnabled = true
            mibinding.switchFiltraProducto.isEnabled = true
            mibinding.btAvanzar.isEnabled = true
            mibinding.btRetroceso.isEnabled = true

        }

    }

    fun pruebaDato(){
        mibinding.etNombreProducto.setOnClickListener {
            habilitaBotonAnadir()
        }
        mibinding.editTextNumberDecimal.setOnClickListener {
            habilitaBotonAnadir()
        }
    }

    fun inicializarSpinner() {
        var miadaptador= ArrayAdapter<TipoProducto>(this,android.R.layout.simple_spinner_item,
            arrayOf(TipoProducto.COMIDA, TipoProducto.BEBIDA, TipoProducto.LIMPIEZA_HIGIENE, TipoProducto.OTROS))
        mibinding.spinnerTipoProducto.adapter=miadaptador
        mibinding.spinnerTipoProducto.setSelection(0)
    }

    private fun datePicker(){
        // Valores por defecto del DatePicker
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { view, year1, monthOfYear, dayOfMonth ->

                mibinding.etFechaCompra.setText("$dayOfMonth-$monthOfYear-$year1")
                //temp = dateChoice
            }, year, month, day
        )
        datePickerDialog.show()
    }

    fun habilitaBotonAnadir(){

        var fechaCompra = mibinding.etFechaCompra.text
        var nombreProducto = mibinding.etNombreProducto.text
        var importe = mibinding.editTextNumberDecimal.text

        if (fechaCompra.isEmpty() || nombreProducto.isEmpty() || importe.isEmpty()){
            mibinding.btAnadirProducto.isEnabled = false
        }
        else {
            mibinding.btAnadirProducto.isEnabled = true
        }
    }



    private fun avanzarProductos() {
            mibinding.btAvanzar.setOnClickListener {

                }
    }


}






