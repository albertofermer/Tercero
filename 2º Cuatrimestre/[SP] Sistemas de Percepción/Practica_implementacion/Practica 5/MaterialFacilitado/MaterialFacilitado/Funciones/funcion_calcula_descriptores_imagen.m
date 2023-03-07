function XImagen=funcion_calcula_descriptores_imagen(IEtiq,N)

XImagen=zeros(N,23);

for i=1:N
Ibin=IEtiq==i;

stats=regionprops(Ibin,'Eccentricity','Solidity','EulerNumber');
excentricidad=cat(1,stats.Eccentricity);
solidez=cat(1,stats.Solidity);
numEuler=cat(1,stats.EulerNumber);

XImagen(i,1)=funcion_calcula_compacticidad(Ibin);
XImagen(i,2)=excentricidad;
XImagen(i,3)=solidez;
XImagen(i,4)=Funcion_Calcula_Extent_SinRotacion(Ibin);
XImagen(i,5)=Funcion_Calcula__Extent(Ibin);

Hu=Funcion_Calcula_Hu(Ibin)';
for j=1:7
XImagen(i,j+5)=Hu(1,j);
end

DF=Funcion_Calcula_DF(Ibin,10)';

for k=1:10
XImagen(i,k+12)=DF(1,k);
end


XImagen(i,23)=numEuler;

end

end