#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

char* question_start = "question:";
char* object_start = "object:";

enum node_type { TYPE_QUESTION, TYPE_OBJECT };

struct node {
  enum node_type type;
  union {
    char* object_name;
    char* question;
  };
  struct node *yes_ptr; // only NULL for objects
  struct node *no_ptr; // only NULL for objects
};

void treePrint(struct node *ptr) {
  if (ptr == NULL)
    return;
  else {
    if (ptr->type==TYPE_QUESTION) {
      printf("question: %s",ptr->question);
      //now print the yes and no subtrees:
      treePrint(ptr->yes_ptr);
      treePrint(ptr->no_ptr);
    } else { // ptr is an object
      printf("object: %s",ptr->object_name);
    }
  }
}

/**
 * nodeRead. populates a struct node with the contense
 */
void nodeRead(char* line,struct node* ptr){
  if(ptr==NULL || line==NULL) return;
  if(strncmp(question_start, line, strlen(question_start)) == 0){
    ptr->type = TYPE_QUESTION;
    char* question = malloc(sizeof(char) * strlen(line));
    assert(question!=NULL);
    strcpy(question,line+strlen(question_start));
    ptr->question = question;
  }else if (strncmp(object_start, line, strlen(object_start)) == 0){
    ptr->type = TYPE_OBJECT;
    char* object = malloc(sizeof(char) * strlen(line));
    assert(object!=NULL);
    strcpy(object,line+strlen(object_start));
    ptr->object_name = object;
  }else{ // error :(
    ptr = NULL;
  }
}

struct node* treeRead(FILE* treeInput) {
  //read the next line of input
  char* line;
  line = fgets(line,99,treeInput);

  if (line ==NULL || strlen(line)==0) // i.e. no input
    return NULL;
  else {
    struct node* ptr = malloc(sizeof(struct node));
    assert(ptr!=NULL);
    if (strncmp(question_start, line, strlen(question_start)) == 0) {
      //fill ptr with the question from the input line
      nodeRead(line, ptr);
      //now read the yes and no subtrees:
      ptr->yes_ptr = treeRead(treeInput);
      ptr->no_ptr= treeRead(treeInput);
    } else { // the line started with "object:"
      //fill ptr with the object-name from the input line
      nodeRead(line, ptr);
      ptr->yes_ptr = ptr->no_ptr = NULL;
    }
    return ptr;
  }
}

void pangolins(){

  //initialise the tree // first version: just one object, a pangolin
  FILE* dataFile = fopen("pangolins.dat","r");
  struct node* root;
  if(dataFile==NULL){// error opening file so start with just a pangolin  
    root = malloc(sizeof(struct node));
    root->type=TYPE_OBJECT;
    root->object_name = "pangolin";
    assert(root!=NULL);
  }else{
    root = treeRead(dataFile);
  }
  
  // first version: play just one round
  struct node* current_node = root;
  struct node** parent_child_pointer;
  int finished = 0;
  while (!finished) {
    if (current_node->type==TYPE_OBJECT) { // object node
      printf("Is it (a|an) %s?i\n",current_node->object_name);
      char *guess_result;
      fgets(guess_result,100,stdin);
      if (strcmp("yes",guess_result)==0) {
        printf("Good. That was soooo easy.");
      } else {
        printf("Oh. Well you win then -- What were you thinking of?\n>");
        struct node *new_question_node = malloc(sizeof(struct node));
        struct node *new_object_node = malloc(sizeof(struct node));
        new_object_node->type=TYPE_OBJECT;
        new_question_node->type=TYPE_QUESTION;
        fgets(new_object_node->object_name,100,stdin);
        printf("Please give me a question about %s, so I can tell the difference between %s and a %s\n>",
               new_object_node->object_name,new_object_node->object_name,current_node->object_name);
        fgets(new_question_node->question,100,stdin);
        printf("What is the answer for %s?\n>",new_object_node->object_name);
        char* new_obj_answer;
        fgets(new_obj_answer,100,stdin);

        //insert the new object-name and question into the tree
        *parent_child_pointer = new_question_node;
        if(strcmp(new_obj_answer,"yes")){
          new_question_node->yes_ptr = new_object_node;
          new_question_node->no_ptr = current_node;
        }else{
          new_question_node->no_ptr = new_object_node;
          new_question_node->yes_ptr = current_node;
        }
      }
      // finished
      // write new pangolin.dat
      // exit
    } else { // question node
            char *guess = malloc(sizeof(char)*100);
                  scanf("%99s",guess);

      ask the question
      set current node according to Yes/No response
    }
  }
}

int main(int argv, char *argc[]){

}
