// knapsack-util.c
// (C) Joshua Knowles 2010-2013
// for issues: email j.knowles@manchester.ac.uk

// set of functions that are used by two or more of the knapsack solution 
// methods: enum, branch-and-bound, dynamic programming and greedy

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>

extern FILE *fp;
extern int Nitems;
extern int Capacity;
extern int *item_weights;
extern int *item_values;
extern int *temp_indexes;  // a temporary list of indexes, eg. to allow sorting by value/weight ratio
extern int QUIET;


void read_knapsack_instance(char *filename);
void print_instance();
void sort_by_ratio();
int check_evaluate_and_print_sol(int *sol,  int *total_value, int *total_weight);
static int mycomp(const void *a, const void *b);

void read_knapsack_instance(char *filename)
{
  // Note: knapsack items in the input files are numbered from 1 to N. 
  // We shall use arrays of size N+1 to store the indexes, weights and values
  // of the items. So w[1] and v[1] store the weight and value of the first item.

  int i;
  if((fp=fopen(filename,"rb")))
    {
      fscanf(fp,"%d\n", &Nitems);  // read the number of items
    

      if(!(item_weights=(int *)malloc((Nitems+1)*sizeof(int))))
	{
	  fprintf(stderr,"Could not allocate memory for weights vector\n");
	  exit(1);
	}
      if(!(item_values=(int *)malloc((Nitems+1)*sizeof(int))))
	{
	  fprintf(stderr,"Could not allocate memory for values vector\n");
	  exit(1);
	}
      if(!(temp_indexes=(int *)malloc((Nitems+1)*sizeof(int))))
	{
	  fprintf(stderr,"Could not allocate memory for index vector\n");
	  exit(1);
	}

      for(i=1;i<=Nitems;i++)
	fscanf(fp,"%d %d %d\n", &temp_indexes[i], &item_values[i], &item_weights[i]);

      fscanf(fp,"%d\n", &Capacity);  // read the knapsack capacity
    }
  else
    {
      fprintf(stderr,"Could not open file %s. Exiting.\n", filename);
      exit(1);
    }
     
}

void print_instance()
{
  int i;
  printf("item\tW\tV\n");
  for(i=1;i<=Nitems;i++)
    printf("%d\t%d\t%d\n", temp_indexes[i], item_weights[i], item_values[i]);
  printf("%d\n", Capacity);
}

void sort_by_ratio()
{
  int i;
  // sort the item indexes
  qsort(&temp_indexes[1], Nitems, sizeof(int), &mycomp);

  // print out the sorted order as a check
  for(i=1;i<=Nitems;i++)
    {
      printf("%d\t%d\t", item_weights[temp_indexes[i]], item_values[temp_indexes[i]]);
      printf("%f\n", (double)item_values[temp_indexes[i]]/(double)item_weights[temp_indexes[i]]);
    }
}

static int mycomp(const void *a, const void *b)
{
  // compare value-to-weight ratios
  int ia,ib;
  ia=(*(int *)a);
  ib=(*(int *)b);
 
  if((double)item_values[ia]/(double)item_weights[ia] > (double)item_values[ib]/(double)item_weights[ib])
    return(-1);
  else if((double)item_values[ia]/(double)item_weights[ia] < (double)item_values[ib]/(double)item_weights[ib])
    return(1);
  else
    return(0);
      
}


int check_evaluate_and_print_sol(int *sol, int *total_value, int *total_weight)
{
  // This function prints out the items packed in a solution, in ascending order.
  // Since items may have been sorted in a different order (using temp_indexes), it first reverses this mapping


  // The vector sol is a binary vector of length Nitems, describing the items to be put in the knapsack.
  // The (global) temp_indexes array maps item i to temp_indexes[i].
  // So sol[i]=1 really means item temp_indexes[i] should be taken.
  // In order to print out the item numbers referred to by sol in ascending order, we
  // copy them accross to an auxiliary array "pack" and then print the
  // items in pack in ascending order. 
 

  int i; // index variable
  *total_value=0;  // total value packed
  *total_weight=0;  // total weight packed
  int pack[Nitems]; // auxiliary binary array to do reverse-mapping


  // First pass: unmap the mapping using pack[.]
  for(i=1;i<=Nitems;i++)
    {
      if(sol[i]==1)
	pack[temp_indexes[i]]=1;
      else
	pack[temp_indexes[i]]=0;
    }

  // Second pass: now print out item numbers of items to be packed in ascending order
  if(!(QUIET))
    printf("Pack items: ");
  for(i=1;i<=Nitems;i++)
    {
      if(pack[i]==1)
	{
	  if(!(QUIET))
	    printf("%d ", i);
	  *total_value+=item_values[i];
	  *total_weight+=item_weights[i];
	}
    }

  // Finally, print out the value, weight and Capacity, and feasibility
  if(!(QUIET))
    {
      if(*total_weight>Capacity)
	printf("\nvalue=%d weight=%d > Capacity=%d: Infeasible\n",*total_value,*total_weight,Capacity);
      else
	printf("\nvalue=%d weight=%d <= Capacity=%d: Feasible\n",*total_value,*total_weight,Capacity);
    }
  if(*total_weight>Capacity)
    return -1;  // return -1 for infeasible solutions
  else
    return 0; // return 0 for feasible solutions
     
}
