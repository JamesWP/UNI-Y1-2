#include <stdio.h>

#define TOTCHAR 255

int main(int argv, char* argc[]){
  if(argv!=2) {
    printf("incorect arguments");
    return 1;
  }
  FILE *input = fopen(argc[1],"r");
  if(input==NULL){
    perror("input file");
    return 1;
  }
  int charcount[TOTCHAR];
  for (int i = 0;i<TOTCHAR;i++)charcount[i]=0;

  char ch;
  while( (ch = getc(input)) != EOF){
    charcount[(int)ch]++;
  }

  fclose(input);

  for (int chi = 0;chi<TOTCHAR;chi++){
    if(charcount[chi]>0)
      printf("%3d instances of character %#04x (%c)\n",charcount[chi]
        ,chi,chi);
  }

  return 0;
}
