#include <stdio.h>
#include <stdlib.h>



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

  FILE *numberFile;
  numberFile = fopen(argc[2],"r");
  if(numberFile==NULL){
    perror("Could not open numbers file\n");
    exit(2);
  }

  int *numbers = malloc(sizeof(int) * count);
  if(numbers==NULL){
    fprintf(stderr, "Could not make array\n");
    exit(3);
  }

  // read numbers into array
  int n=0,number;
  for(n=0;n<count;n++){
    if(fscanf(numberFile,"%d",&number)<0){
      fprintf(stderr, "Could not read number\n");
      exit(4);
    }
    numbers[n] = number;
  }

  // implementation of bucket sort
  int i, j;
  int *bucket = malloc(sizeof(int) * 9000000);
  if(bucket==NULL){
    fprintf(stderr, "Could not make array\n");
    exit(4);
  }
  // initialise data
  for(i=0; i < count; i++)
  {
    bucket[i] = 0;
  }
  // count freq of numbers
  for(i=0; i < count; i++)
  {
    (bucket[numbers[i]])++;
  }
  //insertion sort the numbers in the buckets
  for(i=0,j=0; i < count; i++)
  {
    for(; bucket[i]>0;(bucket[i])--)
    {
      numbers[j++] = i;
    }
  }

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
}
