

#ifdef DICT_TREE

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#define __USE_BSD
#include <string.h>

#include "speller.h"
#include "dict.h"

#define MAX(a,b) (a<b?a:b)

typedef struct node * tree_ptr;
struct node {
  Key_Type element; // only data is the key itself
  tree_ptr left, right;
  uint height; // the height of this tree
};

struct table {
  tree_ptr head; // points to the head of the tree
};

tree_ptr new_node(Key_Type key){
  tree_ptr node = (tree_ptr) malloc(sizeof(tree_ptr));
  node->left = NULL;
  node->right = NULL;
  node->height = 1;
  node->element = key;
  return node;
}


/**
 * gets the height of the tree pointed to by tree or 0 if the pointer does not
 * contain a tree
 */
uint height(tree_ptr tree){
  if(tree==NULL) return 0;
  else return tree->height;
}

/**
 * calculate the left or right balance of this node
 * calculated from the difference in heights of the two sub trees
 */
int balance(tree_ptr tree){
  if(tree==NULL) return 0;
  return height(tree->left) - height(tree->right);
}

/**
 * rightRotate
 * rotate the tree right for the node n
 */
struct node *rightRotate(struct node *n)
{
  struct node *nleft = n->left;
  struct node *nleftr = nleft->right;

  // change ptrs
  nleft->right = n;
  n->left = nleftr;

  // recalculate height
      n->height = MAX(height(n->left),     height(n->right)    )+1;
  nleft->height = MAX(height(nleft->left), height(nleft->right))+1;

  return nleft;
}
/**
 * leftRotate rotates the curent node n left
 **/
struct node *leftRotate(struct node *n)
{
  struct node *nright = n->right;
  struct node *nrightleft = nright->left;

  // change ptrs
  nright->left = n;
  n->right = nrightleft;

  // recalculate height
  n->height      = MAX(height(n->left),      height(n->right)     )+1;
  nright->height = MAX(height(nright->left), height(nright->right))+1;

  return nright;
}

Table initialize_table() {
  Table table;
  table->head = NULL;
  check(table->head);
  return table;
}

tree_ptr insert_node();

Table insert(Key_Type newKey, Table table){
  tree_ptr node = table->head;
  insert_node(newKey,node);
  return table;
}

int cmp(Key_Type a, Key_Type b){
  return strcmp(a, b);
}

tree_ptr insert_node(Key_Type newKey, tree_ptr node) {
  //base case if we are trying to insert into an empty tree
  //return the new node
  if (node == NULL){ return new_node(newKey); }

  //recursive case if not empty then work out which child to process next
  if (newKey < node->element)
    node->left  = insert_node(newKey,node);
  else
    node->right = insert_node(newKey,node);

  // first recalculate height for the new tree
  // recalculate height
  node->height = MAX(height(node->left), height(node->right)) + 1;

  // after insert has completed there are 4 cases if the resulting
  // tree is imbalanced:

  // find the balance of the current subtree
  int newbalance = balance(node);


  // decide if the tree is balanced and if so which case it is

  // Left left case
  if (newbalance > 1 && cmp(newKey, node->left->element) < 0)
    return rightRotate(node);

  // Right right case
  if (newbalance < -1 && cmp(newKey, node->right->element) > 0)
    return leftRotate(node);

  // Left right case
  if (newbalance > 1 && cmp(newKey, node->left->element) > 0 )
  {
    node->left = leftRotate(node->left);
    return rightRotate(node);
  }

  // Right left case
  if (newbalance < -1 && cmp(newKey, node->right->element) < 0)
  {
    node->right = rightRotate(node->right);
    return leftRotate(node);
  }

  return node;
}

Boolean find_node(tree_ptr node,Key_Type key);

Boolean find(Key_Type key, Table table) {
  return find_node(table->head, key);
}

/**
 * recursivley find key if found retrun true else false
 */
Boolean find_node(tree_ptr node,Key_Type key){
  if(node==NULL) return FALSE;
  if(find_node(node->left,key)) return TRUE;
  if(find_node(node->right,key)) return TRUE;
  return FALSE;
}

void print_table_node(tree_ptr node);
/**
 * print table in left inorder
 */
void print_table(Table table) {
  print_table_node(table->head);
}

void print_table_node(tree_ptr node){
  if(node==NULL) return;
  print_table_node(node->left);
  printf("node: %s", node->element);
  print_table_node(node->right);
}

//TODO: dunno
void print_stats(Table table) {
}

#endif