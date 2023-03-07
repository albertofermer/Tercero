load('./Variables Requeridas/XY_Verde_Reducido');

addpath('./Funciones')
  
    representa_datos_color_seguimiento_fondo(X_Verde,Y_Verde);
    xlabel('Rojo','Color','r'); ylabel('Verde','Color','g'); zlabel('Azul','Color','b'); 
    axis([0 255 0 255 0 255]);   
   
rmpath('./Funciones'); 