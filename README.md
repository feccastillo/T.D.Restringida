# T.D.Restringida
Triangulación de Delaunay Restringida 
Las mallas geométricas son herramientas fundamentales en ingeniería y en la ciencia, en particular en la modelación de fenómenos físicos a través de Ecuaciones Diferenciales Parciales. Una malla es un conjunto de caras poligonales (cuadriláteros, triángulos o tetraedros) que definen una superficie en el espacio tal que sí "i"y "j" son dos elementos de una malla T, entonces:"i" ,"j" es un vértice común, una arista común, una cara común o el conjunto vacío. Una malla tiene asociada un conjunto de elementos topológicos tales como: vértices, aristas y caras poligonales. Una malla se obtiene a partir de la discretización de un dominio aplicando un algoritmo de triangulación. Si el dominio es un conjunto de puntos, la triangulación obtenida es una partición de su cierre convexo en triángulos, tal que cada punto presente en nube sea un vértice de un triángulo. Por lo tanto a la triangulación sobre el dominio en 2D se le denomina malla de triángulos, y en 3D malla de tetraedros.

Uno de los primeros algoritmos de generación de triangulaciones fue propuesto por el matemático ruso Boris Nikolaevich Delaunay. Este algoritmo se conoce como Triangulación de Delaunay a través del cual se puede obtener una malla de triángulos, que a partir de un conjunto de puntos en el espacio conforman un polígono convexo y conexo, lo que implica que el resultado es un único conjunto de triángulos unidos de tal forma que se puede ir de cualquiera de estos a otro en linea recta sin salir del polígono.




También se debe tener en cuenta que una triangulación es de Delaunay si cada triángulo presente en la triangulación cumple el Test del Círculo, lo que implica que el círculo circunscrito en cada triangulo solo debe contener los vértices que conforman el triángulo testeado.

A de considerarse teambien que este metodo permite restringir una malla sin imporar el algoritmo de triangulacion de Delaunay que se ocupe, lo que hoy en dia es un gran problema en el estudio de las malas geometricas.

Por otro lado se entiende Triangulación de Delaunay Restringida en este documento como a una Triangulación de Delaunay que concertaba los límites de la figura inicial a tratar. A menudo una Triangulación de Delaunay Restringida contiene triángulos en el borde y espacios intermedios que no satisfacen la condición de Delaunay, además tiende a formar polígonos cóncavos. Por lo tanto, una Triangulación de Delaunay Restringida no suele ser una Triangulación de Delaunay en sí misma.

## Ejemplo

### Lago budi

![lago original](https://github.com/feccastillo/T.D.Restringida/blob/master/LagoBudiOriginal.png)

### Imagen tratada con el software

![canny](https://github.com/feccastillo/T.D.Restringida/blob/master/LagoBudiCanny.png)

### mapa de vertices dibujado

![vertices](https://github.com/feccastillo/T.D.Restringida/blob/master/LagoBudiVertices.png)

### Triangulacion de Delaunay

![TD](https://github.com/feccastillo/T.D.Restringida/blob/master/LagoBudiDelaunay.png)

### Triangulacion de Delaunay restringida

![TDR](https://github.com/feccastillo/T.D.Restringida/blob/master/LagoBudiDelaunayRestringida.png)
