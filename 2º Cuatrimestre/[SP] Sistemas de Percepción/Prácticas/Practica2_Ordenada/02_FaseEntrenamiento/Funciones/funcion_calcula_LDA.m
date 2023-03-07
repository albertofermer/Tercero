function [YQDA,d] = funcion_calcula_LDA(X_LDA,vectorMedias,mCov,Pprio,valoresClases)

YQDA = zeros(size(X_LDA,1),1);

X =[];
%%Variables simbolicas.
for i = 1:size(X_LDA,2)
    char = strcat('x',int2str(i));
    var = sym(char,'real');
    X = [X;var];
end


for i = 1:size(valoresClases,1)
    
    d(i)= expand(-0.5 * (X-vectorMedias(:,i))' * pinv(mCov) * (X-vectorMedias(:,i)) + log(Pprio(i)));   
    %  d(k) = expand(-1/2 * (X-M(:,k))' * pinv(mCovUnica) * (X-M(:,k)) + log(priori));
end

for i = 1:size(X_LDA,1)
    
    ecuacion= expand( subs( d',X,X_LDA(i,:)' ) );
    [~,claseMax]=max(ecuacion);
    YQDA(i) = valoresClases(claseMax);
    
    
end


end

