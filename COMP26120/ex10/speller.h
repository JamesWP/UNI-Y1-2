typedef enum {FALSE, TRUE} Boolean;

extern int verbose; // used to control monitoring output
extern int mode;    // used to control your algorithm

extern char *prog_name; // used by check

void check (void *memory) ; // check result from strdup, malloc etc.
