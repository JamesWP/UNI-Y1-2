#include <GL/glut.h>
  
float r= 0.0;
GLdouble tx=0.0, ty=0.0, tz=0.0;
int object= 0;
  
void init(void) {
  glClearColor(0.9,0.9,0.9,0.0); /* Set grey background */
}
  
void spin (void) {
   r+= 1.0;
   glutPostRedisplay();
}
  
void display(void) {
   glClear (GL_COLOR_BUFFER_BIT);
   glLoadIdentity ();
   gluLookAt (0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
  
   glTranslatef(tx*5.0, ty*5.0, 0.0);
   glRotatef(r, 1, 0, 0);
   glRotatef(r, 0, 1, 0);
   glRotatef(r, 0, 0, 1);
     
   switch (object) {
   case 0: glColor3f (0.0, 1.0, 0.0);
           glutWireCube(2.0);
           break;
   case 1: glColor3f (0.0, 1.0, 1.0);   
           glutWireDodecahedron();
           break;
   case 2: glColor3f (1.0, 0.0, 0.0);
           glutWireIcosahedron();
           break;
   case 3: glColor3f (1.0, 1.0, 0.0);
           glutWireTeapot(1.5);
           break;
   }
   glutSwapBuffers();
}
  
void reshape (int w, int h) {
   glViewport (0, 0, (GLsizei) w, (GLsizei) h); 
   glMatrixMode (GL_PROJECTION);
   glLoadIdentity ();
   gluPerspective (60, (GLfloat) w / (GLfloat) h, 1.0, 100.0);
   glMatrixMode (GL_MODELVIEW);
}
  
void mouse (int x, int y) {
  /* Maps a mouse (x,y) back to world coordinates */
  GLdouble projection[16], modelview[16];
  GLint viewport[4];

  glLoadIdentity();
  glGetDoublev(GL_MODELVIEW_MATRIX, modelview);
  glGetDoublev(GL_PROJECTION_MATRIX, projection);
  glGetIntegerv(GL_VIEWPORT, viewport);
  gluUnProject ((double)x, (double)500-y-1, (double)0.0, 
                modelview, projection, viewport, 
                &tx, &ty, &tz);
}
  
void keyboard(unsigned char key, int x, int y) {
  if (key == 27) { exit (0); }                 /* escape key */
  if (key == 32) { object= (object + 1) % 4; } /* spacebar */
} 
  
int main(int argc, char **argv) {
   glutInit(&argc, argv);
   glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB);
   glutInitWindowSize (500, 500); 
   glutInitWindowPosition (100, 100);
   glutCreateWindow ("ex7: Press the space bar to change the object.");
   init ();
   glutDisplayFunc (display); 
   glutReshapeFunc (reshape);
   glutKeyboardFunc (keyboard);
   glutPassiveMotionFunc (mouse);
   glutIdleFunc (spin);
   glutMainLoop ();
   return 0;
 }
