LOCAL_PATH := $(call my-dir)/../src

include $(CLEAR_VARS)

LOCAL_SRC_FILES := multiprocessing.c pipe_connection.c semaphore.c socket_connection.c

LOCAL_MODULE := multiprocessing

LOCAL_CFLAGS := $(CFLAGS) 

include $(BUILD_SHARED_LIBRARY)
