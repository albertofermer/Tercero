function compacticidad=funcion_calcula_compacticidad(Ibinaria)

stats=regionprops(Ibinaria,'Area','Perimeter');
area=cat(1,stats.Area);
perimeter=cat(1,stats.Perimeter);

compacticidad=(perimeter^2)/area;


end