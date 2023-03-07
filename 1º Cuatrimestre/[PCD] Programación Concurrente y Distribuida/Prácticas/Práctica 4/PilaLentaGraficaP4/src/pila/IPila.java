/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pila;

/**
 *
 * @author Alberto Fernández
 */
public interface IPila {

    void Apila(Object elemento) throws Exception;

    Object Desapila() throws Exception;

    int GetNum();

    Object Primero() throws Exception;
    
}
