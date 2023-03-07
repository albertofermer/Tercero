
addpath('Funciones')
addpath('Imagenes/Entrenamiento')

%%Lectura automatizada de imagenes.
numClases=3;
numImagenesXClase = 2;

nombres{1} = 'Circulo';
nombres{2} = 'Cuadrado';
nombres{3} = 'Triangulo';


for i=1:numClases
    for j=1:numImagenesXClase
        
        nombreImagen = [nombres{i} num2str(j,'%02d') '.jpg']
        I = imread(nombreImagen);
        
    end
end

X = [];
Y = [];

%%Paso 1, Binarizar con metodología de selección automática de Umbral.



contador = 1;

for i=1:numClases
    for j=1:numImagenesXClase
        
        nombreImagen = [nombres{i} num2str(j,'%02d') '.jpg'];
        I = imread(nombreImagen);
        
        umbral = 255*graythresh(I); %%Otsu.
        Ib = I < umbral;
        nombreSinExtension = [nombres{i} num2str(j,'%02d')];
        save("ImagenesBinarizadas/"+nombreSinExtension,'Ib' );
        
        %%Eliminar las componentes conectadas ruidosas de cada imagen:
         %Se ha realizado el etiquetado aprovechando que ya lo realicé en
         %la función.
        [IbinFiltrado,Ietiq] = funcion_elimina_regiones_ruidosas(Ib);
        N = size(unique(Ietiq),1)-1;
%         imshow(Ietiq);
%         figure
%         imshow(IbinFiltrado);
%         pause();


    %%Calcular los descriptores de cada agrupación conexa.
    %%XImagen contiene una matriz de 23 columnas con los descriptores y
    %%tantas filas como agrupaciones conexas existan.
    XImagen = funcion_calcula_descriptores_imagen(Ietiq,N);

    X = [X;XImagen];
    %%Genero Yimagen
    
    Y =[Y;ones(N,1)*i];
    
    end
end
    


nombreDescriptores = { 'Compacticidad', 'Excentricidad', 'Solidez_CHull(Solidity)', 'Extension_BBox(Extent)', 'Extension_BBox(Invariante Rotacion)', 'Hu1', 'Hu2', 'Hu3', 'Hu4', 'Hu5', 'Hu6', 'Hu7', 'DF1', 'DF2', 'DF3', 'DF4', 'DF5', 'DF6', 'DF7', 'DF8', 'DF9', 'DF10', 'NumEuler'};

%%Nombre de las clases del problema.
nombreClases{1} = 'Circulo';
nombreClases{2} = 'Cuadrado';
nombreClases{3} = 'Triangulo';

%%Simbolos y colores que los van a representar.
simbolosClases{1} = '*r';
simbolosClases{2} = '*g';
simbolosClases{3} = '*b';

%Creamos una clase para guardar esta informacion.
nombresProblema = [];
nombresProblema.descriptores = nombreDescriptores;
nombresProblema.clases = nombreClases;
nombresProblema.simbolos = simbolosClases;

save('./DatosGenerados/conjunto_datos','X','Y');
save('DatosGenerados/nombresProblema','nombresProblema');

