
// dynamic programming for 0/1 knapsack
// (C) Joshua Knowles, 2010-2013
// for issues: email j.knowles@manchester.ac.uk


#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>

FILE *fp;  // file pointer for reading the input files
int Capacity;     // capacity of the knapsack (total weight that can be stored)
int Nitems;    // number of items available
int *item_weights;  // vector of item weights
int *item_values;  // vector of item profits or values
int *temp_indexes;  // list of temporary item indexes for sorting items by value/weight
int QUIET=0; // this can be set to 1 to suppress output


// function prototypes
extern void read_knapsack_instance(char *filename);
int DP(int *v,int *wv, int n, int W, int *solution);
extern int check_evaluate_and_print_sol(int *sol,  int *total_value, int *total_weight);

int main(int argc, char *argv[1])
{
  int *solution;    // binary vector indicating items to pack
  int total_value, total_weight;  // total value and total weight of items packed

  read_knapsack_instance(argv[1]);

  if((solution = (int *)malloc((Nitems+1)*sizeof(int)))==NULL)
  {
    fprintf(stderr,"Problem allocating table for DP\n");
    exit(1);
  }

  DP(item_values,item_weights,Nitems,Capacity,solution);
  check_evaluate_and_print_sol(solution,&total_weight,&total_value);
  return(0);
}

#define V(a,b) INDEX(V,a,b)
#define keep(a,b) INDEX(keep,a,b)
#define INDEX(n,a,b) n[((a)*(W+1)+(b))]

int DP(int *v,int *wv, int n, int W, int *solution)
{
  // the dynamic programming function for the knapsack problem
  // the code was adapted from p17 of http://www.es.ele.tue.nl/education/5MC10/solutions/knapsack.pdf

  // v array holds the values / profits / benefits of the items
  // wv array holds the sizes / weights of the items
  // n is the total number of items
  // W is the constraint (the weight capacity of the knapsack)
  // solution: a 1 in position n means pack item number n+1. A zero means do not pack it.

  int *V, *keep;  // 2d arrays for use in the dynamic programming solution

  int K;

  // Dynamically allocate memory for variables V and keep
  V = (int *) malloc(sizeof(int)*(n+1)*(W+1));
  keep = (int *) malloc(sizeof(int)*(n+1)*(W+1));
  // keep[][] and V[][] are both of size (n+1)*(W+1)

  //  set the values of the zeroth row of the partial solutions table to zero
  for(int w=0;w<W+1;w++)
    V(0,w) = 0;

  // main dynamic programming loops , adding one item at a time and looping through weights from 0 to W
  for(int item=1;item<=n;item++)
  {
    for(int weight=0;weight<=W;weight++)
    {
      bool shouldUseItem = (wv[item] <= weight) && ( (v[item] + V(item-1,weight - wv[item])) > (V(item - 1, weight)) );

      if(shouldUseItem)
      {
        V(item,weight) = v[item] + V(item-1,weight - wv[item]);
        keep(item,weight) = 1;
      }else{
        V(item,weight) = V(item-1,weight);
        keep(item,weight) = 0;
      }
    }
  }

  // now discover which items were in the optimal solution
  K = W;

  for(int item=n;item>0;item--)
  {
    solution[item] = keep(item,K);
    if(solution[item])
    {
      K -= wv[item];
    }
  }

  return V(n,W);
}


