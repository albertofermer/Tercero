program Edicion; 

const 
	np1=5; 
	np2=5;  

{Definicion del monitor}
monitor m;

	export
		entraP1, entraP2, saleP1, saleP2;
	var
		libreLaser,libreEscaner, libreTinta: boolean;
		esperaLaser, esperaEscaner, esperaTinta: condition;
		colaEscaner, colaLaser, colaTinta:integer;

	procedure entraP1(id:integer, var coge:integer);
	var
		coge:integer;
	begin
		if libreLaser then coge:=1
		else 
			if libreTinta then coge:=2
			else
				if colaLaser<=colaTinta then begin
					colaLaser:=colaLaser+1;
					delay(esperaLaser);
					colaLaser:=colaLaser-1;
					coge:=1;
				end
				else begin
					colaTinta:=colaTinta+1;
					delay(esperaTinta);
					colaTinta:=colaTinta-1;
					coge:=2;
				end;
		if coge=1 then libreLaser:=false;
		else libreTinta:=false;

		writeln('-->Proceso tipo P1 ',id,' usando ',coge);
	end; 

	procedure saleP1(id:integer, coge:integer);
	begin
		writeln('<--Proceso tipo P1 ',id,' FIN ',coge);
		if coge=1 then 	begin
			libreLaser:=true;
			resume(esperaLaser);
		end
		else begin
			libreTinta:=true;
			resume(esperaTinta);
		end	
	end; 

	procedure entraP2(id:integer);
	begin
		if not libreEscaner then delay(esperaEscaner);
		libreEscaner:=false;
		
		if not libreTinta then begin
			colaTinta:colaTinta+1;
			delay(esperaTinta);
			colaTinta:=colaTinta-1;
		end;
		libreTinta:=false;
		
		writeln('----->Proceso tipo P2 ',id,' usando recursos');
	end; 

	procedure saleP2(id:integer);
	begin
		libreEscaner:=true;	
		resume(esperaEscaner);
		libreTinta:=true;
		resume(esperaTinta);
	end; 


	begin 
		libreLaser=true;
		libreEscaner=true;
		libreTinta=true;
		colaLaser=0;
		colaEscaner=0;
		colaTinta=0;
	end; 
(* Fin del monitor *)

process type P1(id:integer); 
var coge:integer;
begin 
	repeat	 
		m.entraP1(id,coge);
		writeln('Proceso tipo P1 ',id,' accediendo'); 
		m.saleP1(id,coge);
	forever 
end; 

process type P2(id:integer); 
begin 
	repeat 
		m.entraP2(id);
		writeln('Proceso tipo P2 ',id,' accediendo'); 
		m.saleP2(id);
	forever 
end; 

var 
   i,j: integer; 
   PR1: array[1..np1] of P1; 
   PR2: array[1..np2] of P2; 
   
begin 
  cobegin 
    for i := 1 to np1 do PR1[i](i); 
    for j := 1 to np2 do PR2[j](j); 
  coend 
end. 

