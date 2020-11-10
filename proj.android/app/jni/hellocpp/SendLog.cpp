//
// Created by Nafiz on 2020-11-09.
//

#include "SendLog.h"
#include "platform/android/jni/JniHelper.h"
#include <jni.h>
#include "cocos2d.h"


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

void SendLogs::fetch(){
    cocos2d::JniMethodInfo methodInfo;
    if (! cocos2d::JniHelper::getStaticMethodInfo(methodInfo,
                                                  "org.cocos2dx.cpp.AppActivity",
                                                  "fetchUpdate",
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
        return ;
    }
    methodInfo.env->CallStaticVoidMethod(methodInfo.classID,
                                         methodInfo.methodID);

}