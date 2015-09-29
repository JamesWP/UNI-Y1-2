#include <ctype.h>
#include <stdio.h>

#define BUFFER_LEN 80
#define READ_FORMAT "%80s"

int main(void){
	int total = 2;
	int upper = 1;
  int lower = 1;
  char infile[BUFFER_LEN], outfile[BUFFER_LEN]; 
	
  printf("Please enter the infile: "); 
	scanf(READ_FORMAT,infile);
	printf("Please enter the outfile: ");
	scanf(READ_FORMAT,outfile);

	FILE *in = fopen(infile,"r");
  if (in == NULL){
		perror("File input cannot be opened :( ");
		return 1;
	}

	FILE *out = fopen(outfile,"w");
	if(out == NULL){
		perror("File output cannot be opened :( ");
    return 2;
	}

  char ch;
	while((ch = getc(in))!=EOF){
		if(isupper(ch)){
			lower++;
      putc(tolower(ch),out);
		}else if (islower(ch)){
			upper++;
			putc(toupper(ch),out);
		}else putc(ch,out);
		total++;
	}
  	
  fclose(in);

	fprintf(out,"Read %d characters in total, \
%d converted to upper-case, %d to lower-case\n",total,upper,lower);
	fclose(out);
  
return 0;
}
