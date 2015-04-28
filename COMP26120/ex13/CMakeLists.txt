cmake_minimum_required(VERSION 3.1)
project(ex13)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

add_executable(ex13 ${SOURCE_FILES} ex13.h graph.h graph_functions.c ex13.c ordered_queue.h ordered_queue.c dijkstra.h dijkstra.c)