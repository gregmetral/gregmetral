#include <iostream>
#include <cstdio>
#include <Windows.h>
#include "Scheduler.hpp"

#ifdef LINUX
#include <unistd.h>
#endif
#ifdef WINDOWS
#include <windows.h>
#endif

using namespace std;

Scheduler::Scheduler()
{
    humidity = Humidity();
    pression = Pression();
    temperature = Temperature();
    light = Light();

    while(true)
    {
        
        value_humidity = humidity.getData();
        value_pression = pression.getData();
        value_temperature = temperature.getData();
        value_light = light.getData();
        mySleep(1000);

    }
}

void Scheduler::mySleep(int sleepMs) //sleep windows + linux
{
    #ifdef LINUX
        usleep(sleepMs * 1000);   
    #endif
    #ifdef WINDOWS
        Sleep(sleepMs);
    #endif
}

