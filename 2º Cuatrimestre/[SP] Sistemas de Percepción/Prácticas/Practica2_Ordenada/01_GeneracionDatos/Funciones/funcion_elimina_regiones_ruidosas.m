

function [IbinFilt,Ietiq] = funcion_elimina_regiones_ruidosas(Ibin)
% COMPONENTE RUIDOSA:
% COMPONENTES DE MENOS DEL 0.1% DEL N�MERO TOTAL DE P�XELES DE LA IMAGEN
% O N�MERO DE P�XELES MENOR AL AREA DEL OBJETO MAYOR /5
% SE DEBE CUMPLIR CUALQUIERA DE LAS DOS CONDICIONES

%%Componentes de menos del 0.1% del numero total de pixeles:

    pixeles = size(Ibin,1)*size(Ibin,2);

    IbinFilt = bwareaopen(Ibin, round(0.001*pixeles),4); %Eliminar� todos los conjuntos de pixeles conectados menores al 0.1% del total.
    Ietiq = zeros(size(Ibin));
    %%Componentes de menos area que el objeto mayor/5.
    if sum(Ibin(:)) > 0
        Ietiq = bwlabel(IbinFilt);

        stats = regionprops(Ietiq,'Area');
        areas = cat(1,stats.Area);

        pixelesUmbral = floor(max(areas)/5);
        IbinFilt = bwareaopen(IbinFilt,pixeles);

    end

end

