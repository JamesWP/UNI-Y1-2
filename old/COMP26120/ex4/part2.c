#include <stdio.h>
#include <stdlib.h>

typedef enum b_color {W,B} b_color;

b_color* genBoard(int width, int height);
void initBoard(b_color* board, int width, int height);
void printBoard(b_color* board, int width, int height);
void test(int width, int height);

int main(void){
  test(10,10);
  test(5,5);
  test(6,1);
  test(1,6);
  test(-1,1);
  test(0,1);
}

void test(int width, int height){
  b_color *board = genBoard(width,height); 
  if(board==NULL){
    printf("invalid arguments\n");
    return;
  }
  initBoard(board,width,height);
  printBoard(board,width,height);
  free(board);
}

b_color* genBoard(int width, int height){
  if(height<0) return NULL;
  if(width<0) return NULL;
  return (b_color*) malloc(sizeof(b_color) * width * height);
}

void initBoard(b_color* board, int width, int height){
  for(int y = 0;y<height;y++)
    for(int x = 0;x<width;x++){
      board[x + width*y] = (x+y)%2==0?W:B;
    }
}

void printBoard(b_color* board, int width, int height){
  for(int y = 0;y<height;y++){
    for(int x = 0;x<width;x++)
      switch(board[x + width*y]){
      case W:
        printf("[]");
        break;
      case B:
        printf("##");
        break;
      default:
        printf("  ");
      }
    printf("\n");
  }
  printf("\n");
}
