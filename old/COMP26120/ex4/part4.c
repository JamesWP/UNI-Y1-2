#include <stdio.h>
#include <string.h>

float c2f (float c);
float f2c (float f);

int main(int argv, char *argc[]){

  if(argv != 3){
    printf("invalid arguments");
    return 1;
  } 
  float temp,convert;
  sscanf(argc[2],"%f",&temp);
  if(strcmp(argc[1],"-f") == 0){
    convert = f2c(temp);
  }else if(strcmp(argc[1],"-c") == 0){
    convert = c2f(temp);
  }else{
    printf("invalid option");
    return 1;
  }
  
  printf("%3.2f converted is %3.2f\n",temp,convert);
  
  return 0;
}

float c2f(float c){
  return 9*c/5 + 32;
}

float f2c(float f){
  return (f-32)/9*5;
}
