
#include <stdio.h>
#include <stdlib.h>
#include "unity.h"
#include "sort_array.h"

static int compar_int(const void * val1,const void * val2){
    int * int_p1 = (int *) val1;
    int * int_p2 = (int *) val2;
    if((*int_p1) > (*int_p2)){
        return(1);
    }else if((*int_p1) == (*int_p2)){
        return(0);
    }
    return -1;
}

static int  * a_1elem, * a_2elem, *a_5elem, *a_6elem;

void setUp(void){
    a_1elem = malloc(sizeof(*a_1elem));
    a_2elem = malloc(sizeof(*a_2elem)*2);
    a_5elem = malloc(sizeof(*a_5elem)*5);
    a_6elem = malloc(sizeof(*a_6elem)*6);
}

void tearDown(void){

    free(a_1elem);
    free(a_2elem);
    free(a_5elem);
    free(a_6elem);
}

/*QUICK SORT TESTS*/
static void test_quick_sort_one_el(void){
    int expected_arr[] = {-177};
    a_1elem[0] = -177;
    quick_sort((void *)a_1elem, 1, sizeof(*a_1elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_1elem, 1);
}

static void test_quick_sort_two_ordered_el(void){
    int expected_arr[] = {-177, 55};
    a_2elem[0] = -177;
    a_2elem[1] = 55;
    quick_sort((void *)a_2elem, 2, sizeof(*a_2elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_2elem, 2);
}

static void test_quick_sort_two_not_ordered_el(void){
    int expected_arr[] = {-909, 1};
    a_2elem[0] = 1;
    a_2elem[1] = -909;
    quick_sort((void *)a_2elem, 2, sizeof(*a_2elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_2elem, 2);
}

static void test_quick_sort_two_equal_el(void){
    int expected_arr[] = {0, 0};
    a_2elem[0] = 0;
    a_2elem[1] = 0;
    quick_sort((void *)a_2elem, 2, sizeof(*a_2elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_2elem, 2);
}


static void test_quick_sort_five_inv_ordered_el(void){
    int expected_arr[] = {-1,1,3,4,5};
    a_5elem[0] = 5;
    a_5elem[1] = 4;
    a_5elem[2] = 3;
    a_5elem[3] = 1;
    a_5elem[4] = -1;

    quick_sort((void *)a_5elem, 5, sizeof(*a_5elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_5elem, 5);
}

static void test_quick_sort_five_not_ordered_el(void){
    int expected_arr[] = {-6,-1,5,44,100};
    a_5elem[0] = 5;
    a_5elem[1] = -1;
    a_5elem[2] = 100;
    a_5elem[3] = -6;
    a_5elem[4] = 44;

    quick_sort((void *)a_5elem, 5, sizeof(*a_5elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_5elem, 5);
}

static void test_quick_sort_six_binary_el(void){
    int expected_arr[] = {0,0,0,1,1,1};
    a_6elem[0] = 1;
    a_6elem[1] = 0;
    a_6elem[2] = 1;
    a_6elem[3] = 0;
    a_6elem[4] = 1;
    a_6elem[5] = 0;

    quick_sort((void *)a_6elem, 6, sizeof(*a_6elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_6elem, 6);
}

//MERGE SORT TESTS
static void test_merge_sort_one_el(void){
    int expected_arr[] = {-177};
    a_1elem[0] = -177;
    merge_sort((void *)a_1elem, 1, sizeof(*a_1elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_1elem, 1);
}

static void test_merge_sort_two_ordered_el(void){
    int expected_arr[] = {-177, 55};
    a_2elem[0] = -177;
    a_2elem[1] = 55;
    merge_sort((void *)a_2elem, 2, sizeof(*a_2elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_2elem, 2);
}

static void test_merge_sort_two_not_ordered_el(void){
    int expected_arr[] = {-909, 1};
    a_2elem[0] = 1;
    a_2elem[1] = -909;
    merge_sort((void *)a_2elem, 2, sizeof(*a_2elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_2elem, 2);
}

static void test_merge_sort_two_equal_el(void){
    int expected_arr[] = {0, 0};
    a_2elem[0] = 0;
    a_2elem[1] = 0;
    merge_sort((void *)a_2elem, 2, sizeof(*a_2elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_2elem, 2);
}


static void test_merge_sort_five_inv_ordered_el(void){
    int expected_arr[] = {-1,1,3,4,5};
    a_5elem[0] = 5;
    a_5elem[1] = 4;
    a_5elem[2] = 3;
    a_5elem[3] = 1;
    a_5elem[4] = -1;

    merge_sort((void *)a_5elem, 5, sizeof(*a_5elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_5elem, 5);
}

static void test_merge_sort_five_not_ordered_el(void){
    int expected_arr[] = {-6,-1,5,44,100};
    a_5elem[0] = 5;
    a_5elem[1] = -1;
    a_5elem[2] = 100;
    a_5elem[3] = -6;
    a_5elem[4] = 44;

    merge_sort((void *)a_5elem, 5, sizeof(*a_5elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_5elem, 5);
}

static void test_merge_sort_six_binary_el(void){
    int expected_arr[] = {0,0,0,1,1,1};
    a_6elem[0] = 1;
    a_6elem[1] = 0;
    a_6elem[2] = 1;
    a_6elem[3] = 0;
    a_6elem[4] = 1;
    a_6elem[5] = 0;

    merge_sort((void *)a_6elem, 6, sizeof(*a_6elem), compar_int);
    TEST_ASSERT_EQUAL_INT_ARRAY(expected_arr, a_6elem, 6);
}

int main(void){
// start of test session

UNITY_BEGIN();

RUN_TEST(test_quick_sort_one_el);
RUN_TEST(test_quick_sort_two_ordered_el);
RUN_TEST(test_quick_sort_two_not_ordered_el);
RUN_TEST(test_quick_sort_two_equal_el);
RUN_TEST(test_quick_sort_five_inv_ordered_el);
RUN_TEST(test_quick_sort_five_not_ordered_el);
RUN_TEST(test_quick_sort_six_binary_el);

RUN_TEST(test_merge_sort_one_el);
RUN_TEST(test_merge_sort_two_ordered_el);
RUN_TEST(test_merge_sort_two_not_ordered_el);
RUN_TEST(test_merge_sort_two_equal_el);
RUN_TEST(test_merge_sort_five_inv_ordered_el);
RUN_TEST(test_merge_sort_five_not_ordered_el);
RUN_TEST(test_merge_sort_six_binary_el);


return UNITY_END();

}