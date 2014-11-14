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
    
    int n=0,number;
    for(n=0;n<count;n++){
        
        if(fscanf(numberFile,"%d",&number)<0){
            fprintf(stderr, "Could not read number\n");
            exit(4);
        }
        if(number<0) {
            fprintf(stderr, "Could not read number it was negative\n");
            exit(4);
        }
        numbers[n] = number;
    }
    
    int i, j;
    int *ccount = malloc(sizeof(int) * count);
    for(i=0; i < count; i++)
    {
        ccount[i] = 0;
    }
    for(i=0; i < count; i++)
    {
        (ccount[numbers[i]])++;
    }
    for(i=0,j=0; i < count; i++)
    {
        for(; ccount[i]>0;(ccount[i])--)
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
    free(numbers);
}
