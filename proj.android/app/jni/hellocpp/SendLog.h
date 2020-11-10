//
// Created by Nafiz on 2020-11-09.
//

#ifndef PROJ_ANDROID_SENDLOG_H
#define PROJ_ANDROID_SENDLOG_H

#include "cocos2d.h"
#include <stdio.h>


class SendLogs{
public:
    virtual void send();
    //virtual void fetch();
    virtual std::string receive();
};

#endif //PROJ_ANDROID_SENDLOG_H
