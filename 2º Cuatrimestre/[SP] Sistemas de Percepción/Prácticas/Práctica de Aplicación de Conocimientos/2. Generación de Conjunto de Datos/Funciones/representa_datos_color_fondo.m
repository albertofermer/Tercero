function representa_datos_color_fondo(X,Y)


ValoresFondoR = X(Y==0,1);
ValoresFondoG =X(Y==0,2);
ValoresFondoB =X(Y==0,3);
  
    plot3(ValoresFondoR,ValoresFondoG,ValoresFondoB,'.b'), grid on;
    xlabel('Rojo','Color','r'); ylabel('Verde','Color','g'); zlabel('Azul','Color','b'); 
    axis([0 255 0 255 0 255]);
end