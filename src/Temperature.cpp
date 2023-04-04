#include <iostream>
#include <cstdio>
#include "Sensor.hpp"
#include "Temperature.hpp"
using namespace std;

Temperature::Temperature()
{
    int value = aleaGenVal();
}

