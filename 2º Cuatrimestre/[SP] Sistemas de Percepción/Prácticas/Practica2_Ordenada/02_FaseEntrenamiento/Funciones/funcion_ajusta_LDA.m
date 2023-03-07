function [vectorMedias,mCovar,Ppriori] = funcion_ajusta_LDA(X,Y)

    
   clasesPosibles = unique(Y);


   Ppriori = zeros(size(clasesPosibles));

   
   vectorMedias = zeros(size(clasesPosibles,1), size(X,2))';%%lo quiero en clumnas.
   %Cargo los datos de las diferentes clases.
   contador = 1; %%En el caso de que las clases se salten el numero 1 por alguna razon.
   mCovarClases = [];
   nDatos = zeros(size(clasesPosibles,1));
   
   for i = 1:size(clasesPosibles)
       
       DatosClases = X(Y==clasesPosibles(i),:);
       vectorMedias(:,contador) = mean(DatosClases)';
       Ppriori(contador) = size(DatosClases,1)/size(X,1);
       nDatos(contador) = size(DatosClases,1);
       contador = contador +1;
       mCovarClases = cat(size(X,1),mCovarClases,cov(DatosClases));
       
   end
   
   
   
   %mCovUnica = (  (nClase1-1) * mCovar1 + (nClase2-1) * mCovar2  ) / (nTotal-2);
    
    
   mAcumulada = zeros(size(mCovarClases(:,:,1)));
   for i = 1:size(clasesPosibles,1)
       
       mAcumulada = (nDatos(i)-1) * mCovarClases(:,:,i) + mAcumulada;
       
   end
   
   mCovar = mAcumulada/(size(Y,1)-size(clasesPosibles,1));
   
end

