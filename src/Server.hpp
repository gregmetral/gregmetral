#include <iostream>
#include <cstdio>
#include <fstream>
#include "Scheduler.hpp"

#ifndef SERVER_H
#define SERVER_H

class Server 
{

    private :
        void fileWrite(int Humidity, int Temperature, int Pression, int Light);
        void consoleWrite(int Humidity, int Temperature, int Pression, int Light);
        Scheduler scheduler;
        
       

    public:
        Server(); //constructeur
        Server(const Server& server); //constructeur par reccopie
        Server& operator=(const Server& server); //affectation 
        ~Server(); //destructeur
};

#endif