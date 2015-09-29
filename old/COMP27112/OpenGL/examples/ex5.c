#include <GL/glut.h>

void display (void) { 
/* Called when OpenGL needs to update the display */
  glClearColor(0.9,0.9,0.9,0.0); /* Set grey background */
  glClear (GL_COLOR_BUFFER_BIT); /* Clear the window */
  glLoadIdentity ();
  gluLookAt (0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
  glColor3f(1.0,0.0,0.0); 
  glutWireCube(2.0);
  glFlush();                     /* Force update of screen */
}

void keyboard (unsigned char key, int x, int y) { 
/* Called when a key is pressed */
  if (key == 27) exit (0);       /* 27 is the Escape key */
} 

void reshape (int w, int h) { 
/* Called if the window is moved or resized */
   glViewport (0, 0, (GLsizei)w, (GLsizei)h); 
   glMatrixMode (GL_PROJECTION);
   glLoadIdentity ();
   gluPerspective (60, (GLfloat)w / (GLfloat)h, 1.0, 100.0);
   glMatrixMode (GL_MODELVIEW);
}

int main(int argc, char **argv) {
  glutInit (&argc, argv);            /* Initialise OpenGL */
  glutInitWindowSize (500, 500);     /* Set the window size */
  glutInitWindowPosition (100, 100); /* Set the window position */
  glutCreateWindow ("ex5");          /* Create the window */
  glutDisplayFunc (display);         /* Register the "display" function */
  glutKeyboardFunc (keyboard);       /* Register the "keyboard" function */
  glutReshapeFunc (reshape);         /* Register the "reshape" function */ 
  glutMainLoop ();                   /* Enter the OpenGL main loop */
  return 0;
}
