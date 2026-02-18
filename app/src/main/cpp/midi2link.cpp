#include <jni.h>
#include <string>
#include "LinkManager.h"

extern "C" {


JNIEXPORT jlong JNICALL
Java_com_dkxl_midi2link_LinkWrapper_nativeCreateLinkManager(JNIEnv *env, jobject thiz) {
    auto *manager = new(std::nothrow) LinkManager();
    auto returnHandle = (jlong) manager;
    return returnHandle;
}

JNIEXPORT jdouble JNICALL
Java_com_dkxl_midi2link_LinkWrapper_nativeGetTempo(JNIEnv *env, jobject thiz, jlong managerHandle) {
    auto *manager = (LinkManager *) managerHandle;
    if (manager == nullptr) {
        return 0;
    }
    return manager->getTempo();
}

JNIEXPORT jdouble JNICALL
Java_com_dkxl_midi2link_LinkWrapper_nativeGetPhase(JNIEnv *env, jobject thiz, jlong managerHandle) {
    auto *manager = (LinkManager *) managerHandle;
    if (manager == nullptr) {
        return 0;
    }
    return manager->getPhase();
}

JNIEXPORT void JNICALL
Java_com_dkxl_midi2link_LinkWrapper_nativeEnable(JNIEnv *env, jobject thiz, jlong managerHandle) {
    auto *manager = (LinkManager *) managerHandle;
    if (manager != nullptr) {
        manager->enable();
    }
}

} //extern