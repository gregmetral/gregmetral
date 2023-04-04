#include <iostream>
#include <cstdio>
#include "Sensor.hpp"
#include "Humidity.hpp"
using namespace std;

Humidity::Humidity()
{
    int value = aleaGenVal();
}

