#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <inttypes.h>
#include <string.h>



FILE *fp;

typedef struct book{
    double rating;
    double price;
    double relevance;
    int ID;
}B;

B *list;

int read_file(char* infile, int N)
{
    int c;
    if((fp=fopen(infile, "rb")))
    {
        fscanf(fp, "%*s\t%*s\t%*s\t%*s\n");
        c=0;
        while((!feof(fp))&&(c<N))
        {
            fscanf(fp, "%lf\t%lf\t%lf\t%d\n", &list[c].rating,  &list[c].price, &list[c].relevance, &list[c].ID);
            c++;
        }
        fclose(fp);
    }
    else
    {
        fprintf(stderr,"%s did not open. Exiting.\n",infile);
        exit(-1);
    }
    return(c);
}

int comp_on_rating(const void *a, const void *b)
{
    if ((*(B *)a).rating < (*(B *)b).rating)
        return -1;
    else if ((*(B *)a).rating > (*(B *)b).rating)
        return 1;
    else
        return 0;
}

int comp_on_relev(const void *a, const void *b)
{
    if ((*(B *)a).relevance > (*(B *)b).relevance)
        return -1;
    else if ((*(B *)a).relevance < (*(B *)b).relevance)
        return 1;
    else
        return 0;
}

int comp_on_price(const void *a, const void *b)
{
    if ((*(B *)a).price < (*(B *)b).price)
        return 1;
    else if ((*(B *)a).price > (*(B *)b).price)
        return -1;
    else
        return 0;
}


/* Implementation of merge sort copied from url below
 * and used within the licence
 * http://rosettacode.org/wiki/Sorting_algorithms/Merge_sort#C
 */

// merge subroutine
void merge(B* list, uint64_t l_start, uint64_t l_end,
           uint64_t r_start, uint64_t r_end, int (*cmp)(const void *,const void *))
{
    // temporary list sizes
    uint64_t l_len = l_end - l_start;
    uint64_t r_len = r_end - r_start;
    
    // temporary lists for comparison
    B* l_half = malloc(l_len * sizeof(B));
    B* r_half = malloc(r_len * sizeof(B));
    
    uint64_t i = l_start;
    uint64_t l = 0;
    uint64_t r = 0;
    
    // copy values into temporary lists
    for (i=l_start; i < l_end; i++, l++) { l_half[l] = list[i]; }
    for (i=r_start; i < r_end; i++, r++) { r_half[r] = list[i]; }
    
    // merge the values back into positions in main list
    for (i=l_start, r=0, l=0; l < l_len && r < r_len; i++)
    {
        // if left value < r value, move left value
        if (cmp(&l_half[l],&r_half[r]) < 0) {
            list[i] = l_half[l];
            l++;
        }
        // else move right value
        else {
            list[i] = r_half[r];
            r++;
        }
    }
    
    // handle leftover values
    for ( ; l < l_len; i++, l++) { list[i] = l_half[l]; }
    for ( ; r < r_len; i++, r++) { list[i] = r_half[r]; }
    
    free(l_half);
    free(r_half);
}



// recursive mergesort
void mysort(uint64_t left, uint64_t right, B* list, int (*cmp)(const void *,const void *))
{
    // base case
    if (right - left <= 1) { return; }
    
    // get slice indices
    uint64_t l_start = left;
    uint64_t l_end = (left+right)/2;
    uint64_t r_start = l_end;
    uint64_t r_end = right;
    
    // recursive call on left half
    mysort(l_start, l_end, list, cmp);
    // recursive call on right half
    mysort(r_start, r_end, list, cmp);
    // merge sorted right and left halves back together
    merge(list, l_start, l_end, r_start, r_end, cmp);
}


void print_results(int N)
{
    int i;
    if((fp=fopen("top20.txt","w")))
    {
        for(i=N-1;i>=N-20;i--)
        {
            
            printf("%g %g %g %d\n", list[i].rating, list[i].price, list[i].relevance, list[i].ID);
            fprintf(fp, "%g %g %g %d\n", list[i].rating, list[i].price, list[i].relevance, list[i].ID);
            
        }
        fclose(fp);
    }
    else
    {
        fprintf(stderr,"Trouble opening output file top20.txt\n");
        exit(-1);
    }
    
}

void sortList(int func, int N){
  if(func==1)
    mysort(0,N,list,comp_on_rating);
  else if (func==2)
    mysort(0,N,list,comp_on_relev);
  else if (func==3)
    mysort(0,N,list,comp_on_price);
}


void user_interface(int N)
{
    // For Part 1 this function calls the sort function to sort on Price only
    //mysort(0, N, list, comp_on_price);
    
    // For Part 2 this function
    // (1) asks the user if they would like to sort their search results
    
    char sort[10];
    printf("Do you wish to sort your results? (yes,no)");
    
    scanf("%3s", sort);
    if(strcmp(sort,"yes")==0){
      // (2) asks for the most important field (or key), the next most etc
      int first, second, third;
      printf("Fields: 1=Rating, 2=Relevance, 3=Price\n");
      printf("Please enter your first field:");
      scanf("%d", &first);
      printf("\nPlease enter your second field:");
      scanf("%d", &second);
      printf("\nPlease enter your third field:");
      scanf("%d", &third);
      printf("Sorting on (%d,%d,%d)\n",first,second,third);
      // (3) calls your sort function
      sortList(third,N);
      sortList(second,N);
      sortList(first,N);
    }
   
}


int main(int argc, char *argv[])
{
    int N;
    
    if(argc!=3)
    {
        fprintf(stderr, "./exec <input_size> <filename>\n");
        exit(-1);
    }
    
    N=atoi(argv[1]);
    
    list = (B *)malloc(N*sizeof(B));
    
    N=read_file(argv[2], N);
    
    user_interface(N);
    
    print_results(N);
    
    return(0);
}



