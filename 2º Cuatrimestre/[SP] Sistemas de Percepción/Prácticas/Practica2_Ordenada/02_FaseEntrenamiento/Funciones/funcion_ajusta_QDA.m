function [vectorMedias,mCovar,Ppriori] = funcion_ajusta_QDA(X,Y)

    
   clasesPosibles = unique(Y);


   Ppriori = zeros(size(clasesPosibles));

   
   vectorMedias = zeros(size(clasesPosibles,1), size(X,2))';%%lo quiero en clumnas.
   %Cargo los datos de las diferentes clases.
   contador = 1; %%En el caso de que las clases se salten el numero 1 por alguna razon.
   mCovar = [];
   for i = 1:size(clasesPosibles,1)
       
       DatosClases = X(Y==clasesPosibles(i),:);
       vectorMedias(:,contador) = mean(DatosClases)';
       Ppriori(contador) = size(DatosClases,1)/size(X,1);
       contador = contador +1;

       mCovar = cat(3,mCovar,cov(DatosClases));
   end
   
   
   
    
   
end

