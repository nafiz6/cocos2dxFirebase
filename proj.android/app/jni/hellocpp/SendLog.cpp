//
// Created by Nafiz on 2020-11-09.
//

#include "SendLog.h"
#include "platform/android/jni/JniHelper.h"
#include <jni.h>
#include "cocos2d.h"
#include "HelloWorldScene.h"


void SendLogs::send(){
    cocos2d::JniMethodInfo methodInfo;
    if (! cocos2d::JniHelper::getStaticMethodInfo(methodInfo,
    "org.cocos2dx.cpp.AppActivity",
    "sendLogs",
    "()V")) {
        CCLOG("Could not load method information. Wrong signature?");
        return;
    }
    methodInfo.env->CallStaticVoidMethod(methodInfo.classID,
                                         methodInfo.methodID);
}

void SendLogs::getReward(){
    cocos2d::JniMethodInfo methodInfo;
    if (! cocos2d::JniHelper::getStaticMethodInfo(methodInfo,
    "org.cocos2dx.cpp.AppActivity",
    "getReward",
    "()V")) {
        CCLOG("Could not load method information. Wrong signature?");
        return;
    }
    methodInfo.env->CallStaticVoidMethod(methodInfo.classID,
                                         methodInfo.methodID);
}

std::string SendLogs::receive(){
        cocos2d::JniMethodInfo methodInfo;
    if (! cocos2d::JniHelper::getStaticMethodInfo(methodInfo,
                                                  "org.cocos2dx.cpp.AppActivity",
                                                  "getTopMessage",
                                                  "()Ljava/lang/String;")) {
        CCLOG("Could not load method information. Wrong signature?");
        return "FAILED";
    }
    jstring str = (jstring)methodInfo.env->CallStaticObjectMethod(methodInfo.classID,
                                         methodInfo.methodID);

// Convert jstring to native string
    const char *message = methodInfo.env->GetStringUTFChars(str, 0);
    std::string msg(message);
    return msg;

}

extern "C"
JNIEXPORT void JNICALL
Java_org_cocos2dx_cpp_AppActivity_incrementScore(JNIEnv *env, jobject thiz) {
    HelloWorld::incrementScore();

    // TODO: implement incrementScore()
}