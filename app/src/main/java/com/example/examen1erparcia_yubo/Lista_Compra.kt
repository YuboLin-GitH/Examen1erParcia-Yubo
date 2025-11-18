package com.example.examen1erparcia_yubo

import java.util.Date


// Clase lista_Compra implementa la interface Calculadora
class Lista_Compra(var fecha: Date): Calculable {

//atributos


    // Inicializo el arrayList

    private val productos_cesta  = mutableListOf<Producto_Cesta>(
        Producto_Cesta(
            "Patata",
            TipoProducto.COMIDA,
            2.0
        ),
        Producto_Cesta(
            "Agua",
            TipoProducto.BEBIDA,
            1.5
        ),
        Producto_Cesta(
            "Jabon",
            TipoProducto.LIMPIEZA_HIGIENE,
            12.5
        ),
        Producto_Cesta(
            "Jugete",
            TipoProducto.OTROS,
            15.0
        )
    )


    //-----------Parte funciones--------------------------

    fun filtrar_Productos(): Array<Int>{
        var listaResultado = mutableListOf<Int>()


        for (elemento in filtrar_Productos())
        {
            listaResultado.add(elemento)
        }
        return listaResultado.toTypedArray()

    }


    override fun calcularTotal(): Double {
        var sumaTotal = 0.0


        /*
         sumaTotal = productos_cesta.sumOf { it.precio }
        o

        productos_cesta.forEach { sumaTotal += it.precio }
        */

        for (i in productos_cesta){
            sumaTotal += i.precio
        }
        return sumaTotal
    }


    fun Agregar_Producto(productoCesta: Producto_Cesta) {
         productos_cesta.add(productoCesta)
    }


    fun  obtener_Productos():  MutableList<Producto_Cesta>{
        return productos_cesta
    }


}


