package com.example.examen1erparcia_yubo

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
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

    var indice_producto=0



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

        //Deshabilitar botones

        mibinding.btAnadirProducto.isEnabled= false
        mibinding.btAvanzar.isEnabled = false
        mibinding.btRetroceso.isEnabled = false
        mibinding.switchFiltraImporte.isEnabled = false
        mibinding.switchFiltraProducto.isEnabled = false


        mibinding.btAnadirProducto.setOnClickListener {
            //Añadir el producto a la lista de la compra_actual

            var miProducto= Producto_Cesta(
                mibinding.etNombreProducto.text.toString(),
                mibinding.spinnerTipoProducto.selectedItem as TipoProducto,
                mibinding.editTextNumberDecimal.text.toString().toDouble()
            )


            mi_lista_compra_actual?.let {
                it.agregar_Producto(miProducto)
                mibinding.tvImporteTotal.text = "IMPORTE TOTAL: ${it.calcularTotal()}"
            }
            //Vacio los campos de texto
            mibinding.etNombreProducto.setText("")
            mibinding.editTextNumberDecimal.setText("")
            //Recalculo el Importe Total

            Toast.makeText(this, "Producto Añadido", Toast.LENGTH_SHORT).show()
        }

        mibinding.btAvanzar.setOnClickListener {
            val productos = mi_lista_compra_actual?.obtener_Productos()


            if (productos != null && indice_producto < productos.size - 1){
                indice_producto++

                val producto = productos[indice_producto]
                mibinding.editTextNumberDecimal.setText(producto.precio.toString())
                mibinding.etNombreProducto.setText(producto.nombre)
                mibinding.spinnerTipoProducto.setSelection(producto.tipo.ordinal)



                mibinding.btAvanzar.isEnabled = indice_producto < productos.size - 1
                mibinding.btRetroceso.isEnabled = true


            }
            else {
                Toast.makeText(this, "Ya estás en el último producto", Toast.LENGTH_SHORT).show()
                mibinding.btAvanzar.isEnabled= false
            }
        }

        mibinding.btRetroceso.setOnClickListener {

            //Si no estoy al principio de la lista de producto
            //de la lista de compra actual
            if (indice_producto>0){
                var producto= mi_lista_compra_actual?.let {
                    it.obtener_Productos().get(indice_producto)

                }
                mibinding.editTextNumberDecimal.setText(producto?.precio.toString())
                mibinding.etNombreProducto.setText(producto?.nombre.toString())
                mibinding.spinnerTipoProducto.setSelection(producto?.tipo?.ordinal ?: 0)
                indice_producto--
            }
            else{
                //Deshabilito el boton retroceso
                mibinding.btRetroceso.isEnabled = false

            }
        }


        mibinding.switchFiltraProducto.setOnClickListener {
            var lista_productos = mutableListOf<Producto_Cesta>()

            if (mibinding.switchFiltraProducto.isChecked){
                    lista_productos = mi_lista_compra_actual.let {
                        it?.filtrar_Productos {
                            mibinding.spinnerTipoProducto.selectedItem == it.tipo
                        } as MutableList<Producto_Cesta>
                    }
            }
            //Actualizo el importe total de la lista de productos
            var importe = lista_productos?.let {
                it.sumOf { it.precio }
            }
            mibinding.tvImporteTotal.setText(importe.toString())

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

                var fecha_compra = SimpleDateFormat("dd-MM-yyyy").parse("$dayOfMonth-${monthOfYear +1}-$year1")

                mi_lista_compra_actual= listaCompra.find {it.fecha == fecha_compra}
                if (mi_lista_compra_actual!=null){
                        val builder= AlertDialog.Builder(this)
                    builder.run {
                        setMessage("Existe una lista con esa fecha")
                        setTitle("Lista Compra")
                        setPositiveButton("Aceptar"){_,_->}
                        create().show()
                    }
                }else{// No existe la lista de la compra,
                    //Instancio la nueva lista de la compra
                    mi_lista_compra_actual= Lista_Compra(fecha_compra)

                    //La añado a las listas
                    listaCompra.add(mi_lista_compra_actual!!)
                }

                //inicialInicializo un variable que me permite
                //recorrer los productos de la lista

                indice_producto=0

                //Si existe un producto en esa lista de la compra,
                //muestro el producto
                if(mi_lista_compra_actual!!.obtener_Productos().size>=1){
                   var producto_cesta= mi_lista_compra_actual!!.obtener_Productos().get(0)
                    mibinding.spinnerTipoProducto.setSelection(producto_cesta.tipo.ordinal)
                    mibinding.etNombreProducto.setText(producto_cesta.nombre)
                    mibinding.editTextNumberDecimal.setText(producto_cesta.precio.toString())

                }


                mibinding.tvImporteTotal.text = "IMPORTE TOTAL: ${mi_lista_compra_actual!!.calcularTotal()}"

                //Habilito el boton avanzar si hay mas de 1 producto
                mibinding.btAvanzar.isEnabled = (mi_lista_compra_actual!!.obtener_Productos().size>1)

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






