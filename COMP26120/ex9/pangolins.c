#include <stdio.h>
#include <string.h>
#include <strings.h>
#include <stdlib.h>
#include <stdio.h>
#include <assert.h>

#define REM_NEWLN(str) if (str[strlen(str)-1]=='\n') str[strlen(str)-1] = '\0'
#define NODE_NAME_LEN 100

char* question_start = "question: ";
char* object_start = "object: ";

enum node_type { TYPE_QUESTION, TYPE_OBJECT };

typedef struct node {
  enum node_type type;
  union {
    char* object_name;
    char* question;
  }data;
  struct node *yes_ptr; // only NULL for objects
  struct node *no_ptr; // only NULL for objects
} Node;

void treePrint(Node *ptr, FILE* output){
  if (ptr == NULL)
    return;
  else {
    if (ptr->type==TYPE_QUESTION) {
      if(output==NULL)
        printf("question: %s\n", ptr->data.question);
      else
        fprintf(output, "question: %s\n", ptr->data.question);

      //now print the yes and no subtrees:
      treePrint(ptr->yes_ptr, output);
      treePrint(ptr->no_ptr, output);
    } else { // ptr is an object
      if(output==NULL)
        printf("object: %s\n", ptr->data.object_name);
      else
        fprintf(output, "object: %s\n", ptr->data.object_name);
    }
  }
}

void freeTree(Node *ptr){
  if (ptr==NULL) return;

  if (ptr->type==TYPE_OBJECT) {
    if(ptr->data.object_name!=NULL)
      free(ptr->data.object_name);
  }else if (ptr->type==TYPE_QUESTION){
    free(ptr->yes_ptr);
    free(ptr->no_ptr);
    free(ptr->data.question);
  }
  free (ptr);
}

/**
 * nodeRead. populates a Node with the contense
 */
void nodeRead(char* line,Node* ptr){
  if(ptr==NULL || line==NULL) return;
  if(strncmp(question_start, line, strlen(question_start)) == 0){
    REM_NEWLN(line);
    ptr->type = TYPE_QUESTION;
    char* question = malloc(sizeof(char) * strlen(line));
    assert(question!=NULL);
    strcpy(question,line+strlen(question_start));
    ptr->data.question = question;
  }else if (strncmp(object_start, line, strlen(object_start)) == 0){
    REM_NEWLN(line);
    ptr->type = TYPE_OBJECT;
    char* object = malloc(sizeof(char) * strlen(line));
    assert(object!=NULL);
    strcpy(object,line+strlen(object_start));
    ptr->data.object_name = object;
  }else{ // error :(
    ptr = NULL;
  }
}

Node* treeRead(FILE* treeInput) {
  //read the next line of input
  char line[NODE_NAME_LEN+1]="";
  fgets(line,NODE_NAME_LEN,treeInput);

  if (line ==NULL || strlen(line)==0) // i.e. no input
    return NULL;
  else {
    Node* ptr = malloc(sizeof(Node));
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

Node* createNode(enum node_type newType){
  Node* new = malloc(sizeof(Node));
  new->type = newType;
  if(new->type==TYPE_OBJECT)
    new->data.object_name =(char*) malloc(sizeof(char)*(NODE_NAME_LEN+1));
  else
    new->data.question =(char*) malloc(sizeof(char)*(NODE_NAME_LEN+1));
  return new;
}

/**
 * readYesNo. returns 1 for yes 0 for no and re trys if not sure
 */

int readYesNo(){
  char answer[10]="";
  fseek(stdin,0,SEEK_END);
  fgets(answer,10,stdin);
  REM_NEWLN(answer);
  if(strcasecmp(answer,"yes")==0 || strcasecmp(answer,"y")==0 || strcasecmp(answer,"sure")==0 || strcasecmp(answer,"youbetcha")==0)
    return 1;
  else if(strcasecmp(answer,"no")==0 || strcasecmp(answer,"n")==0 || strcasecmp(answer,"nope")==0 || strcasecmp(answer,"nowayhose")==0)
    return 0;
  else {
    printf("\n(yes/no)?>");
    return readYesNo();
  }
}

void pangolins(){

  //initialise the tree // first version: just one object, a pangolin
  FILE* dataFile = fopen("pangolins.dat","r");
  Node* root=NULL;
  // try load from file
  if(dataFile!=NULL){// error opening file so start with just a pangolin
    root = treeRead(dataFile);
    fclose(dataFile);
  }

  // if null then give default
  if(root==NULL){
    root = createNode(TYPE_OBJECT);
    strcpy(root->data.object_name, "pangolin");
    assert(root!=NULL);
  }



  // first version: play just one round
  Node* current_node = root;
  Node** parent_child_pointer = &root;// pointer to the (parent->child pointer)
  int finished = 0;
  while (!finished) {
    if (current_node->type==TYPE_OBJECT) { // object node
      printf("Is it a %s?\n>",current_node->data.object_name);
      if (readYesNo()){
        printf("Good. That was soooo easy!\n");
      } else {
        Node *new_question_node = createNode(TYPE_QUESTION);
        Node *new_object_node = createNode(TYPE_OBJECT);

        printf("Oh. Well you win then -- What were you thinking of?\n>");

        fgets(new_object_node->data.object_name,NODE_NAME_LEN,stdin);
        REM_NEWLN(new_object_node->data.object_name);

        printf("Please give me a question about %s, so I can tell the difference between %s and a %s\n>",
               new_object_node->data.object_name,new_object_node->data.object_name,current_node->data.object_name);

        fgets(new_question_node->data.question,NODE_NAME_LEN,stdin);
        REM_NEWLN(new_question_node->data.question);

        printf("What is the answer for %s?\n>",new_object_node->data.object_name);
        //insert the new object-name and question into the tree
        if(readYesNo()){
          new_question_node->yes_ptr = new_object_node;
          new_question_node->no_ptr = current_node;
        }else{
          new_question_node->no_ptr = new_object_node;
          new_question_node->yes_ptr = current_node;
        }
        // set pointer to new child node; used to shift around later
        *parent_child_pointer = new_question_node;
      }
      // finished
      // write new pangolin.dat
      dataFile = fopen("pangolins.dat","w");
      treePrint(root, dataFile);

      finished = 1;
    } else { // question node
      printf("%s?\n>",current_node->data.question);
      if(readYesNo()){
        parent_child_pointer = &(current_node->yes_ptr);
        current_node = current_node->yes_ptr;
      }else{
        parent_child_pointer = &(current_node->no_ptr);
        current_node = current_node->no_ptr;
      }
    }
  }
  freeTree(root);
  printf("\n");
}

int main(int argv, char *argc[]){
  pangolins();
}
