#include <iostream>
#include <cstdio>
class Humidity;
class Temperature;
class Pression;
class Light;
using namespace std;

#ifndef SENSOR_H
#define SENSOR_H

class Sensor 
{
    protected :
        int aleaGenVal(); //genere valuer aleatoire entre 1 et 10
        int value;
        void mySleep(int sleepMs);

    public :
        Sensor(); //constructeur
        Sensor(const Sensor& sensor); //constructeur par reccopie
        Sensor& operator=(const Sensor& sensor); //affectation 
        ~Sensor(); //destructeur
        int getData();
};

#endif