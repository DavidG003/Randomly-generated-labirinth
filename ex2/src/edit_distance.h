#ifndef EDIT_DISTANCE_H_lnjgnwgfnwiofjwipojfipo
#define EDIT_DISTANCE_H_lnjgnwgfnwiofjwipojfipo

/*
    Function that calculates the minimal number of operations that are required to transform the string pointed by s2
    into the string pointed by s1.
    The possible operations considered by this function are those of deleting or inserting a character in s2, so modifications or swaps are not considered.
    The function does not modify any of the two original strings and it returns an integer that corresponds to the minimal edit distance.
    Both of the strings must terminate with the '\0' character.
*/
int edit_distance(const char *s1, const char* s2);

/*
    Very similiar to the "edit_distance" funcion with the only difference of using a dynamic programmation to improve the execution time.
    The input string values hold the same meaning as in "edit_distance" and they both terminate with a '/0' char.
    It does not modify any of the two strings and the operations remain the same as "edit_distance".
*/

int edit_distance_dyn(const char *s1, const char* s2);


#endif /* EDIT_DISTANCE_H_lnjgnwgfnwiofjwipojfipo */

