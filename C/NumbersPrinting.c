#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include <semaphore.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>
#define MAX_NUM 100



void RandomSleep() {
	int k = rand();
	if (k % 3 == 0) {
		usleep(100);
	}
}

typedef struct 
{
    sem_t * even;
    sem_t * odd;
}dataT;



void * EvenPrinter(void * data) {
    dataT* d = (dataT*) data;
	for(int i = 2; i <= MAX_NUM; i+=2){
        sem_wait(d->even);
        printf("%d\n", i);
        sem_post(d->odd);
    }
	return NULL;
}

void * OddPrinter(void * data) {
    dataT* d = (dataT*) data;
	for(int i = 1; i <= MAX_NUM; i += 2){
        sem_wait(d->odd);
        printf("%d\n", i);
        sem_post(d->even);
    }
	return NULL;
}
int main() {
    dataT* data = malloc(sizeof(dataT));
    data->even = malloc(sizeof(sem_t));
    data->odd = malloc(sizeof(size_t));
    sem_init(data->even, 0, 0);
    sem_init(data->odd, 0, 1);
	pthread_t* forEven = malloc(sizeof(pthread_t));
    pthread_create(forEven, NULL, EvenPrinter, data);
    pthread_t* forOdd = malloc(sizeof(pthread_t));
    pthread_create(forEven, NULL, OddPrinter, data);
    

    pthread_join(*forEven, NULL);
    pthread_join(*forOdd, NULL);

    free(forEven);
    free(forOdd);
	free(data->even);
	free(data->odd);
	free(data);

    return 0;
}
