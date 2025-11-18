package com.example.examen1erparcia_yubo

import java.nio.DoubleBuffer
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
    /*
    fun filtrar_productobit(filtro:(p: Producto_Cesta)-> Boolean): List<Producto_Cesta>{
        return  productos_cesta.filter { filtro(it) }
    }
    */
    /**
     * Funcion que sirve para filtrar productos
     */

    fun filtrar_Productos(filtro:(p:Producto_Cesta)-> Boolean): List<Producto_Cesta>{
       //Recorro la lista de productos de la cesta
        // y compruebo por la funcion filtro si ese pruducto
        // se va a añadir a la lista resultado
        val lista_resultado= mutableListOf<Producto_Cesta>()


        // lo mismo pero con foreach
        productos_cesta.forEach { producto->
            if(filtro(producto)== true)
            lista_resultado.add(producto)
        }

        /*

        for (producto in productos_cesta){
            if(filtro(producto)== true){
                //El producto cumple el criterio del filtro
                //lo añado a una lista de resultados

                lista_resultado.add(producto)
            }
        }
        */

        return  lista_resultado.toList()

    }


    //fun calcularTotal_bit(): Double = productos_cesta.sumOf { it.precio }

    /**
     * para suma total
     */
    override fun calcularTotal(): Double {
        var sumaTotal = 0.0


        /*
         sumaTotal = productos_cesta.sumOf { it.precio }
        o

        for (i in productos_cesta){
            sumaTotal += i.precio
        }

        */

        productos_cesta.forEach {producto-> sumaTotal += producto.precio }


        return sumaTotal
    }

    /**
     * Funcion agregar producto, agrega un producto a la cesta
     */

    fun agregar_Producto(productoCesta: Producto_Cesta) {
         productos_cesta.add(productoCesta)
    }


    /**
     *  Funcion para obtener la cesta de la compra
     *  Retorna una Lista no modificable, para proteger la lista
     */

    //fun  obtener_Productos()= productos_cesta.toList()

    fun  obtener_Productos(): List<Producto_Cesta>{
        return productos_cesta
    }





}


