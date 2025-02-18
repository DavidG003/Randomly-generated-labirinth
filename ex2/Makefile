CC = gcc
all: bin/main_ex2  bin/test_ex2

CFLAGS = -g -Wall -Wextra -Wpedantic -Wno-pointer-arith

INCLUDES = src/*.h

COMMON_DEPS = $(INCLUDES) Makefile

build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

bin/main_ex2: build/edit_distance.o build/edit_distance_main.o $(COMMON_DEPS)
	$(CC) -o bin/main_ex2 build/edit_distance.o build/edit_distance_main.o

bin/test_ex2: build/edit_distance_tests.o build/edit_distance.o build/unity.o $(COMMON_DEPS)
	$(CC) -o bin/test_ex2  build/edit_distance_tests.o build/edit_distance.o build/unity.o

clean:
	del /Q build\*
	del /Q bin\*
