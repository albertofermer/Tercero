function funcion_reconoce_formas(rutaFicheroImagen)

    %% Cargamos los datos de estandarización
    
    addpath('../1. Generación de Patrones de Reconocimiento/Datos Generados/');
    load("../1. Generación de Patrones de Reconocimiento/Datos Generados/conjunto_datos_estandarizados.mat");
    load("../1. Generación de Patrones de Reconocimiento/Datos Generados/datos_estandarizados.mat");

    %% Añadimos la ruta al fichero de entrenamiento
    load('../2. Diseño y Aplicación de Estrategias de Clasificación/QDA/Circulo-Cuadrado-Triangulo/Datos Generados/espacio_ccas.mat');
    
   
    
    clear rutaFichero nombreFichero
    
    %% Leemos la imagen
    I = imread(rutaFicheroImagen);
    
    %% Obtenemos el umbral de la imagen
    umbral = graythresh(I);
    
    %% Obtenemos la imagen binaria
    Ibin = I < 255*umbral;
    
    %% Eliminamos el ruido
    Ibin_filt = funcion_elimina_regiones_ruidosas(Ibin);
    
    %% Comprobamos que queden objetos en la imagen, en caso contrario no devolvemos nada
    
    if sum(sum(Ibin_filt)) > 0
        
        %% Obtenemos la imagen etiquetada
        [Ietiq,N] = bwlabel(Ibin_filt);
        
        %% Por cada objeto de la imagen, calculamos sus descriptores 
        XImagen = funcion_calcula_descriptores_imagen(Ietiq,N);
        
        %% Estandarizamos los descriptores
        Z = XImagen;
        for i=1:size(XImagen,2) - 1
            Z(:,i) = (XImagen(:,i)-medias(i))/desviaciones(i);
        end
        ZRed = Z(:,espacioCcas);
        
        %% Por cada objeto de la imagen, vamos a ir comprobando de que tipo es usando las funciones de clasificación
        for i=1:N
          funcion_visualiza(I,Ietiq == i,[255,255,0],true); hold on;
          
          YTest = funcion_knn (ZRed(i,:), XOI, YOI, 9);
          
          if YTest == 1
              title("Circulo");
          elseif YTest == 2
              title("Cuadrado");
          else
              title("Triangulo");
          end
          
        end
        
    end
end


