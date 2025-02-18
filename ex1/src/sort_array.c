#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include "sort_array.h"

/*
    Macro with parameters that simply swaps two elements, x and y, using the pointer t as a placeholder
*/
#define SWAP(x, y, t)   memcpy((t), (x), global_size);  \
                        memcpy((x), (y), global_size);  \
                        memcpy((y), (t), global_size);  \

/* 
    global variables used to reduce the number of parameters passed on the recursive functions, since they both don't change during the execution.
    The function "global_compar" is used to determine whether an element precedes another one or not. 
    Every element of the array to sort occupies "global_size" bytes in the memory.
*/

int (* global_compar)(const void*, const void*);
size_t global_size;

/* struct which contains a pivot index and the size of an array of equal elements*/
struct double_pivot {
    long indx_piv;
    long indx_low_equal_array;
};

static void quick_sort_ric(void *base,   long low,  long high);
static struct double_pivot partition(void *base,  long low,  long high);
static void merge_sort_ric(void *base, unsigned long first, unsigned long last);
static void merge_iter(void *base, unsigned long first, unsigned long last, unsigned long mid);
static void * my_malloc(int size);


void quick_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*)){
    global_compar = compar;
    global_size = size;

    if(base == NULL){
        fprintf(stderr,"quick_sort: base parameter cannot be NULL\n");
        exit(EXIT_FAILURE);
    }
    if(compar == NULL){
        fprintf(stderr,"quick_sort: compar parameter cannot be NULL\n");
        exit(EXIT_FAILURE);
    }
    if(size == 0){
        fprintf(stderr,"quick_sort: size must be greater than zero\n");
        exit(EXIT_FAILURE);
    }
    if(nitems >= 1){
        quick_sort_ric(base,0, nitems - 1);
    }
}

void merge_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*)){
    global_compar = compar;
    global_size = size;

    if(base == NULL){
        fprintf(stderr,"merge_sort: base parameter cannot be NULL\n");
        exit(EXIT_FAILURE);
    }
    if(compar == NULL){
        fprintf(stderr,"merge_sort: compar parameter cannot be NULL\n");
        exit(EXIT_FAILURE);
    }
    if(size == 0){
        fprintf(stderr,"merge_sort: size must be greater than zero\n");
        exit(EXIT_FAILURE);
    }
    if(nitems >= 1){
        merge_sort_ric(base, 0, nitems - 1);
    }
}



/*
    A ricursive procedure that sorts the array "base" from the index "first" to the index "last"; using the merge sort algorithm.
    The condition: ( first <= last < "base" size ) is always met
*/
static void merge_sort_ric(void *base, unsigned long first, unsigned long last){
    if(first < last){
        unsigned long mid = (first + last) / 2;
        merge_sort_ric(base,first, mid); 
        merge_sort_ric(base,mid+1, last);
        merge_iter(base, first, last, mid);
    }
}

/* 
    Iterative Procedure that takes in input an array "base" and it sorts it from the "first" position to the "last" position.
    The array "base" also must be already ordered from the "first" position to the "mid" position and from the position following "mid" to the "last" position    
 */ 

static void merge_iter(void *base, unsigned long first, unsigned long last, unsigned long mid){
    unsigned long i = first, j = mid + 1, k = 0;
    void * arr_copy = my_malloc((last-first + 1) * global_size);

    while(i <= mid && j <= last){
        if(((* (global_compar)) (base + i * global_size, base + j * global_size)) <= 0){
            memcpy(arr_copy + (k * global_size), base + (i * global_size), global_size);
            i = i+1;
        }else{
            memcpy(arr_copy + (k * global_size), base + (j * global_size) , global_size);
            j = j+1;
        }
        k = k+1;
    }

    if(i <=  mid){
        memcpy(arr_copy + k * global_size, base + i * global_size, (mid -  i + 1)  * global_size);
    }else{
        memcpy(arr_copy + k * global_size, base + j * global_size, (last - j + 1) * global_size);
    }
    memcpy(base + (first * global_size), arr_copy, (last - first + 1) * global_size);
    free(arr_copy);
}

/* 
    recursive procedure that sorts the array using a pivoting function and by deviding the array into two smaller arrays, one that precedes the pivot and one that follows it
    Optimization: It also doesn't consider the last part of the array which precedes the pivot if this array contains all equal elements.
*/
static void quick_sort_ric(void *base, long low, long high){
    if(low < high){
        struct double_pivot pivots = partition(base, low, high);

        quick_sort_ric(base, low, pivots.indx_piv - 1 - pivots.indx_low_equal_array);
        
        quick_sort_ric(base, pivots.indx_piv + 1, high);
    }
}

/*
    Iterative Function that, given the pivot equal to the index of the last element of the array; it modifies (if necessary) the array "base" in order to obtain an array
    where all the elements before this chosen pivot precede or equal it and all the elements after the pivot follow it.
    "base" contains "high - low + 1" elements of size global_size.
    The return value of the function is a struct which contains the new index of the element used as a pivot
    and it contains another long value that indicates the number of elements in the array if this array is made of equal elements, if not its value is zero.
*/

static struct double_pivot partition(void *base, long low, long high){
    long pivot = high, sum = 0;
    long i = low - 1;
    void * temp = my_malloc(global_size);
    int compar_ris;
    struct double_pivot pivots;
    void * pivot_val = base + pivot * global_size;
    pivots.indx_low_equal_array = 0;

    for(long j = low; j < high; j++){
        compar_ris = (*(global_compar))(pivot_val, base + j * global_size);
        if(compar_ris >= 0){
            i++;
            sum = sum + compar_ris; 
            SWAP(base + (i * global_size), base + (j * global_size), temp);
        }

    }

    if(sum == 0 && i != low - 1){
          pivots.indx_low_equal_array = i + 1;
    }

    SWAP(base + ((i+1) * global_size), base + (high * global_size), temp);
    free(temp);
    pivots.indx_piv = i+1;

    return pivots;
} 

/* Function that allocates a new memory of size bytes and returns the pointer to this memory segment. It also checks if it was correctly allocated*/
static void * my_malloc(int size){
  void * new_data = malloc(size);

  if(new_data == NULL){
    fprintf(stderr,"my_malloc: failed to allocate memory");
    exit(EXIT_FAILURE);
  }
  return new_data;
}