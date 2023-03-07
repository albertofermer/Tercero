function Extent=Funcion_Calcula_Extent_SinRotacion(Matriz_Binaria)

    [f,c]=find(Matriz_Binaria);
    fmin=min(f);fmax=max(f);
    cmin=min(c);cmax=max(c);

    fmin=fmin-0.5;
    fmax=fmax+0.5;
    cmin=cmin-0.5;
    cmax=cmax+0.5;

    numPixBB=(cmax-cmin)*(fmax-fmin);
    numPixObjeto=sum(Matriz_Binaria(:));
    

Extent=numPixObjeto/numPixBB;


end