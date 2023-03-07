
%Ii es la imagen de entrada, puede ser color o escala de grises.
%Ib matriz binaria del mismo numero de filas que Ii, logical o double. 
%Color, es una matriz con 3 valores RGB de [0-255]
%flagRepresenta, si esta a true la funcion debe generar una representacion
%con la imagen de salida.
%Io imagen Imagen de salida en color. 

function Io=funcion_visualiza(Ii,Ib,Color,flagRepresenta)

[nf,nc,d]=size(Ii);

num=Ib==1;

if d==3
 Ir=Ii(:,:,1); Ir(num)=Color(1,1);
 Ig=Ii(:,:,2); Ig(num)=Color(1,2);
 Ib=Ii(:,:,3); Ib(num)=Color(1,3);

else
 Ir=Ii;  Ir(num)=Color(1,1);
 Ig=Ii; Ig(num)=Color(1,2);
 Ib=Ii; Ib(num)=Color(1,3); 
end

Io=cat(3,Ir,Ig,Ib);



 if flagRepresenta==true
     figure,imshow(Io);
 end


end