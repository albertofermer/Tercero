function IbFilt = funcion_elimina_regiones_ruidosas(Ib)
    stats = regionprops(Ib,"Area");
    areas = cat(1,stats.Area);
    area_mayor = max(areas);
    area_minima = round(area_mayor/5);
    IbFilt1 = bwareaopen(Ib,area_minima);
    [nf,nc]= size(Ib);
    area_minima = round((nf*nc)*0.001);
    IbFilt2 = bwareaopen(Ib,area_minima);
    IbFilt = and(IbFilt1,IbFilt2);
end