#include <stdio.h>
#include <stdlib.h>
#define MAX 2000000000
#define BASE 10

void radixsort(int *a, int n)
{
  int i, b[MAX], m = a[0], exp = 1;
 
  //Get the greatest value in the array a and assign it to m
  for (i = 1; i < n; i++)
  {
    if (a[i] > m)
      m = a[i];
  }
 
  //Loop until exp is bigger than the largest number
  while (m / exp > 0)
  {
    int bucket[BASE] = { 0 };
 
    //Count the number of keys that will go into each bucket
    for (i = 0; i < n; i++)
      bucket[(a[i] / exp) % BASE]++;
 
    //Add the count of the previous buckets to acquire the indexes after the end of each bucket location in the array
    for (i = 1; i < BASE; i++)
      bucket[i] += bucket[i - 1]; //similar to count sort algorithm i.e. c[i]=c[i]+c[i-1];
 
    //Starting at the end of the list, get the index corresponding to the a[i]'s key, decrement it, and use it to place a[i] into array b.
    for (i = n - 1; i >= 0; i--)
      b[--bucket[(a[i] / exp) % BASE]] = a[i];
 
    //Copy array b to array a
    for (i = 0; i < n; i++)
      a[i] = b[i];
 
    //Multiply exp by the BASE to get the next group of keys
    exp *= BASE;
  }
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

	printf("before");
	radixsort(numbers,count);

	printf("after");
  
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

