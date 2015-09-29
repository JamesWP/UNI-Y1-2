#include <stdio.h>
#include <stdlib.h>

/**
 * intComp: performs integer comparison on two pointers to ints
 */
int intComp (const void *a, const void *b){
  return ( *(int*)a - *(int*)b );
}

int main(int argv, char **argc){
  if(argv!=3){
    fprintf(stderr, "Invalid number of arguments, expected 2 got %d\n", argv);
    exit(1);
  }

  int count = atoi(argc[1]);
  if(count<=0){
    fprintf(stderr, "Invalid count must be greater than 0, got %d\n",count);
    exit(2);
  }

  FILE *numberFile = fopen(argc[2],"r");
  if(numberFile==NULL){
    perror("Could not open numbers file\n");
  }

  int *numbers = malloc(sizeof(int) * count);
  if(numbers==NULL){
    fprintf(stderr, "Could not make array\n");
    exit(3);
  }
	
	int n=0;
  for(n=0;n<count;n++){
    if(fscanf(numberFile,"%d",&numbers[n])<0){
      fprintf(stderr, "Could not read number\n");
      exit(4);
    }
  }

  qsort(numbers, count, sizeof(int), intComp);

  
  int ninetieth = (count+1)*0.9;
  int onebefore = numbers[ninetieth-1];

  while(numbers[ninetieth]==onebefore){
    ninetieth++;
    if(ninetieth>=count){
      fprintf(stderr,"Could not find ninetieth percentile\n");
      exit(5);
    }
  }

  printf("The ninetieth percentile is %d\n",numbers[ninetieth]);
  printf("at index %d\n",ninetieth);

  fclose(numberFile);
  free(numbers);
}

