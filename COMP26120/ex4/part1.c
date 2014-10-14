#include <stdio.h>
#define arraySize(a) (sizeof(a)/sizeof(a[0]))

int findLargest(int *numbers,int count, int* largestIndex);
void test(int *numbers, int count);

int main(void){
  int numbers[] = {1,2,3,54,45,32,2,4,4,6,4,55,66,109,456,2,3};
  int count = arraySize(numbers);
  test(numbers,count);

  int nonumbers[] = {};
  int nonumbersCount = arraySize(nonumbers);
  test(nonumbers,nonumbersCount);

  int negative[] = {-5,-23,-10,-234,-7};
  int negativeCount = arraySize(negative);
  test(negative,negativeCount);


  int lots[] = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
        ,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2};
  int lotsCount = arraySize(lots);
  test(lots,lotsCount);


  int one[] = {69};
  int oneCount = arraySize(one);
  test(one,oneCount);
  return 0;
}

void test(int *numbers, int count){
  int largestIndex;
  if(!findLargest(numbers,count,&largestIndex)){
    printf("no numbers\n");
    return;
  }
  
  char* output = "The largest number is %d at index %d\n";
  printf(output,numbers[largestIndex],largestIndex);
}

int findLargest(int *numbers,int count, int* largestIndex){
  if (count < 1) return 0;
  int max = numbers[0];
  *largestIndex = 0;
  for (int i = 0;i<count;i++)
    if (max < numbers[i]){ 
      max = numbers[i];
      *largestIndex = i;
    }
  return -1;
}
