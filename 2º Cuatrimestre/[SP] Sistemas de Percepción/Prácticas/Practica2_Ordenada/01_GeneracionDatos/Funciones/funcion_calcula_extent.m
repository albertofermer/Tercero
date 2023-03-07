function [Extent_mayor] = funcion_calcula_extent(Matriz_Binaria)

Ibin = Funcion_Centra_Objeto(Matriz_Binaria);

Extent_mayor = 0;

for i = 0:5:355
    Irotada = imrotate(Ibin,5*i);%%Cuidado con la i.
    
   
    stats = regionprops(Irotada,'Extent');
    Extent = cat(1,stats.Extent);
    
    if Extent_mayor < Extent
        Extent_mayor = Extent;
    end
    

end



end

