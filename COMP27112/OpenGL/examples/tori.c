#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <GL/glut.h>

#define WIDTH 640
#define HEIGHT 480

static int ScreenWidth, ScreenHeight;
static GLuint Torus = 0;
static GLfloat Xrot = 0.0, Yrot = 0.0;

static void Display( void )
{
   static GLfloat blue[4] = {0.2, 0.2, 1.0, 1.0};
   static GLfloat red[4] = {1.0, 0.2, 0.2, 1.0};
   static GLfloat green[4] = {0.2, 1.0, 0.2, 1.0};

   glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

   glPushMatrix();
   glRotatef(Xrot, 1, 0, 0);
   glRotatef(Yrot, 0, 1, 0);

   glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE, blue);
   glCallList(Torus);

   glRotatef(90.0, 1, 0, 0);
   glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE, red);
   glCallList(Torus);

   glRotatef(90.0, 0, 1, 0);
   glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE, green);
   glCallList(Torus);

   glPopMatrix();

   glutSwapBuffers();
}

static void Reshape( int width, int height )
{
   float ratio = (float) width / (float) height;

   ScreenWidth = width;
   ScreenHeight = height;

   if (width > WIDTH)
      width = WIDTH;
   if (height > HEIGHT)
      height = HEIGHT;

   glViewport( 0, 0, width, height );
   glMatrixMode( GL_PROJECTION );
   glLoadIdentity();
   glFrustum( -ratio, ratio, -1.0, 1.0, 5.0, 30.0 );
   glMatrixMode( GL_MODELVIEW );
   glLoadIdentity();
   glTranslatef( 0.0, 0.0, -20.0 );
}

static void Key( unsigned char key, int x, int y )
{
   switch (key) {
      case 27:
         exit(0);
         break;
   }
   glutPostRedisplay();
}

static void SpecialKey( int key, int x, int y )
{
   switch (key) {
      case GLUT_KEY_UP:
         break;
      case GLUT_KEY_DOWN:
         break;
      case GLUT_KEY_LEFT:
         break;
      case GLUT_KEY_RIGHT:
         break;
   }
   glutPostRedisplay();
}

static void MouseMove( int x, int y )
{
   Xrot = y - ScreenWidth / 2;
   Yrot = x - ScreenHeight / 2;
   glutPostRedisplay();
}

static void Init( void )
{
   Torus = glGenLists(1);
   glNewList(Torus, GL_COMPILE);
   glutSolidTorus(0.5, 2.0, 10, 20);
   glEndList();

   glEnable(GL_LIGHTING);
   glEnable(GL_LIGHT0);

   glEnable(GL_DEPTH_TEST);
   glEnable(GL_CULL_FACE);
}


int main( int argc, char *argv[] )
{

   /* Give an initial size and position so user doesn't have to place window */
   glutInitWindowPosition(0, 0);
   glutInitWindowSize(WIDTH, HEIGHT);
   glutInit( &argc, argv );

   glutInitDisplayMode( GLUT_RGB | GLUT_DOUBLE | GLUT_DEPTH );

   glutCreateWindow(argv[0]);

   Init();

   glutReshapeFunc( Reshape );
   glutKeyboardFunc( Key );
   glutSpecialFunc( SpecialKey );
   glutDisplayFunc( Display );
   glutPassiveMotionFunc( MouseMove );

   glutMainLoop();
   return 0;
}