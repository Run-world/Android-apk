
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

$(shell rm -rf $(LOCAL_PATH)/gen)

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-subdir-java-files)

LOCAL_JAVA_LIBRARIES :=

LOCAL_PACKAGE_NAME := UMtoXiriSpeedTest

LOCAL_CERTIFICATE := platform

LOCAL_PROGUARD_ENABLED := disabled

include $(BUILD_PACKAGE)

# Use the folloing include to make our test apk.    
include $(call all-makefiles-under,$(LOCAL_PATH))  
 
