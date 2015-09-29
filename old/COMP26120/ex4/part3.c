#include <stdio.h>
#include <string.h>

int main(int argv, char* argc[]){

  if (argv < 1) return 1;
  if (argv == 1) return 0;

  int max = strlen(argc[1]);
  int largestIndex = 1;
  for (int i = 1;i<argv;i++)
    if (max < strlen(argc[i])){ 
      max = strlen(argc[i]);
      largestIndex = i;
    }
  
  printf("%s\n",argc[largestIndex]);
  return 0;
}
