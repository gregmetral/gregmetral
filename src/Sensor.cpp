#include <iostream>
#include <cstdio>
#include <Windows.h>
#include "Sensor.hpp"
#ifdef LINUX
#include <unistd.h>
#endif
#ifdef WINDOWS
#include <windows.h>
#endif

using namespace std;

Sensor::Sensor()
{
    while(true)
    {
        (*this).value = aleaGenVal();
        mySleep(1000);
    }

}

Sensor::Sensor(const Sensor& sensor)
{
    (*this).value = sensor.value;
}

Sensor& Sensor::operator=(const Sensor& other)
{
    (*this).value = other.value;
}

int Sensor::aleaGenVal()
{
    return rand()%11;
}

int Sensor::getData()
{
    return (*this).value;
}

void Sensor::mySleep(int sleepMs)
{
    #ifdef LINUX
        usleep(sleepMs * 1000);   
    #endif
    #ifdef WINDOWS
        Sleep(sleepMs);
    #endif
}

