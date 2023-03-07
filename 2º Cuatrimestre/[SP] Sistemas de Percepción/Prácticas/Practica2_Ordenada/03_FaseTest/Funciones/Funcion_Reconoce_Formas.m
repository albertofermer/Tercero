function Funcion_Reconoce_Formas(Nombre)

%% 1.- AÑADIR PATHS A LAS IMÁGENES DE TEST Y AL DIRECTORIO DONDE ESTÉN LAS
%% FUNCIONES QUE SE UTILICEN
    addpath('../../1. Generación de Patrones de Reconocimiento/ImagenesPractica5/Test/')
    addpath('../../1. Generación de Patrones de Reconocimiento/Funciones/')

    I = imread(Nombre);

    % Binariazamos la imagen, eliminamos regiones ruidosas y la
    % etiquetamos.

    umbral = 255*graythresh(I); % Otsu.
    Ib = I < umbral;
    [IbinFiltrado,Ietiq] = funcion_elimina_regiones_ruidosas(Ib);
     N = size(unique(Ietiq),1)-1;
     XImagen = funcion_calcula_descriptores_imagen(Ietiq,N);

     
     X = XImagen;
     medias = mean(X);
    desv = std(X);
    Z = X;
    [numMuestras, numDescriptores] = size(Z);
    for i=1:numDescriptores-1 

        Z(:,i) = (X(:,i)-medias(i))/desv(i); % A cada valor se le resta su 
                                             % media y se divide por su 
                                             % desviacion tipica.

    end

       %%CLASIFICADOR:
       
       %%QDA 3a3:
       load('../02_FaseEntrenamiento/QDA-3a3/Circulo-Cuadrado-Triangulo/DatosGenerados/Entrenamiento_Circulo_Cuadrado_Triangulo.mat')
       nClases = size(d,2);
       
       
           Variables = []
            for i = 1:size(espacioCcas,2)
                char = strcat('x',int2str(i));
                var = sym(char,'real');

                Variables = [Variables;var];
            end
            
            YTest = zeros(size(Z,1),1);
            
            Datos = Z(:,espacioCcas);
          for i = 1:size(Z,1) %%Calculamos clase de cada dato.
              
              ecuacion= expand( subs( d',Variables,Datos(i,:)' ) );
              [~,claseMax]=max(ecuacion);
               YTest(i) = claseMax;
              
          end
       
       %%--------------------------MOSTRAR CADA OBJETO-----------------------------%%
        ObjetosSeparados = bwlabel(Ietiq,4);
     
     for i = 1:N
         
         funcion_visualiza_definitivo(I,ObjetosSeparados==i,[255 0 0],1);
         title("Reconocido : " + nombresProblema.clases(YTest(i)));
     
         
     end
end

