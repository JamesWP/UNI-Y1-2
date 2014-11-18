/* original: Graham Nov 2001,
    modified: Pete Dec 2010
 */

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#define __USE_BSD
#include <string.h>
#include <getopt.h>

#include "speller.h"
#include "dict.h"

// Set up default values
#define DEFAULT_DICT_FILE ("sample-dictionary")
#define DEFAULT_TABLE_SIZE 507

/* some useful code ***********************************************************/

char * prog_name;
void check (void * memory) { // check result from strdup, malloc etc.
  if (memory == NULL) {
    fprintf (stderr, "%s: out of memory\n", prog_name);
    exit (3);
  }
}

/* reading words from a file **************************************************/

#define WORD_SIZE (50)
static int line_count= 0, first_word_on_line= TRUE;
static void init_get_next_lower_word (void) {
  line_count= 0;
  first_word_on_line= TRUE;
}
static char * get_next_lower_word (FILE *source) {
  /* reads next word from source.
   * A word consists of a sequence of alphabetic characters.
   * Returns the word, or NULL if no further words can be found.
   * Converts upper-case letters to lower-case.
   */
  static char word [WORD_SIZE];
  char ch;
  int done= FALSE, word_len= 0;
  // on seeing a '\n', need to increment line_count at the *next* word,
  // even if the '\n' terminated *this* word,
  // so that line-numbers are reported correctly
  while (!done && fscanf (source, "%c", &ch)==1) {
    if (isalpha (ch)) { // || ch=='-') ?
      if (first_word_on_line) {
	line_count++; first_word_on_line= FALSE;
      }
      word[word_len++]= tolower(ch);
    } else { // non-alphabetic
      if (word_len > 0) { // non-alphabetic terminates the word
	word[word_len]= 0;
	done= TRUE ;
      }
      if (ch == '\n') { // Can't just add one to line count,
	                // since last word on line might be reported
	if (first_word_on_line) // already seen one '\n'
	  line_count++;
	first_word_on_line= TRUE;
      }
    }
  } //while
  if (word_len)
    return word;
  else
    return NULL;
}

/* program parameters *********************************************************/

int verbose= 0;
int mode= 0;
static char *dict_file_name;
static char *file_name;
static int table_size= DEFAULT_TABLE_SIZE;

static void usage() { // reports the usage of the program
  fprintf(stderr,
          "Usage: %s [-d dictionary] {-s table_size] [-m mode] [-v] [-h] text_file\n",
          prog_name);
  fprintf(stderr, "\ts: set table size to arg\n");
  fprintf(stderr, "\td: dictionary name (default %s)\n", DEFAULT_DICT_FILE);
  fprintf(stderr, "\tv: verbose - extra v's increase reporting level\n");
  fprintf(stderr, "\th: help - output this message\n");
  fprintf(stderr, "\ttext_file: file to spell-check\n");
  exit(1);
}

static int process_args (int argc, char *argv[]) {
  /* Processes all command line arguments using getopt.
   * (modified version of the code given in the man page)
   */
  if (argc < 2)
    usage ();
  while (1) {
    int c= getopt (argc, argv, "s:d:m:vh");
    if (c == -1)
      break;
    switch (c) {
    case 's': // set table size to arg
      table_size= atoi (optarg);
      break;
    case 'd': // dictionary name given
      check ((dict_file_name= strdup(optarg)));
      break;
    case 'm': // algorithm mode given
      mode= atoi(optarg);
      break;
    case 'v':
      verbose++; // multiple -v args will increase this
      break;
    case 'h': // Help!
      usage ();
      break;
    default: // report error
      printf ("didn't expect program parameter %c [0%o]\n", c, c);
      usage ();
    }
  }
  /* All being well we've only the file name left.
     We'll ignore any other args
   */
  if (optind < argc) {
    file_name= argv[optind];
  }
  else // no file name given
    usage();
  return (0);
}

/******************************************************************************/

static FILE* open_file (char *file_name) {
  FILE* text_file= fopen (file_name, "r");
  if (text_file == NULL) {
    fprintf(stderr, "%s: Can't open %s\n", prog_name, file_name);
    exit(3);
  }
  return text_file;
}

int main (int argc, char *argv[]) {
  Table table;
  FILE *dict_file, *text_file;
  int word_count= 0;
  char *word;

  prog_name= argv[0];
  dict_file_name= DEFAULT_DICT_FILE;
  process_args (argc, argv);

  if (verbose > 0) {
    fprintf (stderr,"Using table size %d\n", table_size);
    fprintf (stderr,"Using dictionary `%s'\n", dict_file_name);
    fprintf (stderr,"Checking text file `%s'\n", file_name);
    fprintf (stderr,"Using mode %d\n", mode);
  }

  dict_file= open_file (dict_file_name);
  text_file= open_file (file_name);

  table= initialize_table (table_size);

  if (verbose > 0)
    fprintf (stderr, "Reading dictionary\n");

  init_get_next_lower_word ();
  while ((word= get_next_lower_word (dict_file))) {
    table= insert (word, table);
    word_count++;
    if ((verbose > 0) && (word_count % 100 == 0)) {
      fprintf (stderr,".");
      fflush (stderr);
    }
  }

  if (verbose > 0)
    fprintf (stderr,"\nDictionary read\n");
  if (verbose > 1) { // call with option -vv to get this
    print_table (table);
    printf("Spellchecking:\n");
  }

  init_get_next_lower_word ();
  while ((word= get_next_lower_word (text_file))) {
    if (!find (word, table))
      printf ("%d: %s\n", line_count, word);
  }

  printf ("Usage statistics:\n");
  print_stats (table);

  return 0;
}
