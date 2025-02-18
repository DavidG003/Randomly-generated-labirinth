#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "edit_distance.h"

/* This global pointer is used as a cache to memoize the solutions of some recursive calls and
    it is dinamically allocated into the "edit_distance_dym" function */
int ** cache;

static int min_of_three(int val1, int val2, int val3);
static void cache_allocation(int rows, int cols);
static int edit_distance_ric(const char *s1, const char* s2);
static int edit_distance_dyn_ric(const char *s1, const char* s2);

int edit_distance(const char *s1, const char* s2){
    if(s1 == NULL){
        printf("Edit_distance: the first parameter must be a non NULL pointer to a string \n");
        exit(EXIT_FAILURE);
    }

    if(s2 == NULL){
        printf("Edit_distance: the second parameter must be a non NULL pointer to a string \n");
        exit(EXIT_FAILURE);
    }

    return edit_distance_ric(s1, s2);

}

int edit_distance_dyn(const char *s1, const char* s2){
    if(s1 == NULL){
        printf("Edit_distance: the first parameter must be a non NULL pointer to a string \n");
        exit(EXIT_FAILURE);
    }

    if(s2 == NULL){
        printf("Edit_distance: the second parameter must be a non NULL pointer to a string \n");
        exit(EXIT_FAILURE);
    }

    int rows = strlen(s1) + 1;
    int cols = strlen(s2) + 1;

    cache_allocation(rows, cols);

    int ris = edit_distance_dyn_ric(s1, s2);

    for(int i = 0; i < rows; i++) 
        free(cache[i]);
    free(cache);
    return ris;
}


static int edit_distance_ric(const char *s1, const char* s2){
    int s1_len = strlen(s1);
    int s2_len = strlen(s2);

    if(s1_len == 0)
        return s2_len;
    if(s2_len == 0)
        return s1_len;
    
    int no_op = -1;
    if(*s1 == *s2){
        no_op = edit_distance_ric(s1 + 1, s2 + 1);
    }

    int canc_op = 1 + edit_distance_ric(s1, s2 + 1);

    int ins_op = 1 + edit_distance_ric(s1 + 1, s2);

    return min_of_three(no_op, canc_op, ins_op);

}


/*
    Recursive function that uses a 2 dimensional array to store the recursive results.
    The lenghts of the two parameter strings are used to set and retrieve from this cache array.

*/
static int edit_distance_dyn_ric(const char *s1, const char* s2){
    int s1_len = strlen(s1);
    int s2_len = strlen(s2);
    
    if(cache[s1_len][s2_len] != -1){
        return cache[s1_len][s2_len];
    }

    if(s1_len == 0){
        cache[0][s2_len] = s2_len;
        return s2_len;
    }

    if(s2_len == 0){
        cache[s1_len][0] = s1_len;
        return s1_len;
    }
    
    int no_op = -1;
    if(*s1 == *s2){
        no_op = edit_distance_dyn_ric(s1 + 1, s2 + 1);
    }

    int canc_op = 1 + edit_distance_dyn_ric(s1, s2 + 1);

    int ins_op = 1 + edit_distance_dyn_ric(s1 + 1, s2);

    cache[s1_len][s2_len] = min_of_three(no_op, canc_op, ins_op);
    return cache[s1_len][s2_len];


}

/*  
    Function that, given three "positive" integer values, it returns the greatest out of them
    Only val1 can be equal to -1, this is a special value that is used to rapresent Infinity

*/
static int min_of_three(int val1, int val2, int val3){
    int smallest = val1;
    if(val1 == -1){
        if(val2 < val3)
            return val2;
        return val3;
    }
    
    if (smallest > val2) smallest = val2;
    
    if (smallest > val3) smallest = val3;

    return smallest;
}

/*procedure that allocates (rows * cols) bytes in the heap, this newly allocated memory is pointed by the global pointer cache*/
static void cache_allocation(int rows, int cols){
    cache = malloc(sizeof(*cache) * rows);
    if(cache == NULL){
        fprintf(stderr, "cache_allocation: Memory allocation for cache rows failed\n");
        exit(EXIT_FAILURE);
    }
    for(int i = 0; i < rows; i++){
        cache[i] = malloc(cols * sizeof(**cache));

        if(cache[i] == NULL){
            fprintf(stderr, "cache_allocation: Memory allocation for cache column failed\n");
            exit(EXIT_FAILURE);
        }

        memset(cache[i], -1, cols * sizeof(**cache));
    }
}
