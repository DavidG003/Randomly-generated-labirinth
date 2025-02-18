
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <errno.h> 
#include <limits.h>
#include "edit_distance.h"

#define INIT_SIZE 10
#define INIT_WORDS 2
#define MAX_STR_SIZE 1000

static void to_lowercase(char * str);
static char ** load_str_arr( FILE * fp_to_load, int * num_words);
static FILE * openfile(char const *file_name, char const *permissions);
static void search_min_edit_dist(char ** text_to_correct, int nwords_to_correct, char ** dictionary, int nwords_dict);
static void print_correction(char * word, char ** dict, int * similiar_word_indx, int num_words);
static void * memory_managment(void * array_pointer, int * current_size, int elem_size);

/*To execute the main two parameters are needed, the first one must be the file that contains the text to correct and the second one contains the dictionary used to correct it*/
int main(int argc, char const *argv[]) {
    FILE * fp_dict, * fp_to_correct;
    char ** text_to_correct, ** dictionary;
    int nwords_to_correct, nwords_dict;
    if(argc < 3) {
        printf("Usage: main_ex2 <path_dictionary_to_use> <path_text_to_correct>\n");
        exit(EXIT_FAILURE);
    }

    printf("Opening dictionary...\n");
    fp_dict  = openfile(argv[1], "r");

    printf("Opening file to correct...\n");
    fp_to_correct = openfile(argv[2], "r");
    
    printf("Loading text to correct...\n");
    text_to_correct = load_str_arr(fp_to_correct, &nwords_to_correct);

    printf("Loading words from dictionary...\n");
    dictionary = load_str_arr(fp_dict, &nwords_dict);

    printf("\nCorrecting file %s... \n", argv[2]);
    search_min_edit_dist(text_to_correct, nwords_to_correct, dictionary, nwords_dict);
    printf("Correction completed!\n");

    fclose(fp_dict);
    fclose(fp_to_correct);
    free(text_to_correct);
    free(dictionary);
    return (EXIT_SUCCESS);
}

/* funcion that opens a file_name and return a pointer that points to it*/
FILE * openfile(char const *file_name, char const *permissions){
    FILE * opened_f;
    
    opened_f = fopen(file_name, permissions);
    if(opened_f == NULL){
        fprintf(stderr, "openfile: file %s couldn't be opened \t error: %s\n", file_name, strerror(errno));
        exit(EXIT_FAILURE);
    }

    return opened_f;
}

/* Function that allocates a new array of strings and it stores the words contained in the file pointed by fp in it. The return value is the newly defined and initialized array of strings*/
static char **load_str_arr(FILE *fp_to_load, int * num_words) {
    int size = INIT_SIZE;
    char **words = (char **) memory_managment(NULL, &size, sizeof(*words));
  
    char word[100];
    int i = 0, len;

    fseek(fp_to_load, 0, SEEK_SET);
    while (fscanf(fp_to_load, "%99[a-zA-Z]", word) != EOF) {
        if (i >= size) 
            words = (char **) memory_managment(words, &size, sizeof(*words));
        
        len = strlen(word) + 1;
        words[i] =  (char *) memory_managment(NULL, &len, sizeof(**words));
        to_lowercase(word);
        strcpy(words[i], word);

        fscanf(fp_to_load, "%*[^a-zA-Z]");
        i++;
    }

    words = realloc(words ,i *sizeof(*words));
    if(words == NULL){
        fprintf(stderr, "memory_managment: Memory reallocation failed\n");
        exit(EXIT_FAILURE);
    }

    *num_words = i;
    return words;
}

/*
    Procedure that checks if the strings contained in the array text_to_correct exist in the dictionary, which is also an array of strings
    If the word exist in the dictionary it is marked as correct; while if it doesn't the function prints the words (contained in dictionary) with minimal edit distance from that word.
    dictionary has length equal to nwords_dict.
    text_to_correct has leght equal to nwords_to_correct.
*/
static void search_min_edit_dist(char ** text_to_correct, int nwords_to_correct, char ** dictionary, int nwords_dict){
    int k = 0, size_word_indxs = INIT_WORDS;
    int min_edit_dist = INT_MAX, current_edit_dist;
    int * similiar_word_indx = memory_managment(NULL, &size_word_indxs, sizeof(*similiar_word_indx));

    for(int i = 0; i < nwords_to_correct; i++){
        k = 0;
        min_edit_dist = INT_MAX;
       
        for(int j = 0; j < nwords_dict; j++){
            current_edit_dist =  edit_distance_dyn(text_to_correct[i], dictionary[j]);
        
            if(current_edit_dist == 0){
                k = 0;
                break;
            }
            if(min_edit_dist > current_edit_dist) {
                min_edit_dist = current_edit_dist;
                similiar_word_indx[0] = j;
                k = 1;
            } else if (min_edit_dist == current_edit_dist) {
                if(k >= size_word_indxs)
                    similiar_word_indx = (int *) memory_managment(similiar_word_indx, &size_word_indxs, sizeof(*similiar_word_indx));
        
                similiar_word_indx[k] = j;
                k++;
            }
        }

        print_correction(text_to_correct[i], dictionary, similiar_word_indx, k - 1);
    }
    free(similiar_word_indx);
}


/*
    Procedure that prints on stdout either the confirm that a word has been sucessfully found in the dictionary or those words that are similiar to the searched word.
    The similiar words are contained in "dict" and their positions are contained in the array "similiar_word_indx".
    The number of similiar words found is "num_words".
*/
static void print_correction(char * word, char ** dict, int * similiar_word_indx, int num_words){
    printf("The word '%s' ", word);

    if(num_words == -1){
        printf("is in the dictionary\n");
    } else{
        printf("is not in the dictionary, here are some similiar words: \n\t ");
        for( ;num_words >= 0; num_words--)
            printf("'%s' ", dict[similiar_word_indx[num_words]]);

        printf("\n");
    }
    
    printf("\n");

}

/* 
    Function that allocates new memory of (current_size * elem_size) bytes if the "array_pointer" argument is NULL.
    else it reallocates "array_pointer" to a new size, which is the double of (current_size * elem_size) bytes.
*/
static void * memory_managment(void * array_pointer, int * current_size, int elem_size){
    if(array_pointer != NULL) *current_size *= 2;
    array_pointer = realloc(array_pointer, *current_size * elem_size);

    if (array_pointer == NULL) {
        fprintf(stderr, "memory_managment: Memory reallocation failed\n");
        exit(EXIT_FAILURE);
    }

    return array_pointer;
}

/* Procedure that changes all the uppercase letters in the string "str" into their lowercase equivalent. */
static void to_lowercase(char * str){
    int i  = 0;
    char lower = 'a' - 'A';
    if(str == NULL) return;

    while(str[i] != '\0'){
        if(str[i]>= 'A' && str[i] <= 'Z'){
            str[i] += lower;
        }
        i++;
    }

}