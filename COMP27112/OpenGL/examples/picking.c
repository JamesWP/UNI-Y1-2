/*
Reproduced with permission from
http://www.lighthouse3d.com/opengl/picking/ with modifications by Toby
Howard.
 */
#include <math.h>
#include <GLUT/glut.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct cameras {
	float pos[3];
	float lookAt[3];
	float lookUp[3];
} camera;

static GLint snowman_display_list;

static camera cam;

// Selection Buffer
#define SelBufferSize 512
static int mainWindow;
static int border = 6,h=200,w=350;

// Picking Stuff //
#define RENDER					1
#define SELECT					2
#define BUFSIZE 1024
GLuint selectBuf[BUFSIZE];
GLint hits;
int mode = RENDER;
int cursorX,cursorY;

void changeSize(int w1, int h1) {

	float ratio;

	h = h1;
	w = w1;
	// Prevent a divide by zero, when window is too short
	// (you cant make a window of zero width).

	if(h == 0)
		h = 1;

	ratio = 1.0f * w / h;
	// Reset the coordinate system before modifying
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	
	// Set the viewport to be the entire window
        glViewport(0, 0, w, h);

	// Set the clipping volume
	gluPerspective(45,ratio,0.1,1000);

	// setting the camera now
	glMatrixMode(GL_MODELVIEW);
}


void processPick ()
{
	GLint viewport[4];
	GLubyte pixel[3];

	glGetIntegerv(GL_VIEWPORT,viewport);
	glReadPixels(cursorX,viewport[3]-cursorY,1,1,GL_RGB,GL_UNSIGNED_BYTE,(void *)pixel);
	printf("%d %d %d\n",pixel[0],pixel[1],pixel[2]);
	if (pixel[0] == 255)
	  printf ("You picked the 1st snowman on the 1st row");
	else if (pixel[1] == 255)
	  printf ("You picked the 1st snowman on the 2nd row");
	else if (pixel[2] == 255)
	  printf ("You picked the 2nd snowman on the 1st row");
	else if (pixel[0] == 250)
	  printf ("You picked the 2nd snowman on the 2nd row");
   else
	   printf("You didn't click a snowman!");
  printf ("\n");
}


void drawPickingMode() {
  int i, j;

// Draw 4 SnowMen
	glDisable(GL_DITHER);
	for(i = 0; i < 2; i++)
		for(j = 0; j < 2; j++) {
			glPushMatrix();

			switch (i*2+j) {	
				case 0: glColor3ub(255,0,0);break;
				case 1: glColor3ub(0,255,0);break;
				case 2: glColor3ub(0,0,255);break;
				case 3: glColor3ub(130,0,130);break;
			}

			glTranslatef(i*3.0,0,-j * 3.0);
			glCallList(snowman_display_list);
			glPopMatrix();
		}
	glEnable(GL_DITHER);
}

void renderScene() {
	glutSetWindow(mainWindow);  
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glLoadIdentity();
	gluLookAt(cam.pos[0],
		  cam.pos[1],
		  cam.pos[2],   

		  cam.lookAt[0],
		  cam.lookAt[1],
		  cam.lookAt[2],    

		  cam.lookUp[0],
		  cam.lookUp[1],
		  cam.lookUp[2]);

	if (mode == SELECT)
		drawPickingMode();
	else
		drawPickingMode();

	if (mode == SELECT) {
		processPick();
		mode = RENDER;
	}
	else
		glutSwapBuffers();
}

void processKeyboard(unsigned char key, int x, int y) {
	printf("key: %d\n",key);
}

void processNormalKeys(unsigned char key, int x, int y) {
	if (key == 27) {
		exit(0);
	}
	else
		processKeyboard(key, x, y);
}

void mouseStuff(int button, int state, int x, int y) {
	if (button != GLUT_LEFT_BUTTON || state != GLUT_DOWN)
	return;
	cursorX = x;
	cursorY = y;
	mode = SELECT;
}

void picked(GLuint name, int sw) {
	printf("my name = %d in %d\n",name,sw);
}

void init(camera *cam) {
	cam->pos[0] = 1.5;
	cam->pos[1] = 3.75;
	cam->pos[2] = 3;

	cam->lookAt[0] = 1.5;
	cam->lookAt[1] = 1.75;
	cam->lookAt[2] = 0;

	cam->lookUp[0] = 0;
	cam->lookUp[1] = 1;
	cam->lookUp[2] = 0;
}

void drawSnowMan() {
// Draw Body	
	glTranslatef(0.0f ,0.75f, 0.0f);
	glutSolidSphere(0.75f,20,20);
// Draw Head
	glTranslatef(0.0f, 1.0f, 0.0f);
	glutSolidSphere(0.25f,20,20);
// Draw Eyes
	glPushMatrix();
	glColor3f(0.0f,0.0f,0.0f);
	glTranslatef(0.05f, 0.10f, 0.18f);
	glutSolidSphere(0.05f,10,10);
	glTranslatef(-0.1f, 0.0f, 0.0f);
	glutSolidSphere(0.05f,10,10);
	glPopMatrix();
// Draw Nose
	glColor3f(1.0f, 0.5f , 0.5f);
	glRotatef(0.0f,1.0f, 0.0f, 0.0f);
	glutSolidCone(0.08f,0.5f,10,2);
}

GLuint createDL() {
	GLuint snowManDL;

	// Create the id for the list
	snowManDL = glGenLists(1);

	glNewList(snowManDL,GL_COMPILE);
		drawSnowMan();
	glEndList();
	return(snowManDL);
}

void initScene(int argc, char **argv) {
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);
	snowman_display_list = createDL();
}

void draw() {
  int i, j;

// Draw ground
	glColor3f(0.9f, 0.9f, 0.9f);
	glBegin(GL_QUADS);
		glVertex3f(-100.0f, 0.0f, -100.0f);
		glVertex3f(-100.0f, 0.0f,  100.0f);
		glVertex3f( 100.0f, 0.0f,  100.0f);
		glVertex3f( 100.0f, 0.0f, -100.0f);
	glEnd();

// Draw 4 SnowMen

	glColor3f(1.0f, 1.0f, 1.0f);

	for(i = 0; i < 2; i++)
		for(j = 0; j < 2; j++) {
			glPushMatrix();
			glPushName(i*2+j);
			glTranslatef(i*3.0,0,-j * 3.0);
			glColor3f(1.0f, 1.0f, 1.0f);
			glCallList(snowman_display_list);
			glPopName();
			glPopMatrix();
		}

}

int main(int argc, char **argv)
{
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH  | GLUT_DOUBLE | GLUT_RGBA);
	glutInitWindowPosition(100,100);
	glutInitWindowSize(w,h);
	mainWindow = glutCreateWindow("SnowMen from Lighthouse 3D");

	glutKeyboardFunc(processNormalKeys);
	glutReshapeFunc(changeSize);
	glutDisplayFunc(renderScene);
	glutMouseFunc(mouseStuff);
	glutIdleFunc(renderScene);

	initScene(argc,argv);
	init(&cam);

	glutMainLoop();

	return(0);
}

