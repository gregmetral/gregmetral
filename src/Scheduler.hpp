#include <iostream>
#include <cstdio>
#include "Sensor.hpp"
#include "Humidity.hpp"
#include "Temperature.hpp"
#include "Light.hpp"
#include "Pression.hpp"

#ifndef SCHEDULER_H
#define SCHEDULER_H

class Scheduler 
{
    private :
        Sensor humidity;
        Sensor temperature;
        Sensor pression;
        Sensor light;
        int value_humidity;
        int value_temperature;
        int value_pression;
        int value_light;
        int seconds;
        friend class Server;
        void mySleep(int sleepMs);

    public :
        Scheduler();
};

#endif