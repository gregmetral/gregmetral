#include <iostream>
#include <cstdio>
#include "Scheduler.hpp"
#include "Server.hpp"


using namespace std;

Server::Server()
{
    fstream fio;
    scheduler = Scheduler();
    scheduler.mySleep(1000);
    fio.open("Humidity.txt", ios::trunc);
    fio.close();
    fio.open("Temperature.txt", ios::trunc);
    fio.close();
    fio.open("Pression.txt", ios::trunc);
    fio.close();
    fio.open("Light.txt", ios::trunc);
    fio.close();

    while(true)
    {
        consoleWrite(scheduler.value_humidity, scheduler.value_temperature, scheduler.value_pression, scheduler.value_light);
        fileWrite(scheduler.value_humidity, scheduler.value_temperature, scheduler.value_pression, scheduler.value_light);
        scheduler.mySleep(1000);
    }
}

void Server::consoleWrite(int Humidity, int Temperature, int Pression, int Light)
{
    cout << "humidity : " << Humidity << endl
         << "temperature : " << Temperature << endl
         << "pression : " << Pression << endl
         << "light : " << Light << endl;
}

void Server::fileWrite(int Humidity, int Temperature, int Pression, int Light)
{
    fstream fio;
    fio.open("Humidity.txt", ios::out | ios::in);
    fio << "humidity : " << Humidity << endl;
    fio.close();

    fio.open("Temperature.txt", ios::out | ios::in);
    fio << "temperature : " << Temperature << endl;
    fio.close();

    fio.open("Pression.txt", ios::out | ios::in);
    fio << "pression : " << Pression << endl;
    fio.close();

    fio.open("Light.txt", ios::out | ios::in);
    fio << "light : " << Light << endl;
    fio.close();
}