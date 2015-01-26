#include <GL/glut.h>
#include <stdio.h>

void display (void) 
{ /* Callback called when OpenGL needs to update the display */
  glClear (GL_COLOR_BUFFER_BIT); /* Clear the window */
}

void keyboard (unsigned char key, int x, int y)
{ /* Callback called when a key is pressed */
  if (key == 27) { exit (0); }       /* 27 is the Escape key */
} 

void tobys_bistro (int item)
{ /* Callback called when the user clicks the right mouse button */
  printf ("Toby's bistro: you clicked item %d\n", item);
}

void steves_chippy (int item)
{ /* Callback called when the user clicks the middle mouse button */
  printf ("Steve's chippy: you clicked item %d\n", item);
}

int main (int argc, char** argv)
{
  glutInit (&argc, argv);        /* Initialise OpenGL */
  glutCreateWindow ("Menus");    /* Create the window */
  glutDisplayFunc (display);     /* Register the "display" function */
  glutKeyboardFunc (keyboard);   /* Register the "keyboard" function */

  glutCreateMenu (tobys_bistro); /* Create the first menu & add items */
  glutAddMenuEntry ("Petto di Tacchino alla Napoletana", 1);
  glutAddMenuEntry ("Bruschetta al Pomodoro e Olive", 2);
  glutAddMenuEntry ("Chianti Classico", 3);
  glutAttachMenu (GLUT_RIGHT_BUTTON); /* Attach it to the right button */

  glutCreateMenu (steves_chippy); /* Create the second menu & add items */
  glutAddMenuEntry ("Rissoles", 1);
  glutAddMenuEntry ("Curry sauce", 2);
  glutAddMenuEntry ("Vimto", 3);
  glutAttachMenu (GLUT_MIDDLE_BUTTON); /* Attach it to the middle button */

  glutMainLoop ();                   /* Enter the OpenGL main loop */
  return 0;
}
