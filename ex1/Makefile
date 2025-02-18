CC = gcc
all: bin/main_ex1 bin/test_ex1

CFLAGS = -g -Wall -Wextra -Wpedantic -Wno-pointer-arith

INCLUDES = src/*.h

COMMON_DEPS = $(INCLUDES) Makefile

build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

bin/main_ex1: build/sort_array.o build/sort_array_main.o $(COMMON_DEPS)
	$(CC) -o bin/main_ex1 build/sort_array.o build/sort_array_main.o

bin/test_ex1: build/sort_array_tests.o build/sort_array.o build/unity.o $(COMMON_DEPS)
	$(CC) -o bin/test_ex1  build/sort_array_tests.o build/sort_array.o build/unity.o

clean:
	del /Q build\*
	del /Q bin\*
