function representa_datos_color_seguimiento(X,Y)

ValoresColorR = X(Y==1,1);
ValoresColorG =X(Y==1,2);
ValoresColorB =X(Y==1,3);


  
    plot3(ValoresColorR,ValoresColorG,ValoresColorB,'.r'), grid on;
    xlabel('Rojo','Color','r'); ylabel('Verde','Color','g'); zlabel('Azul','Color','b'); 
    axis([0 255 0 255 0 255]);
end