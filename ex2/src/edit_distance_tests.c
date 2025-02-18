
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "unity.h"
#include "edit_distance.h"

#define BZERO(b,len) (memset((b), '\0', (len)), (void) 0)  


static char empty_s[1], casa_s[5], cassa_s[6], ciao_s[5], addio_s[6], sole_s[5], cibo_s[5], sole_s2[5], sparlare_s[9], parlare_s[8]; 

void setUp(void){
    
    strcpy(empty_s, "");
    strcpy(casa_s, "casa");
    strcpy(cassa_s, "cassa");
    strcpy(ciao_s, "ciao");
    strcpy(addio_s, "addio");
    strcpy(sole_s, "sole");
    strcpy(cibo_s, "cibo");
    strcpy(sole_s2, "sole");
    strcpy(parlare_s, "parlare");
    strcpy(sparlare_s, "sparlare");

}

void tearDown(void){
    
    BZERO(empty_s, 1);
    BZERO(casa_s,  5);
    BZERO(cassa_s, 6);
    BZERO(ciao_s,  5);
    BZERO(addio_s, 6);
    BZERO(sole_s,  5);
    BZERO(cibo_s,  5);
    BZERO(sole_s2, 5);
    BZERO(parlare_s, 8);
    BZERO(sparlare_s, 9);

}

//edit_distance Tests
static void test_edit_distance_empty_str(void){
    int expect_edit_dist = 0;
    int actual_edit_dist = edit_distance(empty_s, empty_s);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}

static void test_edit_distance_equal_str(void){
    int expect_edit_dist = 0;
    int actual_edit_dist = edit_distance(sole_s, sole_s2);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}

static void test_edit_distance_insert_one_char(void){
    int expect_edit_dist = 1;
    int actual_edit_dist = edit_distance(cassa_s, casa_s);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}

static void test_edit_distance_remove_one_char(void){
    int expect_edit_dist = 1;
    int actual_edit_dist = edit_distance(parlare_s, sparlare_s);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}

static void test_edit_distance_from_empty_to_str(void){
    int expect_edit_dist = 4;
    int actual_edit_dist = edit_distance(ciao_s, empty_s);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}

static void test_edit_distance_from_str_to_empty(void){
    int expect_edit_dist = 5;
    int actual_edit_dist = edit_distance(empty_s, addio_s);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}

static void test_edit_distance_long_distance_strings(void){
    int expect_edit_dist = 12;
    int actual_edit_dist = edit_distance(cibo_s, sparlare_s);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}


//edit_distance_dyn Tests
static void test_edit_distance_dyn_empty_str(void){
    int expect_edit_dist = 0;
    int actual_edit_dist = edit_distance_dyn(empty_s, empty_s);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}

static void test_edit_distance_dyn_equal_str(void){
    int expect_edit_dist = 0;
    int actual_edit_dist = edit_distance_dyn(sole_s, sole_s2);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}

static void test_edit_distance_dyn_insert_one_char(void){
    int expect_edit_dist = 1;
    int actual_edit_dist = edit_distance(cassa_s, casa_s);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}

static void test_edit_distance_dyn_remove_one_char(void){
    int expect_edit_dist = 1;
    int actual_edit_dist = edit_distance_dyn(parlare_s, sparlare_s);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}

static void test_edit_distance_dyn_from_empty_to_str(void){
    int expect_edit_dist = 4;
    int actual_edit_dist = edit_distance_dyn(ciao_s, empty_s);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}

static void test_edit_distance_dyn_from_str_to_empty(void){
    int expect_edit_dist = 5;
    int actual_edit_dist = edit_distance_dyn(empty_s, addio_s);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}

static void test_edit_distance_dyn_long_distance_strings(void){
    int expect_edit_dist = 12;
    int actual_edit_dist = edit_distance_dyn(cibo_s, sparlare_s);
    TEST_ASSERT_EQUAL_INT(expect_edit_dist, actual_edit_dist);
}


int main(void){
// start of test session

UNITY_BEGIN();

RUN_TEST(test_edit_distance_empty_str);
RUN_TEST(test_edit_distance_equal_str);
RUN_TEST(test_edit_distance_insert_one_char);
RUN_TEST(test_edit_distance_remove_one_char);
RUN_TEST(test_edit_distance_from_empty_to_str);
RUN_TEST(test_edit_distance_from_str_to_empty);
RUN_TEST(test_edit_distance_long_distance_strings);

RUN_TEST(test_edit_distance_dyn_empty_str);
RUN_TEST(test_edit_distance_dyn_equal_str);
RUN_TEST(test_edit_distance_dyn_insert_one_char);
RUN_TEST(test_edit_distance_dyn_remove_one_char);
RUN_TEST(test_edit_distance_dyn_from_empty_to_str);
RUN_TEST(test_edit_distance_dyn_from_str_to_empty);
RUN_TEST(test_edit_distance_dyn_long_distance_strings);

return UNITY_END();

}