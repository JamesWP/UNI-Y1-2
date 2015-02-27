
// greedy.c
// (C) Joshua Knowles 2010-2013
// j.knowles@manchester.ac.uk

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

FILE *fp;  // file pointer for reading the input files
int Capacity;     // capacity of the knapsack (total weight that can be stored)
int Nitems;    // number of items available
int *item_weights;  // vector of item weights
int *item_values;  // vector of item profits or values
int *temp_indexes;  // temporary item indexes for sorting items by value/weight
int QUIET=0; // this can be set to 1 to suppress output

extern void read_knapsack_instance(char *filename);
extern void print_instance();
extern void sort_by_ratio();
extern int check_evaluate_and_print_sol(int *sol,  int *total_value, int *total_weight);
void greedy();

int main(int argc, char *argv[])
{
  read_knapsack_instance(argv[1]);
  print_instance();
  greedy();
  return(0);
}

void greedy()
{
  int total_weight=0; // current total weight of the items in the knapsack
  int total_value=0; //  current total profit of the items in the knapsack
  int i=1;
  int solution[Nitems+1];

  sort_by_ratio(); // sort items in descending profit-to-weight ratio order

  /* ADD CODE HERE TO COMPUTE THE GREEDY SOLUTION */
  
  /* THE CODE SHOULD: take the items in descending 
     profit-to-weight ratio order (by using temp_indexes) and, 
     if an item fits, add it to the knapsack, and 
     do not stop at the first item that doesn't fit 
     - but keep going until all items have been tried */

  printf("The greedy solution - not necessarily optimal - is:\n");
  check_evaluate_and_print_sol(solution, &total_value, &total_weight);  

  /* NOTE: If the result you get when you use the check_ ...() function
           is not what you expected, it could be because you mapped
           to the sorted order TWICE.
           Use 
              solution[i]=1; 
           in order to "pack" the ith most value-dense item, 
           not solution[temp_index[i]];
  */

}
