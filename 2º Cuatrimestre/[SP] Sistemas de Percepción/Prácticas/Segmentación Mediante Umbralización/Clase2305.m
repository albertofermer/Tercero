%% Descriptores Matemáticos 
%   -  Descriptores de Contorno: D. Fourier 
%   -  Descriptores de Región: D. topológicas
% 
% 
% Ib = Ietiq==1;
% m = Funcion_Calcula_Hu(Ib)
% 
% Recorrer las etiquetas de los objetos y aplicar la
% función calcula hu
% 
% 
% 
% Funcion_Representa_Datos(X,Y,espacioCaracteristicas,nombresProblema)
% espaciodeCaracteristicas puede ser 2D o 3D. plot o plot3.
% 
% Guarda el conjunto de datos XY
%
%% FASE 2: Programación de la función Reconoce_Objetos
% funcion_calcula_Hu_objetos_imagen(Ietiq,N)
%   Calcula los 7 momentos de Hu para cada objeto de la imagen.
% 
%% Descriptor EXTENT (Extensión): 
% Se implemeta con regionprops.
%   Calcular Bounding Box: 
%       1. find(Ib) devuelve las filas y las columnas de 
%           objeto de la imagen binaria.
%       2. Sacar el valor mínimo de las columnas y de las filas 
%           ((fmax+0.5)-(fmin-0.5)) * ((c_max+.5)-(c_min-.5)) = area rectangulo.
% 
%       3. Extension = numPixObj/numPixBoundingBox 
% 
%   stats = regionprops(Ib ó Ietiq,'Extent')
%   cat(1,stats.Extent).
% 
%% Programar un descriptor basado en la Extension que sea invariante a la rotación
% 
%   funcion_calcula_extent(); 
%       rota el objeto de 5 en 5 grados.
%       Por cada rotacion calculamos el extent y nos
%       quedamos con el máximo valor.
% 
% 
%% Lectura automatizada de imágenes:
%
% numClases 
% numImagenesPorClase
% 
% for i=1:numClases
%   for j=1:numImagenesPorClase
%       nombreImagen = [nombres(i), num2str(j,'%02d') '.jpg']
%       I = imread(nombreImagen)
%   end
% end
