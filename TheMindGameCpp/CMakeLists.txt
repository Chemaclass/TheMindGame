cmake_minimum_required(VERSION 3.17)
project(untitled)

set(CMAKE_CXX_STANDARD 20)

add_executable(untitled main.cpp TheMindGameLogic.cpp TheMindGameLogic.h TheMindGameLogicResult.cpp TheMindGameLogicResult.h Player.cpp Player.h Card.cpp Card.h)