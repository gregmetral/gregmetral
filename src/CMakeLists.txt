cmake_minimum_required(VERSION 3.23)

project(AP4A)

set(CMAKE_CXX_STANDARD 14)

set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "../src")

add_executable(main.cpp, Humidity.cpp, Humidity.hpp, Light.cpp, Light.hpp, Pression.cpp, Pression.hpp, Scheduler.hpp, Scheduler.cpp, Sensor.hpp, Sensor.cpp, Server.cpp, Server.hpp, Temperature.cpp, Temperature.hpp)