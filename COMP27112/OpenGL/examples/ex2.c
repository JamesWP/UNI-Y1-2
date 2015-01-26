#include <GL/glut.h>
#include <stdio.h>
  
void display (void) { 
/* Called when OpenGL needs to update the display */
  glClearColor(0.9,0.9,0.9,0.0); /* Set grey background */
  glClear (GL_COLOR_BUFFER_BIT); /* Clear the window */
  glFlush();                     /* Force update of screen */
}
  
void keyboard (unsigned char key, int x, int y) {
/* Called when a key is pressed */
  if (key == 27) exit (0);    /* 27 is the Escape key */
  else printf ("You pressed %c\n", key);
} 
  
int main(int argc, char **argv) {
  glutInit (&argc, argv);      /* Initialise OpenGL */
  glutCreateWindow ("ex2");    /* Create the window */
  glutDisplayFunc (display);   /* Register the "display" function */
  glutKeyboardFunc (keyboard); /* Register the "keyboard" function */
  glutMainLoop ();             /* Enter the OpenGL main loop */
  return 0;
}
