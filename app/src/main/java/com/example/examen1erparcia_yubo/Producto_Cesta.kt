package com.example.examen1erparcia_yubo



// enum TipoProducto
enum class TipoProducto {
    COMIDA, BEBIDA, LIMPIEZA_HIGIENE, OTROS
}


// clase de tipo data
data class Producto_Cesta(
    var nombre: String,
    var tipo: TipoProducto,
    var precio: Double
)





