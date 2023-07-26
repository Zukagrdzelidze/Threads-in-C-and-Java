#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include <semaphore.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>

#define NUM_RACERS 10
#define DISTANCE 100

void RandomSleep() {
	int k = rand();
	if (k % 3 == 0) {
		usleep(100);
	}
}

typedef struct{
    int id; 
    int* counter;
    sem_t * lock;
}racerT;

void* RunRace(void* data){
    racerT * racer = (racerT*)data;
    int * counter = racer->counter;
    for (int i=0; i<DISTANCE; i++) {
		RandomSleep();
	}
    int place;
    sem_wait(racer->lock);
    (*counter)++;
    place = *counter;
    sem_post(racer->lock);
    printf("racer %d took place %d\n", racer->id, place);
    return NULL;
}

int main() {
    srand(time(NULL));
    pthread_t tids[NUM_RACERS];
    int * counter = malloc(sizeof(int));
    sem_t * lock = malloc(sizeof(sem_t));
    *counter = 0;
    sem_init(lock, 0, 1);
    for(int i = 0; i < NUM_RACERS; i++){
        racerT * racer = malloc(sizeof(racerT));
        racer->id = i;
        racer->counter = counter;
        racer->lock = lock;
        printf("run race %d\n", i);
        pthread_create(&tids[i], NULL, RunRace, racer);
    }
    pthread_exit(NULL);
}