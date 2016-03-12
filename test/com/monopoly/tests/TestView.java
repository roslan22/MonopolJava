/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monopoly.tests;

import com.monopoly.view.View;

/**
 *
 * @author Ruslan
 */
public class TestView {
    View view = new View();
            
    public void testView()
    {
        System.out.println(view.getHumanPlayersNumber());
        System.out.println(view.getComputerPlayersNumber());
        
    }
}
