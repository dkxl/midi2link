#include <jni.h>
#include <string>
#include "LinkManager.h"

extern "C" {


JNIEXPORT jlong JNICALL
Java_com_dkxl_midi2link_ableton_Link_createLinkManager(JNIEnv *env, jobject thiz) {
    auto *manager = new(std::nothrow) LinkManager();
    auto returnHandle = (jlong) manager;
    return returnHandle;
}

JNIEXPORT jdouble JNICALL
Java_com_dkxl_midi2link_ableton_Link_getTempo(JNIEnv *env, jobject thiz, jlong managerHandle) {
    auto *manager = (LinkManager *) managerHandle;
    if (manager == nullptr) {
        return 0;
    }
    return manager->getTempo();
}

JNIEXPORT jdouble JNICALL
Java_com_dkxl_midi2link_ableton_Link_getPhase(JNIEnv *env, jobject thiz, jlong managerHandle) {
    auto *manager = (LinkManager *) managerHandle;
    if (manager == nullptr) {
        return 0;
    }
    return manager->getPhase();
}

JNIEXPORT void JNICALL
Java_com_dkxl_midi2link_ableton_Link_setEnabled(JNIEnv *env, jobject thiz, jlong managerHandle,
                                                     jboolean bEnable) {
    auto *manager = (LinkManager *) managerHandle;
    if (manager != nullptr) {
        manager->setEnabled(bEnable);
    }
}



} //extern