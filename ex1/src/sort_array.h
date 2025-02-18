#ifndef SORT_ARRAY_H_poiuhnbvcffshjnbvcdrtyjmffg
#define SORT_ARRAY_H_poiuhnbvcffshjnbvcdrtyjmffg

/*
    The procedure sorts the array "base" in the order given by the function "compar" using the quicksort algorithm 
    It accepts as inputs:
    * base: a pointer of any type that points to the first element of an array of elements
    * nitems: number of elements contained in the array "base"
    * size: the size (in bytes) of a signle element in the array
    * compar: a pointer to a function which has two elements of the array "base" as input values.
              The function compares the two elements and it returns:
              a positive integer if the first element follows the second one
              a negative integer if the first element precedes the second one
              zero if the elements hold the same value 
    base and compar parameters cannot be NULL.
    Warning: This procedure possibly alters the order of the elements in the array
*/
void quick_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*));

/*
    The Procedure sorts the array "base" in the order given by the function "compar" using the mergesort algorithm 
    It accepts as inputs:
    * base: a pointer of any type that points to the first element of an array of elements 
    * nitems: number of elements contained in the array "base"
    * size: the size (in bytes) of a signle element in the array
    * compar: a pointer to a function which has two elements of the array "base" as input values.
              The function compares the two elements and it returns:
              a positive integer if the first element follows the second one
              a negative integer if the first element precedes the second one
              zero if the elements hold the same value 
    base and compar parameters cannot be NULL.
    Warning: This procedure possibly alters the order of the elements in the array
*/
void merge_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*));

#endif /* SORT_ARRAY_H_poiuhnbvcffshjnbvcdrtyjmffg */

