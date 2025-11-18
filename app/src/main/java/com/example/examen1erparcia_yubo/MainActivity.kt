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
import androidx.core.widget.addTextChangedListener
import com.example.examen1erparcia_yubo.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity() {
    lateinit var mibinding: ActivityMainBinding


    lateinit var spinnerTipoProducto: Spinner

    //Defino un mutableList de Lista_Compra, para
    //Poder almacenar todas las listas de la compra
    var listaCompra : MutableList<Lista_Compra> = mutableListOf()


    var mi_lista_compra_actual: Lista_Compra?=null



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

        /*
        var milista_compra = Lista_Compra(Calendar.getInstance().time)

        milista_compra.agregar_Producto(Producto_Cesta("gggg", TipoProducto.COMIDA, 5.10))

        // Filtro los pruductos con precio mayor que 10


        var milista_filtrada =    milista_compra.filtrar_Productos { producto ->
            producto.precio > 10
        }

        // Filtrar los pruducto cuyo primer caracter del nombre del producto es A
        milista_compra.filtrar_Productos { producto ->
            producto.nombre.get(0)== 'A'
            producto.tipo == TipoProducto.COMIDA
        }
        */

    }




    fun inicializar_componentes() {
        mibinding.etFechaCompra.setOnClickListener {
            //Obtener la fecha actual
            val calendario = Calendar.getInstance()

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


            //Deshabilitar botones

            mibinding.btAnadirProducto.isEnabled= false
            mibinding.btAvanzar.isEnabled = false
            mibinding.btRetroceso.isEnabled = false
            mibinding.switchFiltraImporte.isEnabled = false
            mibinding.switchFiltraProducto.isEnabled = false


        }

    }

    fun pruebaDato(){
        mibinding.etNombreProducto.addTextChangedListener() {
            habilitaBotonAnadir()
        }
        mibinding.editTextNumberDecimal.addTextChangedListener {
            habilitaBotonAnadir()
        }
        mibinding.etFechaCompra.addTextChangedListener {
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

                mibinding.etFechaCompra.setText("$dayOfMonth-${monthOfYear +1}-$year1")


                //Aqui voy a gestionar si existe una lista de la compra
                //con la fecha seleccionada

               // listaCompra.find { it.fecha }


                //temp = dateChoice
            }, year, month, day
        )
        datePickerDialog.show()
    }

    fun habilitaBotonAnadir(){
/*
        var fechaCompra = mibinding.etFechaCompra.text
        var nombreProducto = mibinding.etNombreProducto.text
        var importe = mibinding.editTextNumberDecimal.text

        if (fechaCompra.isEmpty() || nombreProducto.isEmpty() || importe.isEmpty()){
            mibinding.btAnadirProducto.isEnabled = false
        }
        else {
            mibinding.btAnadirProducto.isEnabled = true
        }


        */

        if((!mibinding.etFechaCompra.text.isEmpty())&& (!mibinding.etNombreProducto.text.isEmpty()) && (!mibinding.editTextNumberDecimal.text.isEmpty())){
            mibinding.btAnadirProducto.isEnabled=true
        }
        else{
            mibinding.btAnadirProducto.isEnabled=false
        }

    }



    private fun avanzarProductos() {
            mibinding.btAvanzar.setOnClickListener {

                }
    }


}






