function [espacioccas,Jespacioccas] = funcion_selecciona_vector_ccas(XoI,YoI,dim)
  
c = combnk(1:22,dim);
[nf,nc] = size(c);

Jaux = zeros(nf,1);
    for i=1:nf
        espacio = c(i,:);
        X = XoI(:,espacio);
        Jaux(i,1) = indiceJ(X,YoI);
    end  
espacioccas = c(Jaux>=0.1,:);
[Jespacioccas,indMaximo] = max(Jaux);

espacioccas = c(indMaximo,:);
    
end