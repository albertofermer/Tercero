function [matrizSalida] =  funcion_visualiza(Ii,Ib, Color, flagRepresenta)



%%Si la matriz es de intensidad.
if(size(Ii,3) == 1)



 R = Ii;
 G = Ii;
 B = Ii;
 

%%Matrices de colores.
R(Ib==1) = Color(1,1);
G(Ib==1) = Color(1,2);
B(Ib==1) = Color(1,3);


%%Colocamos todo en una misma matriz.

matrizSalida = [];
matrizSalida(:,:,1) = R;
matrizSalida(:,:,2) = G;
matrizSalida(:,:,3) = B;


else
    R = Ii(:,:,1);
    G = Ii(:,:,2);
    B = Ii(:,:,3);
    R(Ib==1) = Color(1,1);
    G(Ib==1) = Color(1,2);
    B(Ib==1) = Color(1,3);
    
    matrizSalida = [];
matrizSalida(:,:,1) = R;
matrizSalida(:,:,2) = G;
matrizSalida(:,:,3) = B;


end

if flagRepresenta == true
    s = uint8(matrizSalida);
    figure, imshow(s);
end


end

