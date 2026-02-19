#include <jni.h>
#include <string>
#include <stdexcept>
#include <iostream>
#include "LinkManager.h"

extern "C" {


// Helper to throw a specific Java exception from C++
void throwJavaException(JNIEnv* env, const char* exceptionClassName, const char* message) {
    jclass exClass = env->FindClass(exceptionClassName);
    if (exClass != nullptr) {
        env->ThrowNew(exClass, message);
    }
}


JNIEXPORT jlong JNICALL
Java_com_dkxl_midi2link_ableton_Link_createLinkManager(JNIEnv *env, jobject me) {
    try {
        auto *manager = new(std::nothrow) LinkManager();
        auto returnHandle = (jlong) manager;
        return returnHandle;
    } catch (const std::exception &e) {
        // Catch standard C++ exceptions and translate to Java
        throwJavaException(env, "java/lang/RuntimeException", e.what());
        return 0;
    } catch (...) {
        // Catch-all for non-standard C++ errors
        throwJavaException(env, "java/lang/Error", "Unknown C++ exception occurred");
        return 0;
    }
}

JNIEXPORT jdouble JNICALL
Java_com_dkxl_midi2link_ableton_Link_getTempo(JNIEnv *env, jobject me, jlong handle) {
    auto *manager = (LinkManager *) handle;
    if (manager == nullptr) {
        return -1;
    }
    return manager->getTempo();
}

JNIEXPORT jdouble JNICALL
Java_com_dkxl_midi2link_ableton_Link_getPhase(JNIEnv *env, jobject me, jlong handle) {
    auto *manager = (LinkManager *) handle;
    if (manager == nullptr) {
        return -1;
    }
    return manager->getPhase();
}

JNIEXPORT void JNICALL
Java_com_dkxl_midi2link_ableton_Link_setEnabled(JNIEnv *env, jobject me, jlong handle,
                                                jboolean bEnable) {
    auto *manager = (LinkManager *) handle;
    if (manager != nullptr) {
        manager->setEnabled(bEnable);
    }
}

JNIEXPORT void JNICALL
Java_com_dkxl_midi2link_ableton_Link_setTempo(JNIEnv *env, jobject me, jlong handle,
                                              jdouble bpm) {
    auto *manager = (LinkManager *) handle;
    if (manager != nullptr) {
        manager->setTempo(bpm);
    }
}

JNIEXPORT jboolean JNICALL
Java_com_dkxl_midi2link_ableton_Link_isEnabled(JNIEnv *env, jobject me, jlong handle) {
    auto *manager = (LinkManager *) handle;
    return (manager != nullptr) && manager->isEnabled();
}

JNIEXPORT void JNICALL
Java_com_dkxl_midi2link_ableton_Link_setPlaying(JNIEnv *env, jobject me, jlong handle,
                                                jboolean bPlay) {
    auto *manager = (LinkManager *) handle;
    if (manager != nullptr) {
        manager->setPlaying(bPlay);
    }
}

JNIEXPORT jboolean JNICALL
Java_com_dkxl_midi2link_ableton_Link_isPlaying(JNIEnv *env, jobject me, jlong handle) {
    auto *manager = (LinkManager *) handle;
    return (manager != nullptr) && manager->isPlaying();
}

JNIEXPORT jlong JNICALL
Java_com_dkxl_midi2link_ableton_Link_numPeers(JNIEnv *env, jobject me, jlong handle) {
    auto *manager = (LinkManager *) handle;
    if (manager == nullptr) {
        return -1;
    }
    return manager->numPeers();
}

} // extern