#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <time.h>
#include "sort_array.h"

#define INIT_SIZE 10
typedef int (*compar_field)(const void*, const void*);
typedef void (*chosen_algo)(void *, size_t, size_t, int (*)(const void*, const void*));


struct record{
    long id;
    char* field1;
    long field2;
    double field3;
};


static compar_field field_choice(size_t field);
static chosen_algo algo_choice(size_t algo);

static int compar_record_field1(const void* r1_p,const void* r2_p);
static int compar_record_field2(const void* r1_p, const void* r2_p);
static int compar_record_field3(const void* r1_p, const void* r2_p);

FILE * openfile(char const *file_name, char const *permissions);

static struct record read_line_fields(char * read_line_p);
static struct record* load_array_of_records(FILE * fp, size_t * size_arr_records);
static void store_array_of_records(FILE * outfile,struct record * ordered_records_from_infile, size_t num_recs); 
static struct record* realloc_arr_record(struct record * arr_to_realloc, size_t * old_size);
static void * my_malloc(int size);

/*
  Procedure that orders the records from infile and then transfers them in outfile
  the field that determines the order is shown by the parameter "field" and the sorting algorithm is chosen with "algo"
*/
void sort_records(FILE *infile, FILE *outfile, size_t field, size_t algo){
    clock_t start, end;

    size_t * size_arr_records = (size_t *)my_malloc(sizeof(*size_arr_records));
    compar_field compar_func   = field_choice(field);
    chosen_algo algorithm_used = algo_choice(algo);

    struct record* records_from_infile = load_array_of_records(infile, size_arr_records);

    start = clock();
    algorithm_used(records_from_infile, *size_arr_records, sizeof(*records_from_infile), compar_func);
    end = clock();

    printf("Successfully sorted in %f seconds...\n", (double)(end - start) / CLOCKS_PER_SEC);

    printf("Ordered array of records, now storing them...\n");

    store_array_of_records(outfile, records_from_infile, *size_arr_records); 
}

/*
    Main must be invoked with four parameters:
      -the first one specifying the file that contains the records
      -the second one specifies the file where the ordered records will be stored
      -the third one is a number (between 1 and 3) that is used to choose what field determines the order between two or more records
      -the last one is a number (1 or 2) that is used to choose the algorithm to use for the sorting 
    
*/
int main(int argc, char const *argv[]) {
    FILE * fp_in, * fp_out;
    size_t field , algorithm_used;
    if(argc < 5) {
        printf("Usage: ordered_array_main <input_file_name> <output_file_name> <field_to_order> <algorithm_to_use>\n");
        printf("\tAlgorithm_to_use: 1 for mergesort; 2 for quicksort\n");
        printf("\tfield_to_order: 1 for field1; 2 for field2; 3 for field3\n");
        exit(EXIT_FAILURE);
    }

    field          = atol(argv[3]);
    algorithm_used = atol(argv[4]);

    fp_in  = openfile(argv[1], "r");
    fp_out = openfile(argv[2], "r+");

    sort_records(fp_in, fp_out, field, algorithm_used);

    fclose(fp_in);
    fclose(fp_out);
    return (EXIT_SUCCESS);
}


/* funcion that opens a file_name and return a pointer that points to it*/
FILE * openfile(char const *file_name, char const *permissions){
    FILE * opened_f;
    
    opened_f = fopen(file_name, permissions);
    if(opened_f == NULL){
        printf("openfile: file %s couldn't be opened \t error: %s\n", file_name, strerror(errno));
        exit(EXIT_FAILURE);
    }

    return opened_f;
}

// FUNCTIONS THAT LOAD AND STORE AN ARRAY OF RECORDS

/*
  function that allocates a new array of records and fills it with the records contained in the file pointed by "fp"
  the fields of every record in this file must be separated by a comma and the records themselves must be separated by a new line character ('n')
*/
static struct record* load_array_of_records(FILE * fp, size_t * size_arr_records){
  struct record new_rec;
  char buffer[1024], *read_line_p;
  int buf_size = 1024; 
  size_t i = 0;
  struct record * rec_array = (struct record *) my_malloc(INIT_SIZE * sizeof(struct record));
  *size_arr_records = INIT_SIZE;

  printf("\nLoading data from file...\n");
  
  while(fgets(buffer,buf_size,fp) != NULL){

    if(i >= *size_arr_records){
      rec_array = realloc_arr_record(rec_array, size_arr_records);
    }

    read_line_p = (char *)my_malloc((strlen(buffer)+1)*sizeof(char));

    strcpy(read_line_p,buffer);

    new_rec = read_line_fields(read_line_p);

    memcpy(rec_array + i, &new_rec, sizeof(new_rec));
    free(read_line_p);
    i = i+1;
  }
  rec_array = realloc(rec_array, i * sizeof(struct record));

  fclose(fp);
  *size_arr_records = i;
  printf("\nData loaded sucessfully\n");
  return rec_array;
}


/* function that creates and returns a record by reading the line pointed by read_line_p*/
static struct record read_line_fields(char * read_line_p){
    struct record new_rec;
    char *id_in_read_line_p,  *field1_in_read_line_p,  *field2_in_read_line_p,  *field3_in_read_line_p, *field1;
    int id, field2;
    double field3;

    id_in_read_line_p     = strtok(read_line_p,",");
    field1_in_read_line_p = strtok(NULL,",");
    field2_in_read_line_p = strtok(NULL,",");
    field3_in_read_line_p = strtok(NULL,",");

    id     = atol(id_in_read_line_p);
    field2 = atol(field2_in_read_line_p);
    field3 = atof(field3_in_read_line_p);
    field1 = (char*) my_malloc((strlen(field1_in_read_line_p)+1)*sizeof(char)); 

    strcpy(field1 ,field1_in_read_line_p);
   
    new_rec.id     = id;
    new_rec.field1 = field1;
    new_rec.field2 = field2;
    new_rec.field3 = field3;

    return new_rec;
}

/*function that reallocates the array of record so that it haves double the size and it returns the new pointer to the array*/
static struct record* realloc_arr_record(struct record * arr_to_realloc, size_t * old_size){

      *old_size = *old_size * 2;
      arr_to_realloc = realloc(arr_to_realloc, *old_size * sizeof(struct record));
      if(arr_to_realloc == NULL){
        printf("realloc_arr_record: reallocation failed\n");
        exit(EXIT_FAILURE);
      }
      return arr_to_realloc;
}

/* procedure that stores the array of records into the file pointed by outfile*/
static void store_array_of_records(FILE * outfile,struct record * ordered_records_from_infile, size_t num_recs){
    char rec_to_string[1024];
    
    for(unsigned long i = 0; i < num_recs; i++){
      sprintf(rec_to_string, "%li,%s,%li,%f\n", ordered_records_from_infile[i].id, ordered_records_from_infile[i].field1, ordered_records_from_infile[i].field2, ordered_records_from_infile[i].field3);
      fputs(rec_to_string, outfile);
    }

    printf("Ordered data stored sucessfully\n");
}

// FUNCTIONS THAT CHOOSE A FUNCTION
/*function used to choose one of the comparing functions, it return a pointer to a function*/
static compar_field field_choice(size_t field){
    if(field < 1 || field > 3){
        printf("ordered_array_main: <field_to_order> == %u but it must be a number between 1 a 3 (included)\n", field);
        exit(EXIT_FAILURE);
    }

    if(field == 1)
      return compar_record_field1;
    
    if(field == 2)
      return compar_record_field2;
    
    return compar_record_field3;
}

/* Function used to choose between quicksort and mergesort, it return a pointer to a function*/
static chosen_algo algo_choice(size_t algo){
   if(algo != 1 && algo != 2){
        printf("algo_choice: <algorithm_to_use> == %u but it must be either 1 or 2\n", algo);
        exit(EXIT_FAILURE);
    }

    if(algo == 1)
      return merge_sort;

    return quick_sort;
}

// COMPAR FUNCTIONS
/* function used to compare field1 of two records, it returns an integer that indicates the order */
static int compar_record_field1(const void* r1_p,const void* r2_p){
  if(r1_p == NULL){
    fprintf(stderr,"precedes_record_int_field: the first parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  if(r2_p == NULL){
    fprintf(stderr,"precedes_record_int_field: the second parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  struct record *rec1_p = (struct record*)r1_p;
  struct record *rec2_p = (struct record*)r2_p;
  int cond = strcmp(rec1_p->field1 ,rec2_p->field1);
  if(cond > 0){
    return(1);
  }else if(cond == 0){
    return(0);
  }
    return -1;
}

/* function used to compare field2 of two records, it returns an integer that indicates the order */
static int compar_record_field2(const void* r1_p, const void* r2_p){
  if(r1_p == NULL){
    fprintf(stderr,"precedes_record_int_field: the first parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  if(r2_p == NULL){
    fprintf(stderr,"precedes_record_int_field: the second parameter is a null pointer");
    exit(EXIT_FAILURE);
  }

  struct record *rec1_p = (struct record*)r1_p;
  struct record *rec2_p = (struct record*)r2_p;
  if(rec1_p->field2 > rec2_p->field2){
    return(1);
  }else if(rec1_p->field2 == rec2_p->field2){
    return(0);
  }
    return -1;
}

/* function used to compare field3 of two records, it returns an integer that indicates the order */
static int compar_record_field3(const void* r1_p, const void* r2_p){
  if(r1_p == NULL){
    fprintf(stderr,"precedes_record_int_field: the first parameter is a null pointer");
    exit(EXIT_FAILURE);
  }
  if(r2_p == NULL){
    fprintf(stderr,"precedes_record_int_field: the second parameter is a null pointer");
    exit(EXIT_FAILURE);
  }

  struct record *rec1_p = (struct record*)r1_p;
  struct record *rec2_p = (struct record*)r2_p;
  if(rec1_p->field3 > rec2_p->field3){
    return(1);
  }else if(rec1_p->field3 == rec2_p->field3){
    return(0);
  }
    return -1;
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